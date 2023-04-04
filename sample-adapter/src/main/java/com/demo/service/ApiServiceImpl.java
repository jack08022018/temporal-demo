package com.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiServiceImpl implements ApiService {
    final ObjectMapper customObjectMapper;

    @Override
    public void getCardInfo(Exchange exchange) {
        var requestDto = customObjectMapper.readValue(exchange.getIn().getBody(String.class), RequestDt)
    }
}
