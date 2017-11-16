FROM library/openjdk
ADD /build/libs/myRetailRestApi-0.0.1-SNAPSHOT.jar /opt/myretail/
CMD ["java", "-jar", "/opt/myretail/myRetailRestApi-0.0.1-SNAPSHOT.jar"]
EXPOSE 8082