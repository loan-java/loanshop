FROM java:8
EXPOSE 8080

VOLUME /tmp
ADD dkgj-admin.jar /app-admin.jar
RUN bash -c 'touch /app-admin.jar'
ENTRYPOINT ["java","-jar","/app-admin.jar"]
