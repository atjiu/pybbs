#!/bin/sh
rm -f tpid
nohup java -jar ../pybbs.jar > /dev/null 2>&1 &
echo $! > tpid