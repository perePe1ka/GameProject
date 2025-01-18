FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/games-gateway-0.0.1-SNAPSHOT.jar gateway.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gateway.jar"]
