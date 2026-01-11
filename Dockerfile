# 阶段1：构建Jar包（可选，推荐用Maven本地打包，避免镜像体积过大）
# FROM maven:3.9.6-eclipse-temurin-21 AS builder
# WORKDIR /app
# COPY pom.xml .
# RUN mvn dependency:go-offline -B
# COPY src ./src
# RUN mvn clean package -DskipTests

# 1.基础镜像（核心）
FROM eclipse-temurin:21-jre-alpine

# 2.作者信息
LABEL maintainer="Frank325-m"

# 3.设置时区+字符集（解决中文乱码问题，关键！）
ENV TZ=Asia/Shanghai \
    LANG=en_US.UTF-8 \
    LC_ALL=en_US.UTF-8

# 4.安装基础工具（方便排除问题，生产环境可删除）
#RUN apk add --no-cache tzdata curl bash
#RUN apk add --no-cache tzdata redis

# 5.设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 6.创建非root用户（安全）
RUN addgroup -S appgroup && adduser -S appuser -G appgroup -h /app

# 7.创建工作目录并授权
WORKDIR /app
RUN mkdir -p /app/logs /app/tmp

# 8.复制jar包（重点！确保本地target目录有这个jar包）
# 注意：如果用阶段1构建，替换为COPY --from=builder /app/target/*.jar /app/app.jar
COPY target/springbootcrud-0.0.1-SNAPSHOT.jar /app/app.jar
RUN chown -R appuser:appgroup /app
# 验证Jar包是否存在（调试用，生产环境可删除）
# RUN ls -l /app && jar tf /app/app.jar | grep BOOT-INF

# 9.挂载卷（用于持久化日志等）
VOLUME /app/tmp
VOLUME /app/log

# 10.切换非root用户
USER appuser

# 11.暴露端口
EXPOSE 8080

# 12.JVM优化参数（根据服务器配置调整）
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/app/logs/heapdump.hprof -Dfile.encoding=UTF-8"

# 启动名利
ENTRYPOINT ["sh", "-c", "echo '启动参数：$JAVA_OPTS' && java $JAVA_OPTS -jar /app/app.jar"]