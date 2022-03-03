import paho.mqtt.client as mqtt
import socketio
import cv2
import base64

sio = socketio.Client()


@sio.event
def connect():
    print("socketio connection established")

@sio.event
def sensorSTE(data):
    print(data)
    client.publish('s01In', data, 0)

stopck = 0
@sio.event
def imageSTE(data):
    print(data)
    global stopck
    print(stopck)
    if data == "stop": stopck = 1
    if data == "start":
        cam = cv2.VideoCapture(0)
        cam.set(cv2.CAP_PROP_FRAME_WIDTH, 320);
        cam.set(cv2.CAP_PROP_FRAME_HEIGHT, 240);
        encode_param = [int(cv2.IMWRITE_JPEG_QUALITY), 25]
    
        while True:
            ret, frame = cam.read()
            frame = cv2.flip(frame, 0)
            result, frame = cv2.imencode('.jpg', frame)
            b64data = base64.b64encode(frame)
        
            sio.emit('imageETS', b64data)

            if stopck == 1:
                stopck = 0
                break

        cam.release()

@sio.event
def disconnect():
    print("disconnected from server");


def on_connect(client, userdata, flags, rc):
    if rc == 0:
        print("connected OK")
    else:
        print("Bad connection Returned code=", rc)


def on_disconnect(client, userdata, flags, rc=0):
    print(str(rc))


def on_subscribe(client, userdata, mid, granted_qos):
    print("subscribed: " + str(mid) + " " + str(granted_qos))


def on_message(client, userdata, msg):
    sio.emit('sensorETS', str(msg.payload.decode("utf-8")))
    print(str(msg.payload.decode("utf-8")))


sio.connect('http://52.79.57.59:8083')

# 새로운 클라이언트 생성
client = mqtt.Client()
# 콜백 함수 설정 on_connect(브로커에 접속), on_disconnect(브로커에 접속중료), on_subscribe(topic 구독),
# on_message(발행된 메세지가 들어왔을 때)
client.on_connect = on_connect
client.on_disconnect = on_disconnect
client.on_subscribe = on_subscribe
client.on_message = on_message
# address : localhost, port: 1883 에 연결
client.connect('localhost', 1883)
client.subscribe('s01Out', 0)
client.loop_forever()
