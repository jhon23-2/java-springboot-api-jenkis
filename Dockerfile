FROM eclipse-temurin:17-jre

WORKDIR /app

# Run as a non-root user
RUN useradd --system --create-home appuser
USER appuser

# The jar is produced by "mvn verify" in the Jenkins Build & Test stage
COPY --chown=appuser target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]