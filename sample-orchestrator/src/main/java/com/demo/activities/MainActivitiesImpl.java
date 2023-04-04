package com.demo.activities;

import com.demo.adapter.MainAdapter;
import com.demo.config.exceptions.NotRetryException;
import com.demo.constant.AllFunction;
import com.demo.dto.base.ActivityResponse;
import com.demo.dto.base.RequestDto;
import com.demo.utils.CommonUtils;
import com.google.gson.Gson;
import io.temporal.activity.Activity;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.client.ActivityCompletionClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainActivitiesImpl implements MainActivities {
    private final MainAdapter mainAdapter;
    private final ActivityCompletionClient completionClient;
    final Gson gson = new Gson();

    public MainActivitiesImpl(MainAdapter mainAdapter, ActivityCompletionClient completionClient) {
        this.mainAdapter = mainAdapter;
        this.completionClient = completionClient;
    }

    @Override
    public void deduct() {
        mainAdapter.deduct();
//        ActivityExecutionContext context = Activity.getExecutionContext();
//        byte[] taskToken = context.getTaskToken();
//        context.doNotCompleteOnReturn();
//        ForkJoinPool.commonPool().execute(() -> composeGreetingAsync(taskToken, "greeting!"));
    }

    @Override
    public void refund() throws NotRetryException {
        mainAdapter.refund();
        try {
            int a = 1/0;
        }catch (Exception e) {
            throw new NotRetryException(e.getMessage());
        }
    }

    @Override
    public ActivityResponse getData(RequestDto dto, AllFunction allFunction) {
        ActivityExecutionContext context = Activity.getExecutionContext();
        var activityRequest = CommonUtils.buidActivityRequest(dto, "", context, allFunction);
        log.info("lmid={} activity=getData function={} retry={} StartToCloseTimeout={} request={}",
                dto.lmid, allFunction.getFunctionName(), context.getInfo().getAttempt(),
                context.getInfo().getStartToCloseTimeout(), gson.toJson(activityRequest));
        var activityResponse = mainAdapter.getData(activityRequest);
        return CommonUtils.handleActivity(context, activityResponse);
    }

}
