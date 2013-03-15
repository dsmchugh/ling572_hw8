#!/bin/sh
BASEDIR=$(dirname $0)
JAVA_HOME=/opt/jdk1.7.0_06
PATH=$JAVA_HOME/bin:$PATH
cd $BASEDIR/hw8; sbt assembly
