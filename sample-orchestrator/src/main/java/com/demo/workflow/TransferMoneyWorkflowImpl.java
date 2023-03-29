package com.demo.workflow;

import com.demo.activities.MainActivities;
import com.demo.config.exceptions.NotRetryException;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferMoneyWorkflowImpl implements TransferMoneyWorkflow {

    private final MainActivities mainActivities = Workflow.newActivityStub(MainActivities.class);

    @Override
    public void transferMoney() throws NotRetryException {
//        log.info("Transfer money start: {}", Workflow.getInfo().getWorkflowId());
        log.info("Transfer money start:");
        mainActivities.deduct();
//        Promise<String> promise = Async.function(() -> transferActivities.getData());
//        String info = promise.get();
        String data = mainActivities.getData();
        System.out.println("aaa: " + data);
        mainActivities.refund();
    }
}
