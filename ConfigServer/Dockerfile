FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/ConfigServer-*.jar /app/config-server.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/app/config-server.jar"]