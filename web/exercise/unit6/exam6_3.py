#파일명 : exam6_3.py
from selenium import webdriver

driver = webdriver.Chrome('C:/Temp/chromedriver')
driver.implicitly_wait(3)
driver.get("https://www.istarbucks.co.kr/store/store_map.do")
target=driver.find_element_by_class_name("quickResultLstCon")

print(type(target))
print(type(target.text))
print(target.text)
driver.quit()
