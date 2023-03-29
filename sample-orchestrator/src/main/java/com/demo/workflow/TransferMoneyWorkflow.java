package com.demo.workflow;

import com.demo.config.exceptions.NotRetryException;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface TransferMoneyWorkflow {
    @WorkflowMethod
    void transferMoney() throws NotRetryException;

}
