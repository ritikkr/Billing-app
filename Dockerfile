# -------- Build stage --------
FROM gradle:8.4.0-jdk17 AS build
WORKDIR /app

# Copy Gradle wrapper and warm the cache
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN chmod +x ./gradlew && ./gradlew build -x test || true

# Now copy the full source and re-build
COPY . .
RUN ./gradlew clean build -x test

# -------- Runtime stage --------
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app

# Copy the built JAR
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the correct port
EXPOSE 9090

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
