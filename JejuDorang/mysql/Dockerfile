# mysql/Dockerfile

FROM mysql:8.0

# 필요한 경우 추가 설정 또는 패키지 설치
# 예: RUN apt-get update && apt-get install -y your-package

# 헬스 체크 스크립트 복사
COPY healthcheck.sh /usr/local/bin/healthcheck.sh
RUN chmod +x /usr/local/bin/healthcheck.sh

# 헬스 체크 설정
HEALTHCHECK CMD ["healthcheck.sh"]
