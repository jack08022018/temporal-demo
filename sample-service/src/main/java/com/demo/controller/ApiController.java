package com.demo.controller;


import com.demo.constant.AllFunction;
import com.demo.dto.ServiceRequest;
import com.demo.dto.ServiceResponse;
import com.demo.utils.CommonUtils;
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
    public ServiceResponse getData(@RequestBody ServiceRequest request) throws Exception {
        return commonUtils.sendMessageToAdapter(request, AllFunction.getEnum(request.getFunctionName()));
    }

}
