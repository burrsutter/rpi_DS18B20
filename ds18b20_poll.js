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
  });
}, 1000);


