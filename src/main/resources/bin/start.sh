#!/bin/sh
rm -f tpid

if [ ! -d "../log" ]; then
  mkdir ../log
fi

java -jar ../pybbs.jar > ../log/pybbs.log 2>&1 &
echo $! > tpid