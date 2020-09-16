FROM openjdk:8-jdk

COPY ./viewer4doc /viewer4doc
WORKDIR /viewer4doc

CMD ["./gradlew", "bootRun"]