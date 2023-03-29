package com.demo.workflow;

import com.demo.activities.MainActivities;
import com.demo.config.exceptions.NotRetryException;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class GetDataWorkflowImpl implements GetDataWorkflow {

    ActivityOptions activityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            .setRetryOptions(RetryOptions.newBuilder()
                    .setMaximumAttempts(3)
                    .setDoNotRetry("com.demo.config.exceptions.NotRetryException")
                    .build())
            .build();

    private final MainActivities mainActivities = Workflow.newActivityStub(MainActivities.class, activityOptions);

    @Override
    public void hello() throws NotRetryException {
        log.info("hello:");
        mainActivities.deduct();
        try {
            mainActivities.refund();
        }catch (Exception e) {
            log.error("xxx: " + e.getMessage());
        }
    }
}
