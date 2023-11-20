#파일명 : exercise_solution.py
import urllib.request
from bs4 import BeautifulSoup
url = "http://unico2013.dothome.co.kr/crawling/exercise_bs.html" 
html = urllib.request.urlopen(url)
bs = BeautifulSoup(html, "html.parser")
print('[<h1> 태그의 콘텐츠] ', bs.select('h1')[0].text)
print('[텍스트 형식으로 내용을 가지고 있는 <a> 태그의 콘텐츠와 href 속성값] ',)
aTag = bs.select('a')
for tag in aTag:
	if(tag.text.strip()):
   		print(tag.text, ' : ', tag['href'])
print('[<img> 태그의 src 속성값] ',bs.select('img')[0]['src'])
print('[첫 번째 <h2> 태그의 콘텐츠] ',bs.select('h2:nth-of-type(1)')[0].text)
print('[<ul> 태그의 자식 태그들 중 style 속성의 값이 green으로 끝나는 태그의 콘텐츠] ',bs.select('ul>li[style$=green]')[0].text)
print('[두] 번째 <h2> 태그의 콘텐츠] ',bs.select('h2:nth-of-type(2)')[0].text)
print('[<ul> 태그의 모든 자식 태그들의 콘텐츠 ]')
olliTag = bs.select('ol>li')
for tag in olliTag:
	print(tag.text)
print('[<table> 태그의 모든 자손 태그들의 콘텐츠 ]')
print(bs.select('table')[0].text.strip())
print('[name이라는 클래스 속성을 갖는 <tr> 태그의 콘텐츠] ',bs.select('tr.name')[0].text)
print('[target이라는 아이디 속성을 갖는 <td> 태그의 콘텐츠] ',bs.select('td#target')[0].text)