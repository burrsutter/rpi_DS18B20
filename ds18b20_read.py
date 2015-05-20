import time

# Replace with your temp device id...
device="28-0314668afdff"
while 1:
	tempfile = open("/sys/bus/w1/devices/"+device+"/w1_slave")
	thetext = tempfile.read()
	tempfile.close()
	tempdata = thetext.split("\n")[1].split(" ")[9]
	temp = float(tempdata[2:])
	temp = temp / 1000
        tempF = temp*1.8 + 32
	print "{:.2f}C or {:.2f}F".format(temp, tempF)
	time.sleep(1)

