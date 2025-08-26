FROM gradle:jdk21-ubi-minimal AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradlew ./gradlew
COPY gradle ./gradle

RUN chmod +x ./gradlew

RUN ./gradlew dependencies

COPY src ./src

RUN ./gradlew bootjar

FROM eclipse-temurin:21-jre

WORKDIR /app


COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "app.jar"]

