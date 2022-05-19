FROM maven:3.8.1-openjdk-11-slim AS BUILD
#Kopiere alle Dateien
RUN mkdir -p /app
WORKDIR /app
COPY . /app/
#Build .jar file
RUN mvn package






#Finaler Container
FROM openjdk:11.0-jre-slim
RUN mkdir -p /app
COPY --from=BUILD /app/target/HTTP-Server-1.0-SNAPSHOT.jar /app


CMD ["java", "-jar", "/app/HTTP-Server-1.0-SNAPSHOT.jar"]