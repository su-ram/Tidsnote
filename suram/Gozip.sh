#!/bin/bash
CLASSPATH=/usr/local/java/jdk-12/lib/mysql-connector-java-8.0.15.jar:/usr/local/java/jdk-12:/usr/local/java/json-simple-1.1.1.jar
export CLASSPATH
cd /root/suram
java -classpath $CLASSPATH:. com.example.user.tidsnote.Server_suram