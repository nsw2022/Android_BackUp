#파일명 : exam4_1.py
html_doc = """
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Test BeautifulSoup</title>
    </head>
    <body>
        <h1>테스트</h1>
    </body>
</html> """
from bs4 import BeautifulSoup
bs = BeautifulSoup(html_doc, 'html.parser')
print(type(bs))
