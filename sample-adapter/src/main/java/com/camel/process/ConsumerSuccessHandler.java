package com.camel.process;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerSuccessHandler implements Processor {
    final JmsTemplate jmsTemplateIbm;

    @Value("${queues.queueDev2}")
    private String queueDev2;

    @Value("${queue.selector.key}")
    private String selectorKey;

    @Value("${queue.selector.consumer}")
    private String selectorConsumer;

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("ToQueueProcess");
        String body = (String) exchange.getIn().getBody();
        jmsTemplateIbm.convertAndSend(queueDev2, body, messagePostProcessor -> {
            messagePostProcessor.setStringProperty(selectorKey, selectorConsumer);
            return messagePostProcessor;
        });
    }

}
