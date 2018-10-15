#!/bin/sh
APP_JAR=$(ls *.jar)
command="java -jar $APP_JAR"
echo $command
exec $command