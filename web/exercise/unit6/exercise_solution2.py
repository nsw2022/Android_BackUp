#파일명 : exercise_solution2.py
from selenium import webdriver

driver = webdriver.Chrome('C:/Temp/chromedriver')
print("webdriver 객체 : ", type(driver))

driver.get('http://www.naver.com/') 
target=driver.find_element_by_id("query")
print("태그 객체 : ", type(target))
target.send_keys('파이썬')
target.submit()
driver.quit()
