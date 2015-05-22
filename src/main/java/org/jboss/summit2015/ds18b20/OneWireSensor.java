package org.jboss.summit2015.ds18b20;


import com.pi4j.component.temperature.TemperatureSensorBase;
import com.pi4j.temperature.TemperatureScale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Uses the file system interface to the 1-wire ds18b20 setup up by the w1-gpio and w1-therm modules
 * Created by starksm on 5/20/15.
 */
public class OneWireSensor extends TemperatureSensorBase {
    private static final String W1_FILE = "/sys/bus/w1/devices/w1_bus_master1/w1_master_slaves";

    public static List<String> getSensorIDs() throws IOException {
        FileReader reader = new FileReader(W1_FILE);
        BufferedReader breader = new BufferedReader(reader);
        ArrayList<String> ids = new ArrayList<>();
        String id = breader.readLine();
        while(id != null) {
            ids.add(id);
            id = breader.readLine();
        }
        return ids;
    }

    public OneWireSensor(String id) {
        super.setName(id);
    }

    /**
     * parses the contents of the w1_slave file:
     71 01 55 00 7f ff 0c 10 3e : crc=3e YES
     71 01 55 00 7f ff 0c 10 3e t=23062

     The file consists of two lines, each containing the hexadecimal register-dump of the sensor IC. At the end of the
     first line is the checksum (CRC) and the information whether it is a valid reading (YES). The second line ends with
     the temperature reading in thousandths of a degree Celsius.
     * @return
     */
    @Override
    public double getTemperature() {
        double value = 0;
        String path = "/sys/bus/w1/devices/" + getName() + "/w1_slave";
        try {
            FileReader reader = new FileReader(path);
            BufferedReader breader = new BufferedReader(reader);
            String crc = breader.readLine();
            if (crc.endsWith("YES")) {
                String tempLine = breader.readLine();
                int equals = tempLine.indexOf('=');
                String tempString = tempLine.substring(equals + 1);
                int thousandths = Integer.parseInt(tempString);
                value = 0.001 * thousandths;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }

    @Override
    public TemperatureScale getScale() {
        return TemperatureScale.CELSIUS;
    }

}
