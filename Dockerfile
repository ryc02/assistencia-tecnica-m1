# Use Maven image for both build and runtime for simplicity and exact compatibility with AppLauncher
FROM maven:3.9-eclipse-temurin-17-alpine

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies (caching layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Compile the project
RUN mvn compile -B

# Set the port environment variable
ENV PORT=8080
EXPOSE 8080

# Run the application using the embedded Tomcat launcher
CMD ["mvn", "exec:java"]
