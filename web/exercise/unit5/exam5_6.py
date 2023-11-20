#파일명 : exam5_6.py
import json
import urllib.request

#User-Agent를 조작하는 경우 
hdr = {'User-Agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) '+ 
        'AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36'}

#req = urllib.request.Request('http://unico2013.dothome.co.kr/crawling/header.php', headers = hdr)
req = urllib.request.Request('http://unico2013.dothome.co.kr/crawling/header.php')
data = urllib.request.urlopen(req).read()
#page = data.decode('utf-8', 'ignore')
res_content = json.loads(data)

print(res_content["result"])
