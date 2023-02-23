FROM openjdk:11
EXPOSE 8888
ADD target/library-app-0.0.1-SNAPSHOT.jar library-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/library-app-0.0.1-SNAPSHOT.jar"]