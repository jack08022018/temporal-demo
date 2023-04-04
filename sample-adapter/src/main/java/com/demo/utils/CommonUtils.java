package com.demo.utils;

import com.demo.constant.AllFunction;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    final String FREFIX = "SAMPLE_ADAPTER";

    public String getQueueRequest(AllFunction function) {
        return FREFIX + "." + function.getFunctionName() + ".REQUEST";
    }

    public String getQueueResponse(AllFunction function) {
        return FREFIX + "." + function.getFunctionName() + ".RESPONSE";
    }

}
