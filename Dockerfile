#
# Build stage
#
FROM maven:3.8.3-amazoncorretto-16 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean compile package

#
# Package stage
#
FROM adoptopenjdk/openjdk16:alpine-jre
COPY --from=build /home/app/target/stundenplanbot.jar /usr/local/lib/stundenplanbot.jar
#EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/stundenplanbot.jar"]