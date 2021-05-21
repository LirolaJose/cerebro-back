FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=target/cerebro-1.0.2-SNAPSHOT.jar

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"] 