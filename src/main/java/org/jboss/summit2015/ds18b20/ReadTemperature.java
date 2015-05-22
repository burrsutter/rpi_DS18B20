package org.jboss.summit2015.ds18b20;

import java.io.IOException;
import java.util.List;

/**
 * Read the temperature from the ds18b20 1-wire sensor, printing the temp in C and F
 */
public class ReadTemperature {
    public static void main(String[] args) throws IOException, InterruptedException {
        int count = 1;
        if(args.length > 0)
            count = Integer.parseInt(args[0]);

        List<String> ids = OneWireSensor.getSensorIDs();
        // Just grab first sensor
        String id = ids.get(0);
        OneWireSensor tempSensor = new OneWireSensor(id);
        for(int n = 0; n < count; n ++) {
            double temp = tempSensor.getTemperature();
            double tempF = temp * 1.8 + 32;
            System.out.printf("#%d: %.2fC or %.2fF\n", n, temp, tempF);
            Thread.sleep(1000);
        }
    }
}
