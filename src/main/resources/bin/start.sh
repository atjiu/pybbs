#!/bin/sh
rm -f tpid

if [ ! -d "../log" ]; then
  mkdir ../log
fi

java -jar ../yiiu.jar > ../log/yiiu.log 2>&1 &
echo $! > tpid