#파일명 : exercise_solution1.py
import urllib.request
import urllib.parse
params = urllib.parse.urlencode({'category': '역사', 'page': 25})
url = "http://unico2013.dothome.co.kr/crawling/exercise.php?%s" % params
with urllib.request.urlopen(url) as f:
	print(f.read().decode('utf-8'))