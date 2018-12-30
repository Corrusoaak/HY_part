#!/usr/bin/env bash

USAGE="Usage: heye-server.sh (start|stop)"
if [[ $# -ne 1 ]]
then
    echo ${USAGE}
    exit 1
fi
. "$(dirname $0)"/heye-env.sh

STARTSTOP=$1
HEYE_SERVER="HeyeServer"
HEYE_SERVER_LIB_DIR=${HEYE_HOME}/heye-server/lib
HEYE_SERVER_CLASSPATH=`ls $HEYE_SERVER_LIB_DIR|grep .jar|awk '{print "'$HEYE_SERVER_LIB_DIR'/"$1}'|tr "\n" ":"`

export CLASS_TO_RUN=com.heye.crm.server.HeYeServerApplication
export HEYE_CONF_DIR=$HEYE_HOME/heye-server/conf
export CLASSPATH=$HEYE_CONF_DIR:$HEYE_SERVER_CLASSPATH"${HEYE_BIN_DIR}"/heye-daemon.sh $STARTSTOP ${HEYE_SERVER}

"${HEYE_BIN_DIR}"/heye-daemon.sh $STARTSTOP ${HEYE_SERVER}
