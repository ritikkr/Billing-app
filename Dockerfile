FROM gradle:8.4.0-jdk17 AS build
WORKDIR /app

# Copy only wrapper files first, then give execution permission
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew

# Warm the Gradle cache
RUN ./gradlew build -x test || true

# Now copy the rest of the code
COPY . .

# Run the full build
RUN ./gradlew clean build -x test
