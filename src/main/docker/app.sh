#!/bin/sh
if [ ! -d "/app/views" ]; then
  cp -r /views /app/
fi
cd /app
java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar ../app.jar
