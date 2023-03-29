package com.camel.grpcService;

import com.fasterxml.jackson.databind.ObjectMapper;
import grpc.SenderServiceGrpc;
import grpc.TransactionRequest;
import grpc.TransactionResponse;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class SenderService extends SenderServiceGrpc.SenderServiceImplBase {
    final ObjectMapper customObjectMapper;

    private StatusRuntimeException getException(String message) {
        return Status.INVALID_ARGUMENT
                .withDescription(message)
                .asRuntimeException(new Metadata());
    }

    @Override
    public void deduct(TransactionRequest dto, StreamObserver<TransactionResponse> responseObserver) {
        System.out.println("deduct: " + dto.getTransactionId());
        TransactionResponse response = TransactionResponse.newBuilder()
                .setResult("success")
                .build();

//        if(user == null) {
//            responseObserver.onError(getException("User sender not found"));
//            responseObserver.onCompleted();
//        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

//    @Override
//    @Transactional
//    public void refund(TransactionRequest dto, StreamObserver<TransactionResponse> responseObserver) {
//    }

//    @Override
//    public void hello(HelloRequest dto, StreamObserver<HelloResponse> responseObserver) {
//        HelloResponse response = HelloResponse.newBuilder()
//                .setOutput("Hello: " + dto.getInput())
//                .build();
//
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
}