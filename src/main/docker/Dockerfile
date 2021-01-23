FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ARG profile=dev

ENV SPRING_PROFILES_ACTIVE ${profile}

COPY ${JAR_FILE} app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}"]

#docker build -t demo --build-arg  JAR_FILE=demo.jar --build-arg profile=test .