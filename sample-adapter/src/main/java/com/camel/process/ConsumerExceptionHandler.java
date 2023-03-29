package com.camel.process;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerExceptionHandler implements Processor {
    final JmsTemplate jmsTemplate;

    @Value("${queues.queueDev2}")
    private String queueDev2;

    @Value("${queue.selector.key}")
    private String selectorKey;

    @Value("${queue.selector.consumer}")
    private String selectorConsumer;

    @Override
    public void process(Exchange exchange) throws Exception {
        Throwable caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
        log.error("QueueException: ", caused);
        ModelMap result = new ModelMap();
        result.put("status", "error");
        result.put("message", caused.getMessage());
        exchange.getIn().setBody(result);
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
//        log.info(String.valueOf(result));

        jmsTemplate.convertAndSend(queueDev2, String.valueOf(result), messagePostProcessor -> {
            messagePostProcessor.setStringProperty(selectorKey, selectorConsumer);
            return messagePostProcessor;
        });
    }

}
