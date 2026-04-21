FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# 🔥 ระบุชื่อไฟล์ตรง ๆ กันพลาด
COPY target/user-menagement-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]