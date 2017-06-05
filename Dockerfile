
FROM maven:alpine

COPY . /app/pybbs

WORKDIR  /app/pybbs

RUN mvn -Dmaven.test.skip=true package && \
  mv target/pybbs-2.4.2.jar /app/ && \
  rm -fr /app/pybbs

EXPOSE 8080

CMD ["java", "-jar", "/app/pybbs-2.4.2.jar"]
