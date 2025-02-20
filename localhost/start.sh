#!/bin/bash

# Latest build version
VERSION="0.0.3"
# Application port
PORT="9500"

# Jar file name
JAR_NAME="N8N_RestAPI-$VERSION.jar"

# Need Java 17 to run
JAVA_PATH="/Users/milu/Library/Java/JavaVirtualMachines/openjdk-17.0.2/Contents/Home/bin"

# Java Memory Settings
MIN_JAVA_MEMORY="128M"
MAX_JAVA_MEMORY="1024M"


lsof -i tcp:$PORT -s tcp:listen -t | xargs kill -9

# Start java parameters
$JAVA_PATH/java -Xms$MIN_JAVA_MEMORY -Xmx$MAX_JAVA_MEMORY -jar $JAR_NAME
