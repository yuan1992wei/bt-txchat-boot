FROM centos7env:1.2
MAINTAINER batain

COPY ./libraries/libWeWorkFinanceSdk_Java.so /usr/lib64/


#RUN chown -RL mysql:mysql /var/run/mysqld/
#RUN mysql –uroot –proot < /home/ry_20191008.sql
#RUN mysql –uroot –proot < /home/ry_20191008.sql

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /zk-boot/{console,logs}
WORKDIR /bt-tx-boot

EXPOSE  8085

ADD ./target/bt-txchat-boot-1.0.0.jar ./
COPY ./start.sh ./
COPY ./stop.sh ./
RUN chmod -R 777 ./*
#ENTRYPOINT ["/bin/sh","-c","./start.sh"]
#ENTRYPOINT   ["/home/jdk/jdk1.8.0_201/bin/java","-jar","bt-txchat-boot-0.0.1-SNAPSHOT.jar"]
#             &&nohup /home/jdk/jdk1.8.0_201/bin/java -Djava.security.egd=file:/dev/./urandom -jar ruoyi.jar | /usr/local/sbin/cronolog ./logs/console-%Y-%m-%d.log > /dev/null 2>&1 &
#             &&nohup /home/jdk/jdk1.8.0_201/bin/java -Djava.security.egd=file:/dev/./urandom -jar ruoyi.jar | /usr/local/sbin/cronolog ./logs/console-%Y-%m-%d.log > /dev/null 2>&1 &
#ENTRYPOINT /usr/sbin/init

