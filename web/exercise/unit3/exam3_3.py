#파일명 : exam3_3.py
import urllib.request
print("===========================================================")
url = 'https://www.python.org/'
f = urllib.request.urlopen(url)
print(type(f))
print(type(f.info()))
encoding = f.info().get_content_charset()
print(url, ' 페이지의 인코딩 정보 :', encoding)
text = f.read(500).decode(encoding)
print(text)
print("===========================================================")

url = 'https://www.daum.net/'
f = urllib.request.urlopen(url)
encoding = f.info().get_content_charset()
print(url, ' 페이지의 인코딩 정보 :', encoding)
text = f.read(500).decode(encoding)
print(text)
print("===========================================================")

url = 'https://www.aladin.co.kr/home/welcome.aspx'
f = urllib.request.urlopen(url)
encoding = f.info().get_content_charset()
print(url, ' 페이지의 인코딩 정보 :', encoding)

text = f.read(500).decode(encoding)
print(text)
print("===========================================================")