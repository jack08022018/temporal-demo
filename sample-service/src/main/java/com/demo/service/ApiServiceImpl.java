package com.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    final ObjectMapper customObjectMapper;

    @Override
    public <T> T getData() {
        return (T) null;
    }

}
