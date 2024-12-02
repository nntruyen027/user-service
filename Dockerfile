# Sử dụng hình ảnh OpenJDK phù hợp (thay thế openjdk:23-slim nếu không tồn tại)
FROM openjdk:23-slim AS base

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR đã build vào container
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service-0.0.1-SNAPSHOT.jar

# Expose cổng 8761 để truy cập Eureka Server
EXPOSE 8081

# Command để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]