FROM openjdk:18
ADD build/libs/login-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]