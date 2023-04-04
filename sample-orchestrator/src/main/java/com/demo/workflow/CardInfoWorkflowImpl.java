package com.demo.workflow;

import com.demo.activities.MainActivities;
import com.demo.constant.AllFunction;
import com.demo.dto.CardInfoDto;
import com.demo.dto.base.RequestDto;
import com.demo.dto.base.WorkflowResponse;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class CardInfoWorkflowImpl implements CardInfoWorkflow {
    ActivityOptions activityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            .setRetryOptions(RetryOptions.newBuilder()
                    .setMaximumAttempts(3)
                    .setDoNotRetry("com.demo.config.exceptions.NotRetryException")
                    .build())
            .build();

    private final MainActivities mainActivities = Workflow.newActivityStub(MainActivities.class, activityOptions);

    @Override
    public WorkflowResponse getCardInfo(RequestDto<CardInfoDto> dto) throws Exception {
        var function = AllFunction.GET_CARD_INFO;
        log.info("lmid={} start workflow {}", dto.lmid, function.getFunctionName());
        var response = mainActivities.getData(dto, function);
        return WorkflowResponse.builder()
                .responseCode(response.getResponseCode())
                .description(response.getDescription())
                .jsonData(response.getJsonData())
                .build();
    }
}
