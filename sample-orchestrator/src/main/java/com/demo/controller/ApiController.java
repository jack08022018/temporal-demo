package com.demo.controller;


import com.demo.constant.AllFunction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.config.properties.TemporalProperties;
import com.demo.workflow.*;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ApiController {
//    final ApiService apiService;
    final WorkflowClient workflowClient;
    final TemporalProperties temporalProperties;

    @PostMapping(value = "/transfer")
    public void transfer() throws Exception {
        String random = RandomStringUtils.random(10);
        var workflow = workflowClient.newWorkflowStub(
                TransferMoneyWorkflow.class,
                WorkflowOptions.newBuilder()
//                        .setWorkflowId("TransferMoneyWorkflow-" + random)
                        .setTaskQueue(AllFunction.TRANSFER_MONEY.name())
                        .setWorkflowExecutionTimeout(Duration.ofMillis(60000))
                        .setWorkflowTaskTimeout(Duration.ofMillis(1000))
                        .setRetryOptions(RetryOptions.newBuilder()
                                .setMaximumAttempts(1)
                                .build())
                        .build());
        workflow.transferMoney();
//        WorkflowClient.start(workflow::transferMoney);
    }

    @PostMapping(value = "/hello")
    public void hello() throws Exception {
        System.out.println("Xxx: " + new ObjectMapper().writeValueAsString(temporalProperties));
        var workflow = workflowClient.newWorkflowStub(
                GetDataWorkflow.class,
                WorkflowOptions.newBuilder()
//                        .setWorkflowId("TransferMoneyWorkflow-" + random)
                        .setTaskQueue(AllFunction.GETDATA.name())
                        .setWorkflowExecutionTimeout(Duration.ofSeconds(10))
                        .setWorkflowTaskTimeout(Duration.ofSeconds(10))
                        .setRetryOptions(RetryOptions.newBuilder()
                                .setDoNotRetry("com.demo.config.exceptions.NotRetryException")
                                .setMaximumAttempts(1)
                                .build())
                        .build());
        workflow.hello();
//        WorkflowClient.start(workflow::hello);
    }

}
