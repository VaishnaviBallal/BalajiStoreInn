# Use a Maven + JDK image
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Set workdir
WORKDIR /app

# Copy pom and source code
COPY pom.xml .
COPY src ./src

# Build the jar
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Start app
CMD ["java", "-jar", "app.jar"]
