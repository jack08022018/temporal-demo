package com.demo.controller;


import com.demo.dto.ActivityResult;
import com.demo.dto.CompleteActivityDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.temporal.client.WorkflowClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/completeActivity")
public class CompleteActivityController {
    final WorkflowClient workflowClient;
    final ObjectMapper customObjectMapper;

    @PostMapping(value = "/complete")
    public void complete(@RequestBody CompleteActivityDto dto) throws Exception {
        try {
            log.info("lmid={} CompleteActivity request={}", dto.getLmid(), customObjectMapper.writeValueAsString(dto));
            var completionClient = workflowClient.newActivityCompletionClient();
            var activityResult = customObjectMapper.readValue(dto.getJsonData(), ActivityResult.class);
            completionClient.complete(dto.getWorkflowId(), Optional.empty(), dto.getActivityId(), activityResult);
        }catch (Exception e) {
            log.error("lmid={} CompleteActivity error message={}", dto.getLmid(), e.getMessage(), e);
        }
    }

}
