package com.demo.workflow;

import com.demo.config.exceptions.NotRetryException;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface GetDataWorkflow {
    @WorkflowMethod
    void hello() throws NotRetryException;

}
