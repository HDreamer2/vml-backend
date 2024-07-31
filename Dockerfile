# 使用官方Java 1.8镜像作为基础镜像
FROM openjdk:8-jdk

# 设置容器内的工作目录，用于存放应用
WORKDIR /app

# 复制项目中的pom.xml和所有依赖到工作目录
COPY src/main/resources/ ./resources/
COPY src/main/java/ ./java/
COPY mvnw .
COPY pom.xml .

# 使用Maven在容器内构建项目
RUN ./mvnw clean package -DskipTests

# 声明容器运行时需要暴露的端口号，Spring Boot应用默认是8080
EXPOSE 8080

# 复制构建后的jar文件到工作目录
COPY target/*.jar ./app.jar

# 定义环境变量，用于Spring Boot应用的运行
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS="-Xmx512m -Xms256m"

# 运行Spring Boot应用
CMD ["java", "-jar", "app.jar"]