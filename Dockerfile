FROM maven:3.8.5-openjdk-17 AS builder
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=builder target/*.jar demo.jar
EXPOSE 8080
CMD ["java","-jar","demo.jar"]