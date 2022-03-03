const app = require('express')();
const fs = require('fs');
const path = require('path');
const PORT = 8083;
const server = require('http').createServer(app);

var io = require('socket.io')(server, {
	cors: {
		origin: "*",
		method: ["GET", "POST"]
	}
});

app.get('/', function (req, res) {
	res.sendFile('Hellow Chating App Server');
});

//connection event handler 
io.on('connection', function (socket) {
	console.log('Connect from Client: ' + socket.id);

	socket.on('sensorFTS', function (data) {
		console.log("sensorFTS : " + data)

		socket.broadcast.emit('sensorSTE', data);
	});


	socket.on('sensorETS', function (data) {
		console.log("sensorETS : " + data);

		socket.broadcast.emit('sensorSTF', data);
	})

	socket.on('imageFTS', function (data) {
		console.log("imageFTS : " + data);

		socket.broadcast.emit('imageSTE', data);
	})

	socket.on('imageETS', function (data) {
		//console.log("imageETS : " + data);

		socket.broadcast.emit('imageSTF', data);
	})
})

server.listen(PORT, function () { console.log(`socket io server listening on port ${PORT}`) })


