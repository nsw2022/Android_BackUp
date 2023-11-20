#파일명 : exam4_2.py
html_doc = """
<!DOCTYPE html>
<html>
     <head>
          <meta charset='utf-8'>
          <title>Test BeautifulSoup</title>
     </head>
     <body>
          <p align="center">P태그의 컨텐트</p>
          <img src="http://unico2013.dothome.co.kr/image/flower.jpg" width="300">
     </body>
</html> """
from bs4 import BeautifulSoup
bs = BeautifulSoup(html_doc, 'html.parser')
print(bs.prettify())

