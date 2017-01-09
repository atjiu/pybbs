FROM ubuntu:16.04
RUN apt-get update
RUN mkdir /home/jdk && cd /home/jdk
RUN apt-get install -y wget
RUN wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u111-b14/jdk-8u111-linux-x64.tar.gz
RUN tar -zxvf jdk-8u111-linux-x64.tar.gz
ENV JAVA_HOME /home/jdk/jdk1.8.0_111
ENV PATH $PATH:$JAVA_HOME/bin
RUN apt-get -y install mysql-server
RUN /etc/init.d/mysql start \
    && mysql -uroot -e "grant all privileges on *.* to 'root'@'%' identified by '123123';" \
    && mysql -uroot -e "grant all privileges on *.* to 'root'@'localhost' identified by '123123';"
RUN sed -Ei 's/^(bind-address|log)/#&/' /etc/mysql/my.cnf \
    && echo 'skip-host-cache\nskip-name-resolve' | awk '{ print } $1 == "[mysqld]" && c == 0 { c = 1; system("cat") }' /etc/mysql/my.cnf > /tmp/my.cnf \
    && mv /tmp/my.cnf /etc/mysql/my.cnf
RUN git clone https://github.com/tomoya92/pybbs /home/github/pybbs
RUN apt-get install -y maven
RUN cd /home/github/pybbs && mvn clean package -Dmaven.test.skip=true
RUN cd /home/github/pybbs/target && java -jar pybbs-2.4.jar
EXPOSE 8080
CMD ["/usr/bin/mysqld_safe"]