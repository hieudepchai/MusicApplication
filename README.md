# MusicApplication

* create simple stream server by node js and make music application being client:

- create file server.js and copy below code:
```
var http = require('http'),
    fileSystem = require('fs'),
    path = require('path')
    util = require('util');

http.createServer(function(request, response) {
    var filePath = 'Em-Gi-Oi-Jack-K-ICM.mp3';
    var stat = fileSystem.statSync(filePath);
    
    response.writeHead(200, {
        'Content-Type': 'audio/mpeg',
        'Content-Length': stat.size
    });

    var readStream = fileSystem.createReadStream(filePath);
    // We replaced all the event handlers with a simple call to util.pump()
    readStream.pipe(response);
})
.listen(2000,function(){
	console.log("Listening on 2000");
    });
```    
- change filePath which links to a local .mp3 file and run the server (node server.js)
- change streamURL in streamFragment.java to "http://< your localhost IP >:2000/";
