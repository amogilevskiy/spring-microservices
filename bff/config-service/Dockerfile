FROM maven:3.6.3-jdk-11-slim AS builder

WORKDIR /app

COPY pom.xml /app/pom.xml
RUN mvn -e -B dependency:resolve

COPY src ./src
RUN mvn -e -B -DskipTests package

FROM openjdk:11.0-jre-buster
EXPOSE 8888
COPY --from=builder /app/target/config-service-1.0.jar .

CMD ["java", "-jar", "config-service-1.0.jar"]