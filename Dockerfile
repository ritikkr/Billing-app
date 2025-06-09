# -------- Build stage --------
FROM gradle:8.4.0-jdk17 AS build
WORKDIR /app

# Copy only what's needed first
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Make the wrapper executable
RUN chmod +x gradlew

# Warm Gradle dependencies (optional, helps cache)
RUN ./gradlew build -x test || true

# Copy full project (do this AFTER chmod)
COPY . .

# Just make sure gradlew is executable again (copy above may overwrite it)
RUN chmod +x gradlew

# Run actual build
RUN ./gradlew clean build -x test

# -------- Runtime stage --------
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]
