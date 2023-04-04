package com.demo.activities;

import com.demo.config.exceptions.NotRetryException;
import com.demo.constant.AllFunction;
import com.demo.dto.base.ActivityResponse;
import com.demo.dto.base.RequestDto;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface MainActivities {
    @ActivityMethod
    void deduct();

    @ActivityMethod
    void refund() throws NotRetryException;

    @ActivityMethod
    ActivityResponse getData(RequestDto dto, AllFunction allFunction);
}
