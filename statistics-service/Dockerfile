FROM techblog-builder AS techblog-builder

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=techblog-builder /app/statistics-service/target/statistics-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]
