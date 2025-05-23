#!/system/bin/sh
echo "server"
echo "Start Logging..." > /data/local/tmp/SincoGamer.log
date >> /data/local/tmp/SincoGamer.log

setenforce 0 >> /data/local/tmp/SincoGamer.log 2>&1 


rm -rf /data/local/tmp/.SincoGamer >> /data/local/tmp/SincoGamer.log  2>&1 

mkdir /data/local/tmp/.SincoGamer  >> /data/local/tmp/SincoGamer.log 2>&1
chmod 777 /data/local/tmp/.SincoGamer >> /data/local/tmp/SincoGamer.log  2>&1 
mkdir /data/local/tmp/.SincoGamer/dalvik-cache >> /data/local/tmp/SincoGamer.log 2>&1
chmod 777 /data/local/tmp/.SincoGamer/dalvik-cache >> /data/local/tmp/SincoGamer.log 2>&1 

dd if=/sdcard/SincoGamer/sincoserver.dex of=/data/local/tmp/.SincoGamer/sincoserver.dex >> /data/local/tmp/SincoGamer.log 2>&1

chmod 777 /data/local/tmp/.SincoGamer/sincoserver.dex >> /data/local/tmp/SincoGamer.log 2>&1    
chown shell /data/local/tmp/.SincoGamer/sincoserver.dex >> /data/local/tmp/SincoGamer.log 2>&1    
export CLASSPATH=/data/local/tmp/.SincoGamer/sincoserver.dex >> /data/local/tmp/SincoGamer.log 2>&1
trap \"\" HUP
exec app_process /data/local/tmp/.SincoGamer --nice-name=sincoserver com.jieli.adb.shell.server.JavaMain 1 2 3 >> /data/local/tmp/SincoGamer.log 2>&1 &

echo "server end" >> /data/local/tmp/SincoGamer.log