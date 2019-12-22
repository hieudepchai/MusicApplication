# MusicApplication

### Create simple stream server by node js and make music application being client:

- Create file **server.js** and copy below code:
```
var http = require('http'),
    fileSystem = require('fs'),
    path = require('path')
    util = require('util');

http.createServer(function(request, response) {
    var filePath = '<your local .mp3 file>';
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
- Run the server.
```
node server.js
```
- Change **streamURL** in **streamFragment.java** to "http://< your localhost IP >:2000/"
```
String streamURL = "http://<your localhost IP>:2000/";
```
