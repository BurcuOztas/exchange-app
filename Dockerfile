# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk
WORKDIR /app

COPY --from=build /app/target/exchange-app-0.0.1-SNAPSHOT.jar exchange-app.jar

ENTRYPOINT ["java", "-Xshare:off", "-jar", "exchange-app.jar"]
