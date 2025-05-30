FROM tomcat:10.1.18-jdk17-temurin

# Clean default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose Tomcat default port
EXPOSE 8080

CMD ["catalina.sh", "run"]

RUN echo "Hello my name is Aniket"
RUN echo "Hello my name is Aniket"
