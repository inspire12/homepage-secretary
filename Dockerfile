FROM adoptopenjdk/openjdk8:x86_64-alpine-jre8u222-b10
WORKDIR .
ARG JAR_FILE
COPY target/${JAR_FILE} app.jar
ENV PROFILE=local
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=${PROFILE}","-jar","app.jar"]



