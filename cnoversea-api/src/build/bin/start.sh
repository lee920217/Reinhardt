#!/bin/bash

set -x

#计算根路径
ROOT_DIR=`dirname $0`
ROOT_DIR=`(cd $ROOT_DIR/..; pwd)`
#echo "ROOT_DIR=$ROOT_DIR"
export ROOT_DIR=$ROOT_DIR

cd $ROOT_DIR

BIN_DIR=$ROOT_DIR/bin
CONF_DIR=$ROOT_DIR/conf
LOG_DIR=$ROOT_DIR/log
LIB_DIR=$ROOT_DIR/lib
DATA_DIR=$ROOT_DIR/data

source $BIN_DIR/env.sh

JAVA=$JAVA_HOME/bin/java
CLASSPATH=$CONF_DIR:$LIB_DIR/*:${CLASSPATH:-.}
#MAIN_CLASS=uk.co.cnoversea.api.Application
#MAIN_CLASS_ARGS=$CONF_DIR/config.properties
JAR_FILE=$LIB_DIR/`ls $LIB_DIR/`

#JMX_OPTS="$JMX_OPTS -Dcom.sun.management.jmxremote.port=12345"
#JMX_OPTS="$JMX_OPTS -Dcom.sun.management.jmxremote.authenticate=true"
#JMX_OPTS="$JMX_OPTS -Dcom.sun.management.jmxremote.ssl=false"
#JMX_OPTS="$JMX_OPTS -Dcom.sun.management.jmxremote.access.file=/usr/share/jdk/jre/lib/management/jmxremote.access.zhangxiaopeng1"
#JMX_OPTS="$JMX_OPTS -Dcom.sun.management.jmxremote.password.file=/usr/share/jdk/jre/lib/management/jmxremote.password.zhangxiaopeng1"

#$BIN_DIR/replace.sh ES_CLUSTER_HOSTS $CONF_DIR/application.properties elasticsearch.cluster-nodes.hosts

nohup $JAVA \
-Xms2048M \
-Xmx2048M \
-Xmn682M \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=$LOG_DIR/dump.log \
-server \
-cp $CLASSPATH \
-Dlog4j.configuration=file:$CONF_DIR/log4j.properties \
$JMX_OPTS \
$MAIN_CLASS \
$MAIN_CLASS_ARGS \
--spring.config.location=file:///$CONF_DIR/application.properties \
-jar $JAR_FILE \
> $LOG_DIR/nohup.out 2>&1 &

