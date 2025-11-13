FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copy ไฟล์ที่จำเป็นสำหรับการ Build
COPY pom.xml .
COPY src ./src

# Build Project และข้ามการ Test
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

# กำหนด Volume (ทางเลือก)
VOLUME /tmp

# Argument สำหรับไฟล์ JAR
ARG JAR_FILE=target/*.jar

# คัดลอกไฟล์ JAR ที่สร้างเสร็จแล้วจาก Stage 'build'
COPY --from=build /app/${JAR_FILE} app.jar

# สั่งให้ Container รันแอปพลิเคชัน Spring Boot
ENTRYPOINT ["java", "-jar", "/app.jar"]