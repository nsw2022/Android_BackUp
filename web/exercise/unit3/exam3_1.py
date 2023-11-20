#파일명 : exam3_1.py
import urllib.request
res = urllib.request.urlopen("http://www.naver.com/")
print(type(res))
print(res.status)
print(res.version)
print(res.msg)
res_header = res.getheaders()
print("[ header 정보 ]----------")
for s in res_header :
    print(s)