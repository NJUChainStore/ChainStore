sudo chmod +x master.sh
sudo chmod +x webservice.sh
sudo chmod +x mining.sh

if [ "$1" == "run" ]; then
./master.sh run
tail -f -n 1 master.txt | echo | grep -qe "Started Master"
./mining.sh run
./webservice.sh run
else if [ "$1" == "start" ]; then
./master.sh start
tail -f master.txt | while read LOGLINE
do
[[ "${LOGLINE}" == *"Started Master"* ]] && pkill -P $$ tail
done
./mining.sh start
./webservice.sh start
else if [ "$1" == "stop" ]; then
./master.sh stop
./mining.sh stop
./webservice.sh stop
if [ -z "$PID" ]; then
echo Application is already stopped
else
echo kill $PID
kill $PID
fi
else if [ "$1" == "status" ]; then
./master.sh status
./mining.sh status
./webservice.sh status
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

