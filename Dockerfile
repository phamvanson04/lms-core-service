# syntax=docker/dockerfile:1.7

FROM gradle:8.7-jdk21 AS build
WORKDIR /workspace

COPY gradlew gradlew.bat settings.gradle build.gradle gradle.properties ./
COPY gradle ./gradle
COPY src ./src
RUN chmod +x gradlew
RUN --mount=type=cache,target=/home/gradle/.gradle \
	./gradlew --no-daemon clean bootJar -x test
RUN cp build/libs/*.jar /workspace/app.jar

FROM gcr.io/distroless/java21-debian12:nonroot
WORKDIR /app

COPY --from=build /workspace/app.jar /app/app.jar

EXPOSE 8081
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
