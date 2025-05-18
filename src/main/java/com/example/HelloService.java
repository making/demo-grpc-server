package com.example;

import com.example.proto.HelloRequest;
import com.example.proto.HelloResponse;
import com.example.proto.ReactorHelloServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HelloService extends ReactorHelloServiceGrpc.HelloServiceImplBase {

    private final Logger log = LoggerFactory.getLogger(HelloService.class);

    @Override
    public Mono<HelloResponse> sayHello(Mono<HelloRequest> request) {
        log.info("sayHello");
        return request
            .map(req -> HelloResponse.newBuilder().setReply(String.format("Hello %s!", req.getGreeting())).build());
    }

    // 以下でも可
    //@Override
    //public Mono<HelloResponse> sayHello(HelloRequest request) {
    //	log.info("sayHello");
    //	return Mono
    //		.just(HelloResponse.newBuilder().setReply(String.format("Hello %s!", request.getGreeting())).build());
    //}

    @Override
    public Flux<HelloResponse> lotsOfReplies(Mono<HelloRequest> request) {
        log.info("lotsOfReplies");
        return request.flatMapMany(req -> Flux.range(0, 10)
            .map(i -> HelloResponse.newBuilder()
                .setReply(String.format("[%05d] Hello %s!", i, req.getGreeting()))
                .build()));
    }

}
