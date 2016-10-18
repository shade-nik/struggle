#!/bin/bash

TOMCAT_HOME=/home/halflight/applications/apache-tomcat-8.0.33
PROJECT_HOME=/home/halflight/repo/struggle/Struggle

rm -rf $TOMCAT_HOME/webapps/webapi.*

cp $PROJECT_HOME/WebApi/target/webapi.war $TOMCAT_HOME/webapps/

$TOMCAT_HOME/bin/catalina.sh start ; tail -f -n 500 $TOMCAT_HOME/logs/catalina.out


