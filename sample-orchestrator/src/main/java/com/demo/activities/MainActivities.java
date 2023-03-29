package com.demo.activities;

import com.demo.config.exceptions.NotRetryException;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface MainActivities {
    @ActivityMethod
    void deduct();

    @ActivityMethod
    void refund() throws NotRetryException;

    @ActivityMethod
    String getData();
}
