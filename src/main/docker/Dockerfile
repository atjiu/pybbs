FROM frolvlad/alpine-oraclejdk8:slim
VOLUME ['/tmp','/app']
ADD yiiu.jar /app.jar
ADD views/ /views
ADD app.sh /app.sh
WORKDIR /app
EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh","-c","../app.sh"]