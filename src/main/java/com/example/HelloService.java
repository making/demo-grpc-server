package com.example;

import com.example.proto.HelloRequest;
import com.example.proto.HelloResponse;
import com.example.proto.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HelloService extends HelloServiceGrpc.HelloServiceImplBase {

    private final Logger log = LoggerFactory.getLogger(HelloService.class);

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        log.info("sayHello");
        HelloResponse response = HelloResponse.newBuilder()
            .setReply(String.format("Hello %s!", request.getGreeting()))
            .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void lotsOfReplies(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        log.info("lotsOfReplies");
        for (int i = 0; i < 10; i++) {
            HelloResponse response = HelloResponse.newBuilder()
                .setReply(String.format("[%05d] Hello %s!", i, request.getGreeting()))
                .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

}
