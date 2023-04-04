package com.demo.controller;


import com.demo.constant.AllFunction;
import com.demo.dto.ActivityRequest;
import com.demo.dto.ActivityResponse;
import com.demo.utils.CommonUtils;
import io.grpc.reflection.v1alpha.ServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ApiController {
    final CommonUtils commonUtils;

    @PostMapping(value = "/getData")
    public ActivityResponse getData(@RequestBody ActivityRequest request) throws Exception {
        return commonUtils.sendMessageToAdapter(request, AllFunction.getEnum(request.getFunctionName()));
    }

}
