#!/bin/bash -e
java -jar pybbs.jar --spring.profiles.active=prod > log.file 2>&1 &
