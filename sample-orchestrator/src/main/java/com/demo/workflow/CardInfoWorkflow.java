package com.demo.workflow;

import com.demo.dto.CardInfoDto;
import com.demo.dto.base.RequestDto;
import com.demo.dto.base.WorkflowResponse;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface CardInfoWorkflow {
    @WorkflowMethod
    WorkflowResponse getCardInfo(RequestDto<CardInfoDto> dto) throws Exception;

}
