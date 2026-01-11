FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN mvn -B -pl statistics-service -am package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/statistics-service/target/*jar app.jar

EXPOSE 8084

ENTRYPOINT ["java","-jar","app.jar"]
