package com.demo.process;

import com.demo.constant.AllFunction;
import com.demo.constant.PropertyKey;
import com.demo.dto.AdapterDto;
import com.google.gson.Gson;
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
public class RequestProcess implements Processor {
    final Gson gson;

    @Override
    public void process(Exchange exchange) throws Exception {
        var body = exchange.getIn().getBody(String.class);
        log.info("Start RequestProcess body={}", body);
        var adapterDto = gson.fromJson(body, AdapterDto.class);
        var function = AllFunction.getEnum(adapterDto.getFunctionName());
        log.info("lmid={} RequestProcess function={} body={}", adapterDto.getLmid(), function.getFunctionName(), body);
        exchange.setProperty(PropertyKey.LMID, adapterDto.getLmid());
        exchange.setProperty(PropertyKey.FUNCTION_NAME, function.getFunctionName());
        exchange.setProperty(PropertyKey.REQUEST, body);
        exchange.getIn().setBody(adapterDto.getJsonData());
    }

}
