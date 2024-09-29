# Dockerfile

FROM openjdk:17-jdk-slim

# Chromedriver 설치
# RUN apt-get update && \
#    apt-get install -y wget unzip chromium-driver

# Chromedriver 경로 설정
ENV CHROMEDRIVER_PATH=/usr/bin/chromedriver

# 앱 디렉토리 설정
WORKDIR /app

# Gradle Wrapper 사용 시
COPY gradlew /app/
COPY gradle /app/gradle

# 소스 코드 복사
COPY . /app

# 실행 권한 부여
RUN chmod +x /app/gradlew

# 애플리케이션 빌드
RUN /app/gradlew build -x test

# wait-for-it.sh 스크립트 복사
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# ENTRYPOINT 수정
ENTRYPOINT ["/wait-for-it.sh", "db:3306", "--", "java", "-jar", "/app/build/libs/JejuDorang-0.0.1-SNAPSHOT.jar"]
