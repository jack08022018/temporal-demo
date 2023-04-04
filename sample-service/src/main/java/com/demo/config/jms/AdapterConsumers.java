package com.demo.config.jms;

import com.demo.constant.AllFunction;
import com.demo.dto.AdapterDto;
import com.demo.utils.CommonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.jms.Message;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdapterConsumers implements JmsListenerConfigurer {
    final CommonUtils commonUtils;
    final RestTemplate customRestTemplate;
    final ObjectMapper customObjectMapper;

    @Value("${orchestrator.url}")
    String orchestratorUrl;

    @Override
    public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
        for (AllFunction s : AllFunction.values()) {
            var queueName = commonUtils.getQueueName(s);
            log.info("Init comsuer function={} queueName={}", s.getFunctionName(), queueName);
            var endpoint = new SimpleJmsListenerEndpoint();
            endpoint.setId(queueName);
            endpoint.setDestination(queueName);
            endpoint.setSelector("TYPE = 'RESPONSE'");
            endpoint.setMessageListener(message -> {
                CompletableFuture.runAsync(() -> {
                    if (message instanceof ActiveMQTextMessage) {
                        handleMessageConsume(message);
                    }else {
                        log.error("Can not parse message {}", message);
                    }
                });
            });
            registrar.registerEndpoint(endpoint);
        }
    }

    private void handleMessageConsume(Message message) {
        try {
            var adapterDto = message.getBody(AdapterDto.class);
            log.info("lmid={} consume data response={}", adapterDto.getLmid(), customObjectMapper.writeValueAsString(adapterDto));
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            var request = new HttpEntity<>(adapterDto, headers);
            customRestTemplate.postForObject(orchestratorUrl + "/completeActivity/complete", request, JsonNode.class);
        }catch (Exception e) {
            log.error("handleMessageConsume error={} message={}", e.getMessage(), message, e);
        }
    }
}
