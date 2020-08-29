FROM adoptopenjdk/openjdk11
LABEL maintainer="inspire12<ox4443@naver.com>"
# Add a volume pointing to /tmp
VOLUME /tmp

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV PROFILE=local
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=${PROFILE}","-jar","app.jar"]
