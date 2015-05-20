var ds18b20 = require('ds18b20');

// Save the sensor ids
var sensorIDs = ds18b20.readSensorsIDs();

console.log("Getting temperature of: "+sensorIDs[0]);
ds18b20.temperature(sensorIDs[0], function(err, value) {
  console.log('Current temperature is', value);
});
