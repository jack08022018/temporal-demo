package com.demo.utils;

import com.demo.constant.AllFunction;
import com.demo.constant.ResponseStatus;
import com.demo.dto.ActivityRequest;
import com.demo.dto.ActivityResponse;
import com.demo.dto.AdapterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.reflection.v1alpha.ServiceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonUtils {
    final ObjectMapper customObjectMapper;
    final JmsTemplate activeMqTemplate;

    final String FREFIX = "SAMPLE_TEMPORAL";

    public String getQueueName(AllFunction function) {
        return FREFIX + "." + function.getFunctionName();
    }

    public ActivityResponse sendMessageToAdapter(ActivityRequest request, AllFunction function) {
        var functionName = request.getFunctionName();
        var lmid = request.getLmid();
        try {
            log.info("lmid={} function={} request={}", lmid, functionName, customObjectMapper.writeValueAsString(request));
            var adapterDto = AdapterDto.builder()
                    .lmid(lmid)
                    .functionName(functionName)
                    .workflowId(request.getWorkflowId())
                    .activityId(request.getActivityId())
                    .jsonData(request.getJsonData())
                    .build();
            var queueName = getQueueName(function);
            var message = customObjectMapper.writeValueAsString(adapterDto);
            log.info("lmid={} function={} send to adapter with queue={} request={}", lmid, functionName, queueName, message);
            activeMqTemplate.convertAndSend(queueName, message, messagePostProcessor -> {
                messagePostProcessor.setStringProperty("TYPE", "REQUEST");
                return messagePostProcessor;
            });
            return ActivityResponse.builder()
                    .responseCode(ResponseStatus.PROGRESSING.getCode())
                    .description(ResponseStatus.PROGRESSING.getDetail())
                    .build();
        }catch (Exception e) {
            log.error("lmid={} function={} message={}", lmid, functionName, e.getMessage(), e);
            return ActivityResponse.builder()
                    .responseCode(ResponseStatus.ERROR.getCode())
                    .description(e.getMessage())
                    .build();
        }
    }
}
