FROM eclipse-temurin:21-jre-jammy AS base
LABEL authors="rojojun"

# 작업 디렉토리 설정
WORKDIR /app

RUN apt-get update \
    && apt-get install -y ffmpeg --no-install-recommends \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /app/dumps

COPY build/libs/*.jar app.jar

ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

# 애플리케이션이 사용할 포트 노출 (기본 8080)
EXPOSE 8080

# 컨테이너 시작 시 애플리케이션 실행 명령어
# Java 21의 향상된 기능 (가상 스레드 등)을 활용하려면 적절한 JVM 옵션 추가 가능
ENTRYPOINT ["java", "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/app/dump/heapdump.bin", "-jar", "/app/app.jar"]