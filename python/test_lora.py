#!/usr/bin/env python
# -*- coding: utf-8 -*-
import requests
import json
import time
import string
import os
from time import sleep
def main():
    os.system("sh /home/pi/work/lora_monitor.sh &")
    while True:
		fileRecord = open("/tmp/opt", "r")
		temperature = fileRecord.read()
		#fileRecord.write("connect to yeelink\n");
		fileRecord.close()
		device = temperature[0:1]
		#device_i = int(device)
		if device == '1':
			print "soil"
			temp = temperature[2:6]
			humi = temperature[7:11]
			#try :
			#t = float(temp)
			print temp
			print humi
			soil_humi=temperature[12:16]
			try :
				sh=float(soil_humi)
				if sh>0 :
					if sh<0.4 :
						sh_up=80
					elif sh<0.55 :
						sh_up=75
					elif sh<0.65 :
						sh_up=50
					elif sh <1.5 :
						sh_up=25
					else:
						sh_up=0
			except Exception,e:
				file = open("/tmp/loraLog","a")
                       		localtime = time.asctime(time.localtime(time.time()))
                    	    	file.write(localtime)
				file.write("\n")
                        	file.write("get soil_humi error\n")
                        	file.close()
			#print soil_humi
			#print sh_up
			file  =open("/tmp/sensor_log","a")
			localtime = time.asctime(time.localtime(time.time()))
			file.write(localtime)
			file.write(soil_humi)
			#file.write(sh_up)
			file.close()
			payload = {"datastreams":[{"id":"lora_temp","datapoints":[{"value":temp}]},{"id":"lora_humi","datapoints":[{"value":humi}]},{"id":"soil_humi","datapoints":[{"value":sh_up}]},{"id":"soil_humi_v","datapoints":[{"value":soil_humi}]}]}
                if device == '2':
                        print "light"
			light=temperature[2:6]
			#"GET /devices/284289/datapoints HTTP/1.1\r\napi-key: UxvFGrtEFZMnfhwF6pa6zV5h9ZwA\r\nHost: api.heclouds.com\r\n\r\n"
			#
			payload = {"datastreams":[{"id":"light","datapoints":[{"value":light}]}]}
       		try :
		 	# 设备URI
			apiurl = 'http://api.heclouds.com/devices/3503914/datapoints'
			# 用户密码, 指定上传编码为JSON格式
			apiheaders = {'api-key': 'qXcedt1vUh33pjYewmYQwtOKEIg=', 'Content-Length': '120'}
			# 字典类型数据，在post过程中被json.dumps转换为JSON格式字符串 {"value": 48.123}
			#payload = {"datastreams":[{"id":"soil_humi","datapoints":[{"value":sh_up}]},{"id":"soil_humi_v","datapoints":[{"value":soil_humi}]}]}
			
			#发送请求
			r = requests.post(apiurl, headers=apiheaders, data=json.dumps(payload))
			#time.sleep(5)
		except Exception,e:
			file = open("/tmp/loraLog","a")
			localtime = time.asctime(time.localtime(time.time()))
			file.write(localtime)
			file.write("connect to server error")
			file.close()
		time.sleep(5)


if __name__ == '__main__':
    main()
    
