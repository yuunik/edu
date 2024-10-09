FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/edu.jar edu.jar
ENTRYPOINT ["java","-jar","/edu.jar", "&"]