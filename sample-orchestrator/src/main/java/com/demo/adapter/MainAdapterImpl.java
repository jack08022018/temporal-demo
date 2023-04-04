package com.demo.adapter;

import com.demo.dto.base.ActivityRequest;
import com.demo.dto.base.ActivityResponse;
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
public class MainAdapterImpl implements MainAdapter {
    final RestTemplate customRestTemplate;

    @Value("${service.url}")
    String serviceUrl;

    @Override
    public void deduct() {
        System.out.println("deduct!");
    }

    @Override
    public void refund() {
        System.out.println("refund!");
    }

    @Override
    public ActivityResponse getData(ActivityRequest params) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(params, headers);
        return customRestTemplate.postForObject(serviceUrl + "/api/getData", request, ActivityResponse.class);
    }

}
