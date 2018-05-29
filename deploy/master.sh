if [ "$1" == "run" ]; then
java -jar "Master-1.0-SNAPSHOT.jar" --server.port="$2"
else if [ "$1" == "start" ]; then
nohup java -jar "Master-1.0-SNAPSHOT.jar" --server.port="$2" >master"$2".txt &
echo "Master is starting."
else if [ "$1" == "stop" ]; then
PID=$(ps -ef | grep "Master-1.0-SNAPSHOT.jar" | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]; then
echo Application is already stopped
else
echo kill $PID
kill $PID
fi
else if [ "$1" == "status" ]; then
PID=$(ps -ef | grep "Master-1.0-SNAPSHOT.jar" | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]; then
echo Application is stopped
else
echo Application is running
echo $PID
fi
fi
fi
fi
fi
