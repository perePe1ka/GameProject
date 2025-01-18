FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/games-service-0.0.1-SNAPSHOT.jar domain-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "domain-service.jar"]
