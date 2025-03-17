# FROM openjdk:17-jdk-slim
# ADD BACK/build/libs/*.jar app.jar
# ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#
#
#
# OpenJDK 17을 베이스 이미지로 사용
FROM openjdk:17-jdk-slim

# 컨테이너 내 작업 디렉토리 설정
WORKDIR /app

# 빌드된 jar 파일을 컨테이너로 복사
COPY build/libs/*.jar app.jar

# 애플리케이션이 사용하는 포트 노출
EXPOSE 8080

# 컨테이너 시작 시 jar 파일 실행
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
