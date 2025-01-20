package ru.vladuss.gamesservice.configs;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vladuss.gamesservice.services.impl.GameGrpcServiceImpl;

@Configuration
public class GrpcConfig {

    @Autowired
    private GameGrpcServiceImpl gameGrpcService;

    @Bean
    public Server grpcServer() throws Exception {
        return ServerBuilder.forPort(9091)
                .addService(gameGrpcService)
                .build();
    }
}
