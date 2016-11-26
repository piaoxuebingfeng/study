from socket import *
import time
device_id = "3503914"
api_key = "qXcedt1vUh33pjYewmYQwtOKEIg=" 
desc_id = "light"
values=0
HOST = 'api.heclouds.com'
PORT = 80
BUFSIZ = 1024
ADDR = (HOST, PORT)
while True:
	#"GET /devices/284289/datapoints HTTP/1.1\r\napi-key: UxvFGrtEFZMnfhwF6pa6zV5h9ZwA\r\nHost: api.heclouds.com\r\n\r\n"
	data = ''
	data +="GET /devices/"+device_id+"/datapoints HTTP/1.1\r\napi-key:"+api_key+"\r\nHost: api.heclouds.com\r\n\r\n"
	tcpCliSock = socket(AF_INET, SOCK_STREAM)
	tcpCliSock.connect(ADDR)
	tcpCliSock.send(data.encode())
	data1 = tcpCliSock.recv(BUFSIZ).decode()
	if not data1:
		break
	print "the data is :\n"
	print (data1)
	tcpCliSock.close()
	time.sleep(5)