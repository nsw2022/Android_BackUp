#파일명 : exam3_9.py
import requests

r = requests.get('http://unico2013.dothome.co.kr/crawling/exam.html')
r.encoding = 'utf-8'
print(type(r))
print(r.headers)
if r.text :
    print(r.text)
else :
    print('응답된 콘텐츠가 없어요')
