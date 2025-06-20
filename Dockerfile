#Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

#Set the working directory in the container
WORKDIR /app

#Copy the Gradle wrapper and project files to the container
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .
COPY src/ src/
#COPY .env .
#Make the Gradle wrapper executable
RUN chmod +x gradlew

#Download the Gradle distribution
RUN ./gradlew --version

#Build the application
RUN ./gradlew build -x test

#Run the application#
CMD ["java", "-jar", "build/libs/gallery-0.0.1-SNAPSHOT.jar"]