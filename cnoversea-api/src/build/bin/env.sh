#!/bin/bash

export JAVA_HOME=/usr/share/jdk
export JRE_HOME=$JAVA_HOME/jre
export M2_HOME=/usr/share/maven

export CLASSPATH=.:$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt/jar:$JRE_HOME/lib/rt.jar
export PATH=$JAVA_HOME/bin:$M2_HOME/bin:$PATH

