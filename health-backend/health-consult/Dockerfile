#FROM java:8
FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8040

# Image创建容器时的初始化内存，最大内存，及启动时使用的profile. -c为清除以前启动的数据
ENTRYPOINT ["java","-Xms512m","-Xmx512m","-jar","/app.jar","--spring.profiles.active=prod","-c"]