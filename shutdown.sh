#!/bin/bash -e
ps -ef | grep pybbs.jar | grep -v grep | cut -c 9-15 | xargs kill
