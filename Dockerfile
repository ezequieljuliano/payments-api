FROM openjdk:8-jdk-alpine
COPY target/payments-api-*.jar /app/payments-api.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app/payments-api.jar"]