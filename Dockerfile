FROM ubuntu:16.04
RUN apt-get update
RUN mkdir /home/jdk && cd /home/jdk
RUN wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u111-b14/jdk-8u111-linux-x64.tar.gz
RUN tar -zxvf jdk-8u111-linux-x64.tar.gz
RUN echo 'export JAVA_HOME=/home/jdk/jdk1.8.0_111' >> /etc/profile && echo 'export PATH=$PATH:$JAVA_HOME/bin' >> /etc/profile && source /etc/profile
RUN debconf-set-selections <<< 'mysql-server mysql-server/root_password password 123123'
RUN debconf-set-selections <<< 'mysql-server mysql-server/root_password_again password 123123'
RUN apt-get -y install mysql-server
RUN git clone https://github.com/tomoya92/pybbs /home/github/pybbs
RUN apt-get install -y maven
RUN cd /home/github/pybbs && mvn clean package -Dmaven.test.skip=true
RUN cd /home/github/pybbs/target && java -jar pybbs-2.4.jar
EXPOSE 8080