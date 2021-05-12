#基础镜像
FROM java:8

# 镜像制作人
MAINTAINER yangyouwang

# 在容器中创建挂载点，可以多个
VOLUME ["/tmp"]

# 定义参数
ARG JAR_FILE

# 文件到镜像
ADD ${JAR_FILE} app.jar

# 端口
EXPOSE 8080

# 执行命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]