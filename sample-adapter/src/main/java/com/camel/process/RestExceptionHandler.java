package com.camel.process;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Slf4j
@Component
public class RestExceptionHandler implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Throwable caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
        log.error("ExceptionProcessor: ", caused);
        ModelMap result = new ModelMap();
        result.put("status", "error");
        result.put("message", caused.getMessage());
        exchange.getIn().setBody(result);
//        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);

//        Map<String, Object> headersMap = exchange.getIn().getHeaders();
//        if (!headersMap.isEmpty()) {
//            headersMap.entrySet()
//                    .stream()
//                    .forEach(e -> log.info("Header key [{}] -||- Header value [{}]", e.getKey(), e.getValue()));
//        } else {
//            log.info("Empty header");
//        }

    }

}
