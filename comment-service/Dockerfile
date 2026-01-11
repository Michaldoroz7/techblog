FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN mvn -B -pl comment-service -am package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/comment-service/target/*jar app.jar

EXPOSE 8083

ENTRYPOINT ["java","-jar","app.jar"]
