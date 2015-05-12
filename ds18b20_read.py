import time

while 1:
	tempfile = open("/sys/bus/w1/devices/28-0414505a09ff/w1_slave")
	thetext = tempfile.read()
	tempfile.close()
	tempdata = thetext.split("\n")[1].split(" ")[9]
	temp = float(tempdata[2:])
	temp = temp / 1000
	print temp
	time.sleep(1)

