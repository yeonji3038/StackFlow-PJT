# FROM openjdk:17-jdk-slim
# ADD BACK/build/libs/*.jar app.jar
# ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#
#
#
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY BACK/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
CMD []