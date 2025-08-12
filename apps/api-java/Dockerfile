FROM gradle:8.8-jdk17 AS build
WORKDIR /home/gradle/project
COPY . .
RUN gradle clean bootJar --no-daemon
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
