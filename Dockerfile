# Stage 1: Build the application with Maven
FROM maven:3.8.5-openjdk-17 AS build
LABEL maintainer="zeppsolutions1966"

# Set the working directory for the build process
WORKDIR /app

# Copy only the pom.xml first to leverage Docker caching for dependencies
COPY pom.xml .

# Download dependencies (this won't rebuild every time unless pom.xml changes)
RUN mvn dependency:go-offline

# Now copy the rest of the source code
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the final image with the compiled JAR file
FROM openjdk:17-jdk-slim

# Set the working directory for the runtime container
WORKDIR /app

# Set environment variables for the database connection
#ENV SPRING_DATASOURCE_URL=jdbc:postgresql://my-postgres:5432/blog-apis
#ENV SPRING_DATASOURCE_USERNAME=postgres
#ENV SPRING_DATASOURCE_PASSWORD=The/koufax32

# Copy the compiled JAR file from the build stage
COPY --from=build /app/target/spring-boot-docker.jar spring-boot-docker.jar

# Expose port 8080 for the Spring Boot application
EXPOSE 8080

# Use ENTRYPOINT to run the JAR file
ENTRYPOINT ["java", "-jar", "spring-boot-docker.jar"]