FROM techblog-builder AS techblog-builder

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=techblog-builder /app/api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
