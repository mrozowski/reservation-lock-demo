FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/\*.jar
COPY ${JAR_FILE} reservation_backend.jar
ENTRYPOINT ["java","-jar","/reservation_backend.jar"]