FROM maven:3.6.0-jdk-11 AS build
COPY . ./
RUN mvn clean package -DskipTests

FROM openjdk:11
COPY --from=build target/*.jar ./app.jar
COPY src/main/resources/storage/room-storage src/main/resources/storage/room-storage
COPY src/main/resources/storage/user-storage src/main/resources/storage/user-storage
ENTRYPOINT ["java","-jar","./app.jar"]