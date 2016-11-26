import os
import requests
import json,time,string
def getcputemperature():
    #cputemp=os.popen('vcgencmd measure_temp').readline()
    #sumcputemp=cputemp.replace("temp=","").replace("'C\n","")
	#return sumcputemp
	file = open("/sys/class/thermal/thermal_zone0/temp") 
	temp = float(file.read()) / 1000  
	file.close() 
	return str(temp)
def get_temp():
	tfile = open("/sys/bus/w1/devices/28-000003123ccb/w1_slave")
	text = tfile.read()
	tfile.close()
	secondline = text.split("\n")[1]
	temperaturedata = secondline.split(" ")[9]
	temperature = float(temperaturedata[2:])
	temperature = temperature / 1000
	return str(temperature)
def getcpuused():
    return(os.popen("top -n1"))
apiheaders={'U-ApiKey':'6d12d15052e9b1a482dfe8f622fd5c2a','content-type': 'application/json'}
cputemp_apiurl="http://api.yeelink.net/v1.0/device/344015/sensor/382236/datapoints"
cpuused_apiurl="http://api.yeelink.net/v1.0/device/344015/sensor/382237/datapoints"
memeryused_apiurl="http://api.yeelink.net/v1.0/device/344015/sensor/382238/datapoints"
temp_apiurl="http://api.yeelink.net/v1.0/device/344015/sensor/382245/datapoints"
if __name__=='__main__':
	while 1:
		cpu_temp=getcputemperature()
		cputemp_payload={'value':cpu_temp}
		r=requests.post(cputemp_apiurl, headers=apiheaders, data=json.dumps(cputemp_payload))
		print "CPU TEMP :"+cpu_temp+"C"
		temp_updata=get_temp()
		temp_updata_payload={'value':temp_updata}
		r=requests.post(temp_apiurl, headers=apiheaders, data=json.dumps(temp_updata_payload))
		print "TEMP:"+temp_updata+"C"
		tempcpuused=getcpuused()
		for cpuline in tempcpuused:
			if cpuline[:3]=="%Cp":
				cpulineused=cpuline.split(":")[1].split(",")[0].split(" ")[-2]
				cpuused_payload={'value':cpulineused}
				r=requests.post(cpuused_apiurl, headers=apiheaders, data=json.dumps(cpuused_payload))
				print "CPU USED %:"+cpulineused
			if "Mem:" in cpuline:
				memlineused=cpuline.split(":")[1].split(",")[1].strip("used").split(" ")[-2]
				memlinetotal=cpuline.split(":")[1].split(",")[0].strip("total").split(" ")[-2]
				memeryusedratio=float(str(memlineused))/float(str(memlinetotal))
				memeryusedratiostr="%.2f"%(memeryusedratio*100)
				memeryused_payload={'value':memeryusedratiostr}
				r=requests.post(memeryused_apiurl, headers=apiheaders, data=json.dumps(memeryused_payload))
				print memeryusedratiostr
				print "================"
		time.sleep(3)