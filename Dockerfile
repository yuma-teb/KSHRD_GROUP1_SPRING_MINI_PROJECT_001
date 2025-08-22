FROM gradle:jdk21-ubi-minimal AS builder

WORKDIR /app

COPY build.gradle ./build.gradle
COPY gradlew ./gradlew
COPY settings.gradle ./setting.gradle
COPY gradle ./gradle

RUN chmod +x ./gradlew

RUN ./gradlew dependencies