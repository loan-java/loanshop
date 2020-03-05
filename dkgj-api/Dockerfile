FROM java:8
EXPOSE 8081

VOLUME /tmp
ADD dkgj-api.jar /app-api.jar
RUN bash -c 'touch /app-api.jar'
ENTRYPOINT ["java","-jar","/app-api.jar"]
