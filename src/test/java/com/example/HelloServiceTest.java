package com.example;

import com.example.proto.HelloRequest;
import com.example.proto.HelloResponse;
import com.example.proto.HelloServiceGrpc;
import com.google.common.collect.Streams;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.grpc.client.default-channel.address=0.0.0.0:${local.server.port}")
class HelloServiceTest {

    @Autowired
    HelloServiceGrpc.HelloServiceBlockingStub stub;

    @Test
    void sayHello() {
        HelloResponse response = this.stub.sayHello(HelloRequest.newBuilder().setGreeting("John Doe").build());
        assertThat(response.getReply()).isEqualTo("Hello John Doe!");
    }

    @Test
    void lotsOfReplies() {
        Iterator<HelloResponse> response = this.stub
            .lotsOfReplies(HelloRequest.newBuilder().setGreeting("John Doe").build());
        List<String> replies = Streams.stream(response).map(HelloResponse::getReply).toList();
        assertThat(replies).containsExactly("[00000] Hello John Doe!", "[00001] Hello John Doe!",
                "[00002] Hello John Doe!", "[00003] Hello John Doe!", "[00004] Hello John Doe!",
                "[00005] Hello John Doe!", "[00006] Hello John Doe!", "[00007] Hello John Doe!",
                "[00008] Hello John Doe!", "[00009] Hello John Doe!");
    }

}
