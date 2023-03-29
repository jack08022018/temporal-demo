package com.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestServiceImpl implements RestService {
    final RestTemplate customRestTemplate;

    @Value("${orchestrator.url}")
    String orchestratorUrl;

    @Override
    public void excuteCompleteActivity(String params) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(params, headers);
        customRestTemplate.postForObject(orchestratorUrl, request, JsonNode.class);
    }
}
