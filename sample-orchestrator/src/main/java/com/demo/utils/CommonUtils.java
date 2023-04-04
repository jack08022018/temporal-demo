package com.demo.utils;

import com.demo.constant.AllFunction;
import com.demo.constant.ResponseStatus;
import com.demo.dto.base.ActivityRequest;
import com.demo.dto.base.ActivityResponse;
import com.demo.dto.base.RequestDto;
import io.temporal.activity.ActivityExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {
    public static ActivityResponse handleActivity(ActivityExecutionContext context, ActivityResponse res) {
        var status = ResponseStatus.getEnum(res.getResponseCode());
        switch (status) {
            case PROGRESSING -> context.doNotCompleteOnReturn();
            case SUCCESS -> {
                return ActivityResponse.builder()
                        .responseCode(ResponseStatus.SUCCESS.getCode())
                        .description(ResponseStatus.SUCCESS.getDetail())
                        .jsonData(res.getJsonData())
                        .build();
            }
            default -> {
                return ActivityResponse.builder()
                        .responseCode(ResponseStatus.ERROR.getCode())
                        .description(res.getDescription())
                        .build();
            }
        }
        return null;
    }

    public static ActivityRequest buidActivityRequest(RequestDto dto, String jsonData, ActivityExecutionContext context, AllFunction allFunction) {
        var workflowId = context.getInfo().getWorkflowId();
        var activityId = context.getInfo().getActivityId();
        return ActivityRequest.builder()
                .lmid(dto.lmid)
                .functionName(allFunction.getFunctionName())
                .workflowId(workflowId)
                .activityId(activityId)
                .jsonData(jsonData)
                .build();
    }
}
