FROM openjdk:25-jdk-slim
WORKDIR /app
# Kopiraj Maven wrapper i pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
# Download dependencies
RUN ./mvnw dependency:go-offline -B
# Kopiraj source code
COPY src src
# Build aplikacija
RUN ./mvnw package -DskipTests
# Expose port
EXPOSE 8080
# Run aplikacija
CMD ["java", "-jar", "target/muzej-backend-1.0.0.jar"]
