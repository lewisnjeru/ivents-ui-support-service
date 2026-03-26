# ==============================
# Build stage
# ==============================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ==============================
# Run stage
# ==============================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8081

# Default environment variable for Spring profile
ENV SPRING_PROFILES_ACTIVE=prod

# Run the jar
ENTRYPOINT ["java","-jar","app.jar"]