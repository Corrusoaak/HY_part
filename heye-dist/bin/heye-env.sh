#!/usr/bin/env bash

#JAVA_HOME - the java which to used
#Online Mode
export JAVA_HOME=/home/heye/softwares/jdk-1.8

export HEYE_HOME=`cd $(dirname "$0"); cd ..; pwd`
export HEYE_BIN_DIR=${HEYE_HOME}/bin
export HEYE_PID_DIR=${HEYE_HOME}/pids
export HEYE_LOG_DIR=${HEYE_HOME}/logs

export HOST=`hostname -f`

# Get the java binary
if [ -n "${JAVA_HOME}" ]; then
  RUNNER="${JAVA_HOME}/bin/java"
else
  if [ `command -v java` ]; then
    RUNNER="java"
  else
    echo "JAVA_HOME is not set" >&2
    exit 1
  fi
fi
export RUNNER


if [ "$HEYE_IDENT_STRING" = "" ]; then
    export HEYE_IDENT_STRING="$USER"
fi

cd ${HEYE_HOME}
