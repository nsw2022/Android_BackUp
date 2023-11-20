#파일명 : exercise_solution2.py
import requests
dicdata = {'category': '여행', 'page': 100}
urlstr = 'http://unico2013.dothome.co.kr/crawling/exercise.php'
r = requests.get(urlstr, params=dicdata)
r.encoding = "utf-8"
print(r.text)

