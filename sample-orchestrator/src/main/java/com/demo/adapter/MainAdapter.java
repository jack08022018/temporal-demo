package com.demo.adapter;

import com.demo.dto.base.ActivityRequest;
import com.demo.dto.base.ActivityResponse;

public interface MainAdapter {
    void deduct();
    void refund();
    ActivityResponse getData(ActivityRequest request);
}
