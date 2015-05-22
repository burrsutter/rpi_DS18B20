package org.jboss.summit2015.ds18b20;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.List;

/**
 * Read the temperature from the ds18b20 1-wire sensor, printing the temp in C and F and publishing
 * the
 */
public class ReadTemperatureMQTT {
    public static void main(String[] args) throws Exception {
        int count = 1;
        if(args.length > 0)
            count = Integer.parseInt(args[0]);

        // Just grab first sensor
        List<String> ids = OneWireSensor.getSensorIDs();
        String id = ids.get(0);
        String topic = "RHSummit2015_temp_rpi_DS18B20/"+id;

        MqttClient client = new MqttClient("tcp://iot.eclipse.org:1883", "ReadTemperatureMQTT", new MemoryPersistence());
        client.connect();
        System.out.printf("Connected to: tcp://iot.eclipse.org:1883\n");
        System.out.printf("Use wget -qO- http://eclipse.mqttbridge.com/%s\nto query the published values", topic);
        OneWireSensor tempSensor = new OneWireSensor(id);
        for(int n = 0; n < count; n ++) {
            double temp = tempSensor.getTemperature();
            double tempF = temp * 1.8 + 32;
            System.out.printf("#%d: %.2fC or %.2fF\n", n, temp, tempF);
            String json = String.format("{'sensorid':'%s', 'temp':%.1f, 'time': %d}", id, temp, System.currentTimeMillis());
            client.publish(topic, json.getBytes(), 2, true);
            Thread.sleep(1000);
        }
        client.disconnect();
        System.exit(0);
    }
}
