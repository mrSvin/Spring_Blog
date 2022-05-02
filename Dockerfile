FROM adoptopenjdk/openjdk11:jre-11.0.11_9
ARG JAR_FILE=target/bookLibrary-1.0-SNAPSHOT.jar
ADD . /src
WORKDIR /src
EXPOSE 8081
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]

#docker build -t spring-docker-simple:0.0.1 .
#docker run -d -p 8081:8081 -t spring-docker-simple:0.0.1