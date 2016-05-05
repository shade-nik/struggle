#!/bin/bash

TOMCAT_HOME=$HOME/applications/apache-tomcat-8.0.33
PROJECT_HOME=$HOME/repo/struggle
PROJECT_NAME=webapi

rm -rf $TOMCAT_HOME/webapps/$PROJECT_NAME
rm -rf $TOMCAT_HOME/webapps/$PROJECT_NAME.war

cp $PROJECT_HOME/Struggle/WebApi/target/$PROJECT_NAME.war $TOMCAT_HOME/webapps/


