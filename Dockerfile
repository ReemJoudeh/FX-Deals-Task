FROM openjdk:17

#WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

#COPY target/fx-deals-1.0.0.jar app.jar
#COPY config/application.properties /app/config/application.properties
#COPY config/log4j2.xml /app/config/log4j2.xml

# Expose the application port
EXPOSE 8080

# Run the App
ENTRYPOINT ["java", "-jar", "/app.jar"]

#ENTRYPOINT ["java", "-jar", "app.jar"]
