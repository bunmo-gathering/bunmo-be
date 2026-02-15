# ===== 1. Build Stage =====
FROM --platform=$BUILDPLATFORM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# 1-1. Gradle 빌드 환경 복사 (캐시 활용을 위해 소스보다 먼저 복사)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# 1-2. gradlew 실행 권한 부여
RUN chmod +x ./gradlew

# 1-3. 의존성 다운로드 (소스코드 변경 시에도 이 레이어는 캐시됨)
RUN ./gradlew dependencies --no-daemon

# 1-4. 소스 코드 복사 및 빌드
COPY src src
RUN ./gradlew build -x test --no-daemon

# 1-5. Layered JAR 추출 (레이어별 캐싱 최적화)
RUN java -Djarmode=layertools -jar build/libs/*.jar extract

# ===== 2. Run Stage =====
FROM eclipse-temurin:17-jre
WORKDIR /app

# 2-1. 보안: root가 아닌 별도 계정 생성 및 사용
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

# 2-2. 타임존 설정 (한국 시간)
ENV TZ=Asia/Seoul

# 2-3. Layered JAR 복사 (변경 빈도 낮은 순서대로 - 캐시 최적화)
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

# 2-4. 비-root 사용자로 전환
USER appuser
EXPOSE 18080

# 2-5. 실행 (컨테이너 환경 최적화 JVM 옵션 포함)
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "org.springframework.boot.loader.launch.JarLauncher"]