# -*- coding: utf-8 -*-
import requests
def lookup(ip):
	URL = 'http://freeipapi.17mon.cn/' + ip
	try:
		r = requests.get(URL, timeout=3)
	except requests.RequestException as e:
		print(e)
	json_data = r.json()
	print '���ڹ��ң�' + json_data[0].encode('utf-8')
	print '����ʡ�ݣ�' + json_data[1].encode('utf-8')
	print '���ڳ��У�' + json_data[2].encode('utf-8')
	return(ip)
ip='202.104.15.102'
lookup(ip)