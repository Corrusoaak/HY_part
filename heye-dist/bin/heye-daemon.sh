#!/usr/bin/env bash
################################################################################
#  deamon to start heye custom,...
################################################################################

# Start/stop a heye daemon.
USAGE="Usage: heye-daemon.sh (start|stop|stop-all) [args]"

STARTSTOP=$1
DAEMON=$2
ARGS=("${@:2}") # get remaining arguments as array

pid=$HEYE_PID_DIR/heye-$HEYE_IDENT_STRING-$DAEMON.pid
mkdir -p "$HEYE_PID_DIR"

# The lock is created on the PID directory since a lock file cannot be safely
# removed. The daemon is started with the lock closed and the lock remains
# active in this script until the script exits.
command -v flock >/dev/null 2>&1
if [[ $? -eq 0 ]]; then
    exec 200<"$HEYE_PID_DIR"
    flock 200
fi

# Ascending ID depending on number of lines in pid file.
# This allows us to start multiple daemon of each type.
id=$([ -f "$pid" ] && echo $(wc -l < $pid) || echo "0")

out="${HEYE_LOG_DIR}/heye-${DAEMON}.out"

mkdir -p "${HEYE_LOG_DIR}"

log_setting=("-Dlog4j.configuration=file:${HEYE_CONF_DIR}/log4j.properties")

case $STARTSTOP in

    (start)
        # Print a warning if daemons are already running on host
        if [ -f $pid ]; then
            echo "[INFO] $DAEMON are already running on $HOST."
        fi

        echo "Starting $DAEMON daemon on host $HOST."

        $RUNNER "${log_setting[@]}" -classpath "$CLASSPATH" ${CLASS_TO_RUN} "${ARGS[@]}" > "$out" 200<&- 2>&1 < /dev/null &

        mypid=$!

        # Add to pid file if successful start
        if [[ -n ${mypid} ]] && kill -0 $mypid > /dev/null 2>&1 ; then
            echo $mypid >> $pid
        else
            echo "Error starting $DAEMON daemon."
            exit 1
        fi
    ;;

    (stop)
        if [ -f $pid ]; then
            # Remove last in pid file
            to_stop=$(tail -n 1 $pid)

            if [ -z $to_stop ]; then
                rm $pid # If all stopped, clean up pid file
                echo "No $DAEMON daemon to stop on host $HOST."
            else
                sed \$d $pid > $pid.tmp # all but last line

                # If all stopped, clean up pid file
                [ $(wc -l < $pid.tmp) -eq 0 ] && rm $pid $pid.tmp || mv $pid.tmp $pid

                if kill -0 $to_stop > /dev/null 2>&1; then
                    echo "Stopping $DAEMON daemon (pid: $to_stop) on host $HOST."
                    kill $to_stop
                else
                    echo "No $DAEMON daemon (pid: $to_stop) is running anymore on $HOST."
                fi
            fi
        else
            echo "No $DAEMON daemon to stop on host $HOST."
        fi
    ;;

    (stop-all)
        if [ -f $pid ]; then
            mv $pid ${pid}.tmp

            while read to_stop; do
                if kill -0 $to_stop > /dev/null 2>&1; then
                    echo "Stopping $DAEMON daemon (pid: $to_stop) on host $HOST."
                    kill $to_stop
                else
                    echo "Skipping $DAEMON daemon (pid: $to_stop), because it is not running anymore on $HOST."
                fi
            done < ${pid}.tmp
            rm ${pid}.tmp
        fi
    ;;

    (*)
        echo "Unexpected argument '$STARTSTOP'. $USAGE."
        exit 1
    ;;

esac
