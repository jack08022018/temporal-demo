package com.demo.worker;

import com.demo.activities.MainActivitiesImpl;
import com.demo.adapter.MainAdapter;
import com.demo.constant.AllFunction;
import com.demo.workflow.CardInfoWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerFactoryOptions;
import io.temporal.worker.WorkerOptions;
import io.temporal.worker.WorkflowImplementationOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class CardInfoWorker {
    final WorkflowClient workflowClient;
    final WorkerFactoryOptions defaultWorkerFactoryOptions;
    final WorkerOptions defaultWorkerOptions;
    final WorkflowImplementationOptions defaultWorkflowImplementationOptions;
    final MainAdapter mainAdapter;

    @PostConstruct
    public void createWorker() {
        log.info("Registering CardInfoWorker..");
        var workerFactory = WorkerFactory.newInstance(workflowClient, defaultWorkerFactoryOptions);
        var worker = workerFactory.newWorker(AllFunction.GET_CARD_INFO.name(), defaultWorkerOptions);

        var completionClient = workflowClient.newActivityCompletionClient();
        worker.registerWorkflowImplementationTypes(defaultWorkflowImplementationOptions, CardInfoWorkflowImpl.class);
        worker.registerActivitiesImplementations(new MainActivitiesImpl(mainAdapter, completionClient));
        workerFactory.start();
    }
}
