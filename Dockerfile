FROM amazoncorretto:17
ARG JAR_FILE=build/libs/Linker-API.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]