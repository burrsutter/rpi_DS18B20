var ds18b20 = require('ds18b20');
ds18b20.sensors(function(err, ids) {
  // got sensor IDs ...
  for (i = 0; i < ids.length; i++) {
    console.log(ids[i]);
  } 
});
