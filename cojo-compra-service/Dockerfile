FROM openjdk:11-jdk-slim
LABEL maintainer="Jose Manuel Martinez <20150378@ce.pucmm.edu.do>"
ENV NOMBRE_APP = 'cojo-compra-service'
EXPOSE 200-65000
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]