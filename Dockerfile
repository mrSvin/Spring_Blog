#Oбраз создаётся на основе alpine linux с установленной openjdk11
FROM adoptopenjdk/openjdk11:jre-11.0.11_9
#Переменной JAR_FILE присваивается путь к jar- архиву
ARG JAR_FILE=target/bookLibrary-1.0-SNAPSHOT.jar
#Назначаем рабочую директорию, в которой будут выполняться дальнейшие команды (перемещаемся в папку app)
ADD . /src
WORKDIR /src
#Задаем порт, на котором будет запущен контейнер
EXPOSE 8081
#Наш jar-файл, указанный в JAR_FILE, копируется в папку app, и копии задаётся имя app.jar
COPY ${JAR_FILE} app.jar
#jar-файл запускается, собирается команда java -jar app.jar из заданной рабочей директории
ENTRYPOINT ["java","-jar","app.jar"]

#docker build -t spring-docker-simple:0.0.1 .
#docker run -d -p 8081:8081 -t spring-docker-simple:0.0.1