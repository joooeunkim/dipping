FROM openjdk:11-jdk
VOLUME /tmp
EXPOSE 3001
ARG JAR_FILE=build/libs/dipping-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
