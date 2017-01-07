FROM ubuntu:14.04
RUN apt-get update
RUN apt-get -y install python-software-properties
RUN apt-get -y software-properties-common
RUN add-apt-repository ppa:webupd8team/Java
RUN apt-get update
RUN apt-get install Oracle-java8-installer
RUN debconf-set-selections <<< 'mysql-server mysql-server/root_password password 123123'
RUN debconf-set-selections <<< 'mysql-server mysql-server/root_password_again password 123123'
RUN apt-get -y install mysql-server
RUN git clone https://github.com/tomoya92/pybbs /home/github/pybbs
RUN apt-get install -y maven
RUN cd /home/github/pybbs && mvn clean package -Dmaven.test.skip=true
RUN cd /home/github/pybbs/target && java -jar pybbs-2.4.jar
EXPOSE 8080