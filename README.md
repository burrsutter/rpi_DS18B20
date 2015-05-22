http://www.reuk.co.uk/DS18B20-Temperature-Sensor-with-Raspberry-Pi.htm
sudo nano /boot/config.txt
# add the following to the bottom of config.txt
dtoverlay=w1-gpio
# then reboot
sudo reboot
# after reboot
ls -l /sys/bus/w1/devices/
cat /sys/bus/w1/devices/28-0414505a09ff/w1_slave
# note: 28-0414505a09ff is the identifier of the temp sensor
# it is also used in the python code 

# wiring setup
https://www.dropbox.com/s/vh05s40x4kfz4hq/2015-05-11%2018.10.49.jpg?dl=0
https://www.dropbox.com/s/dgakxjkv7nc62bk/2015-05-11%2018.11.00.jpg?dl=0

# python usage
python ds18b20_read.py

# node usage
npm install ds18b20
node ds18b20_read.js

# Java usage
gradle readTemp
gradle readTempLoop
gradle readTempLoopMQTT