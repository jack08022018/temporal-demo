package com.demo.process;

import com.demo.constant.PropertyKey;
import com.demo.config.exeptions.AdapterException;
import com.demo.dto.ActivityResponse;
import com.demo.dto.AdapterDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdapterExceptionProcess implements Processor {
    final Gson gson;

    @Override
    public void process(Exchange exchange) throws Exception {
        try {
            var exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, AdapterException.class);
            var requestString = exchange.getProperty(PropertyKey.REQUEST, String.class);
            log.error("Start AdapterExceptionProcess message={} requestBody={}", exception.getDescription(), requestString);
            var request = gson.fromJson(requestString, AdapterDto.class);
            var lmid = request.getLmid();
            var activityResponse = ActivityResponse.builder()
                    .responseCode(exception.getResponseCode())
                    .description(exception.getDescription())
                    .build();
            var adapterResponse = AdapterDto.builder()
                    .lmid(lmid)
                    .workflowId(request.getWorkflowId())
                    .activityId(request.getActivityId())
                    .jsonData(gson.toJson(activityResponse))
                    .build();
            String body = gson.toJson(adapterResponse);
            exchange.getIn().setBody(body);
            log.error("lmid={} AdapterExceptionProcess message={}", lmid, exception.toString(), exception);
        } catch (Exception ex) {
            log.error("AdapterExceptionProcess fail", ex);
        }
    }
}
