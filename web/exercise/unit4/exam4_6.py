#파일명 : exam4_6.py
html="""
<!DOCTYPE html>
<html>
  <head>
     <meta charset='utf-8'>
     <title>Test BeautifulSoup</title>
  </head>
  <body>
     <p align="center"> text contents </p>
     <p align="right" class="myp">  text contents 2 </p>
     <p align="left" a="b">   text contents 3 </p>
     <img src="http://unico2013.dothome.co.kr/image/flower.jpg" width="500">
  </body>
</html> """

from bs4 import BeautifulSoup
bs = BeautifulSoup(html, 'html.parser')
print(bs.find('p', align="center"))
print(bs.find('p', class_="myp"))
print(bs.find('p', align="left"))
print("-------------------------------------")
print(bs.find('p', attrs={"align":"center"}))
print(bs.find('p', attrs={"align":"right", "class":"myp"}))
print(bs.find('p', attrs={"align":"left"}))







