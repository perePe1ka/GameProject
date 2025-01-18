package ru.vladuss.gamesservice;

import io.grpc.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GamesServiceApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(GamesServiceApplication.class);

    @Autowired
    private Server grpcServer;

    public static void main(String[] args) {
        SpringApplication.run(GamesServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        LOGGER.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< gRPC start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        grpcServer.start();
        grpcServer.awaitTermination();
    }
}
