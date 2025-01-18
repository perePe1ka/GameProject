package ru.vladuss.gamesgateway.configs;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {
    @Bean
    public ManagedChannel grpcChannel() {
        return ManagedChannelBuilder.forAddress("domain", 9091)
                .usePlaintext()
                .build();
    }

    @Bean
    public com.example.gameproto.GameServiceGrpc.GameServiceBlockingStub GameServiceStub(ManagedChannel channel) {
        return com.example.gameproto.GameServiceGrpc.newBlockingStub(channel);
    }
}
