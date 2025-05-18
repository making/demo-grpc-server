package com.example;

import com.example.proto.HelloRequest;
import com.example.proto.HelloResponse;
import com.example.proto.ReactorHelloServiceGrpc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.grpc.client.ImportGrpcClients;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.grpc.client.default-channel.address=0.0.0.0:${local.server.port}")
@ImportGrpcClients(types = ReactorHelloServiceGrpc.ReactorHelloServiceStub.class)
class HelloServiceTest {

    @Autowired
    ReactorHelloServiceGrpc.ReactorHelloServiceStub stub;

    @Test
    void sayHello() {
        Mono<HelloResponse> response = this.stub.sayHello(HelloRequest.newBuilder().setGreeting("John Doe").build());
        StepVerifier.create(response)
            .assertNext(r -> assertThat(r.getReply()).isEqualTo("Hello John Doe!"))
            .verifyComplete();
    }

    @Test
    void lotsOfReplies() {
        Flux<HelloResponse> response = this.stub
            .lotsOfReplies(HelloRequest.newBuilder().setGreeting("John Doe").build());
        StepVerifier.create(response)
            .assertNext(r -> assertThat(r.getReply()).isEqualTo("[00000] Hello John Doe!"))
            .assertNext(r -> assertThat(r.getReply()).isEqualTo("[00001] Hello John Doe!"))
            .assertNext(r -> assertThat(r.getReply()).isEqualTo("[00002] Hello John Doe!"))
            .assertNext(r -> assertThat(r.getReply()).isEqualTo("[00003] Hello John Doe!"))
            .assertNext(r -> assertThat(r.getReply()).isEqualTo("[00004] Hello John Doe!"))
            .assertNext(r -> assertThat(r.getReply()).isEqualTo("[00005] Hello John Doe!"))
            .assertNext(r -> assertThat(r.getReply()).isEqualTo("[00006] Hello John Doe!"))
            .assertNext(r -> assertThat(r.getReply()).isEqualTo("[00007] Hello John Doe!"))
            .assertNext(r -> assertThat(r.getReply()).isEqualTo("[00008] Hello John Doe!"))
            .assertNext(r -> assertThat(r.getReply()).isEqualTo("[00009] Hello John Doe!"))
            .verifyComplete();
    }

}
