FROM library/openjdk
ADD /build/libs/myRetailRestApi.jar /opt/dockerdemo/
CMD ["java", "-jar", "/opt/dockerdemo/myRetailRestApi.jar"]
EXPOSE 8081
				
	


