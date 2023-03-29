package com.demo.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.concurrent.CompletableFuture;

public interface RestService {
    public JsonNode getData();
}
