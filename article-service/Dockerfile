FROM techblog-builder AS techblog-builder

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=techblog-builder /app/article-service/target/article-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
