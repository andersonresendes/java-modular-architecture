FROM adoptopenjdk/openjdk11:ubi

RUN mkdir /opt/app

COPY app/spring-app/target/*.jar app.jar
ADD app/spring-app/target .

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
