package com.demo.activities;

import com.demo.adapter.MainAdapter;
import com.demo.config.exceptions.NotRetryException;
import io.temporal.activity.Activity;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.client.ActivityCompletionClient;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MainActivitiesImpl implements MainActivities {
    private final MainAdapter mainAdapter;
    private final ActivityCompletionClient completionClient;

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
    public String getData() {
        ActivityExecutionContext context = Activity.getExecutionContext();
//        byte[] taskToken = context.getTaskToken();
        context.doNotCompleteOnReturn();
        System.out.println("WorkflowId: \n" + context.getInfo().getWorkflowId());
        System.out.println("ActivityId: \n" + context.getInfo().getActivityId());
//        var dto = CompletionDto.builder()
//                .activityId(context.getInfo().getActivityId())
//                .workflowId(context.getInfo().getWorkflowId())
//                .build();
//        ForkJoinPool.commonPool().execute(() -> getData(dto));
        return null;
    }
    private void composeGreetingAsync(byte[] taskToken) {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String str = new String(taskToken, StandardCharsets.UTF_8);
        System.out.println("taskToken: " + str);
        String info = mainAdapter.getInfo();
        completionClient.complete(taskToken, info);
    }

}
