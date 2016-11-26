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
	values = values+1;
	Content = "{\"datastreams\":[{\"id\": \"temperature\",\"datapoints\": [{\"value\": "+str(values)+"}]},"
	Content +="{\"id\": \""+desc_id+"\",\"datapoints\": [{\"value\": "+str(values*2)+"}]}]}\r\n"
	value = len(Content)
	data = ''
	data +="POST /devices/"+device_id+"/datapoints HTTP/1.1\r\n"
	data +="Host:api.heclouds.com\r\n"
	data +="Connection: close\r\n"
	data +="api-key:"+api_key+"\r\n"
	data +="Content-Length:"+str(value)+"\r\n"
	data +="\r\n"
	data +=Content
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