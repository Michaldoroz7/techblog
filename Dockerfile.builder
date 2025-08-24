FROM maven:3.9.6-eclipse-temurin-21 AS techblog-builder

WORKDIR /app

COPY . .

RUN mvn clean package