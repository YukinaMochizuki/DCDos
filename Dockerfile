FROM adoptopenjdk/openjdk11

RUN mkdir /app
WORKDIR /app
COPY build/libs/*.jar app.jar

CMD "java" "-jar" "app.jar"
