#!/bin/sh
rm -f tpid
nohup /home/jdk/jdk1.8.0_201/bin/java -Xms256m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m -Dfile.encoding=utf-8 -jar bt-txchat-boot-1.0.0.jar | /usr/local/sbin/cronolog ./logs/console-%Y-%m-%d.log >> /dev/null 2>&1 &
echo $! > tpid
echo Start Success!
