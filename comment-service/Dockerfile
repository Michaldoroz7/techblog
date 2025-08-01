FROM techblog-builder AS techblog-builder

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=techblog-builder /app/comment-service/target/comment-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
