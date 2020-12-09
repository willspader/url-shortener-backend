FROM adoptopenjdk/openjdk11:latest
EXPOSE 8080
ADD build/libs/url-shortener-backend*.jar /opt/api.jar
ENTRYPOINT ["java","-Dspring.config.name=${PROPERTIES_FILE}","-jar","/opt/api.jar"]