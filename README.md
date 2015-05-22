http://www.reuk.co.uk/DS18B20-Temperature-Sensor-with-Raspberry-Pi.htm

# Enabling 1-wire
To enable the 1-wire module and drivers, you would need to add a dtoverlay=w1-gpio line to the /boot/config.txt file of the default raspbian image. This has already been done for you on the image you have:

	root@raspberrypi:/media/github/rpi_DS18B20# tail /boot/config.txt 
	# Uncomment some or all of these to enable the optional hardware interfaces
	#dtparam=i2c_arm=on
	#dtparam=i2s=on
	#dtparam=spi=on
	
	# Uncomment this to enable the lirc-rpi module
	#dtoverlay=lirc-rpi
	
	# Additional overlays and parameters are documented /boot/overlays/README
	dtoverlay=w1-gpio

# Look for your device
Your temperature sensor will appear with an address in the format
28-0xxxxxxxxxxx under the /sys/bus/w1/devices/ directory:

	root@raspberrypi:/rpi_DS18B20# ls /sys/bus/w1/devices/
	28-0414505a09ff  w1_bus_master1

This shows that the 28-0414505a09ff is the identifier of the temp sensor.
The temperature reading is available from the /sys/bus/w1/devices/28-*/w1_slave file contents:

	cat /sys/bus/w1/devices/28-0414505a09ff/w1_slave
	root@raspberrypi:/rpi_DS18B20# cat /sys/bus/w1/devices/28-0414505a09ff/w1_slave 
	54 01 55 00 7f ff 0c 10 c0 : crc=c0 YES
	54 01 55 00 7f ff 0c 10 c0 t=21250

The file consists of two lines, each containing the hexadecimal register-dump of the sensor IC. At the end of the first line is the checksum (CRC) and the information whether it is a valid reading (YES). The second line ends with the temperature reading in thousandths of a degree Celsius. 

# wiring setup
https://www.dropbox.com/s/vh05s40x4kfz4hq/2015-05-11%2018.10.49.jpg?dl=0
https://www.dropbox.com/s/dgakxjkv7nc62bk/2015-05-11%2018.11.00.jpg?dl=0

# python usage
To access the temperature using a python client, edit the ds18b20_read.py file and change the device identifier value to that of your sensor:
<!--TODO, add code to pull the sensor id from the filesystem -->

	# Replace with your temp device id...
	device="28-0314668afdff"

and then run:

	python ds18b20_read.py

# node usage
To access the temperature using a nodejs client, you would first install the ds18b20 module(this has been done for you, see node_modules/ds18b20/) and then run:

	node ds18b20_read.js

To read the temperature in a loop run the ds18b20_poll.js version:

	node ds18b20_poll.js

To read the temperature in a loop and publish the data as a json message to an MQTT broker run the ds18b20_poll_mqtt.js version:

	node ds18b20_poll_mqtt.js

# Java usage
To access the temperature using a Java client, you can use the Gradle script and run:

	gradle readTemp
	
To read the temperature in a loop run the readTempLoop target:

	gradle readTempLoop

To read the temperature in a loop and publish the data as a json message to an MQTT broker run the readTempLoopMQTT target:

	gradle readTempLoopMQTT
