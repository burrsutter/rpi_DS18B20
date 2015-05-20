var mqtt    = require('mqtt');
var client  = mqtt.connect('mqtt://192.168.3.5');
var ds18b20 = require('ds18b20');

ds18b20.sensors(function(err, ids) {
  // got sensor IDs ...
  for (i = 0; i < ids.length; i++) {
    console.log(ids[i]);
  } 
});


setInterval(function() {
  ds18b20.temperature('28-0414505a09ff', function(err, value) {
    console.log('Current temperature is', value);
    client.publish('temp_rpi_DS18B20',
       '{"sensorid":"rpi_DS18B20",' + 
       ' "temp":' + value.toFixed(1) + ',' +
       ' "time":' + Date.now() + '}'
    );
  });
}, 1000);


