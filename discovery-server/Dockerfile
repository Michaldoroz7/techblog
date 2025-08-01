FROM techblog-builder AS techblog-builder

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=techblog-builder /app/discovery-server/target/discovery-server-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]