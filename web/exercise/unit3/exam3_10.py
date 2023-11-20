#파일명 : exam3_10.py
import requests

r = requests.head('http://unico2013.dothome.co.kr/crawling/exam.html')
print(type(r))
print(r.headers)
if r.text :
    print(r.text)
else :
    print('응답된 콘텐츠가 없어요')