package com.camel.service;

public interface ActorService {
    void saveActor(String firstName);
    void saveActorRollbackFor(String firstName);
}
