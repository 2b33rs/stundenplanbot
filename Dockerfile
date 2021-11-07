#
# Build stage
#
FROM maven:3.6.0-jdk-16-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:16-jre-slim
COPY --from=build /home/app/target/stundenplanbot-SNAPSHOT.jar /usr/local/lib/stundenplanbot.jar
#EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/stundenplanbot.jar"]