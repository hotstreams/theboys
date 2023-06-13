FROM maven:3.8.5-openjdk-17-slim AS build

RUN mkdir /app
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:resolve-plugins dependency:resolve

COPY . .
RUN mvn package -Dmaven.test.skip

FROM openjdk:17-alpine
COPY --from=build /app/target/backend-1.0-SNAPSHOT.jar /app/backend-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/backend-1.0-SNAPSHOT.jar"]
