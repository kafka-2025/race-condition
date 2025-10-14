FROM openjdk:17-jdk-slim

COPY ./build/libs/*-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-Dserver.port=8081", "-Dspring.profiles.active=dev", "-Duser.timezone=Asia/Seoul", "-jar", "./app.jar"]

