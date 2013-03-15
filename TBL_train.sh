#!/bin/sh
BASEDIR=$(dirname $0)
/opt/jdk1.7.0_06/bin/java -classpath $BASEDIR/hw8/target/hw8.jar ling572.DriverTrain  $@
