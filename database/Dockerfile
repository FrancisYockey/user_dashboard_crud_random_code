FROM mysql:8
ENV MYSQL_ROOT_PASSWORD=password
ARG JAR_FILE=target/*.jar
COPY ./data.sql /docker-entrypoint-initdb.d/
ENTRYPOINT ["java", "-jar", "/app.jar"]