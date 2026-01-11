FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN mvn -B -pl discovery-server -am clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/discovery-server/target/discovery-server-*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar","app.jar"]
