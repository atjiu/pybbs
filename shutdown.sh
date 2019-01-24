ps -ef | grep pybbs-5.1.0.jar | grep -v grep | cut -c 9-15 | xargs kill -s 9
