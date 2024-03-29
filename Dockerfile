#
# Build stage
#
FROM maven:3.8.3-amazoncorretto-16 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM adoptopenjdk/openjdk16:alpine-jre
COPY --from=build /home/app/target/stundenplanbot-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/local/lib/stundenplanbot.jar
COPY .env .
#EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/stundenplanbot.jar"]