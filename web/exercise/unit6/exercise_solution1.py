#파일명 : exercise_solution1.py
from selenium import webdriver
from selenium.webdriver.common.keys import Keys 

driver = webdriver.Chrome('C:/Temp/chromedriver')
print("webdriver 객체 : ", type(driver))

driver.get('http://www.naver.com/') 
target=driver.find_element_by_css_selector("[name = 'query']")
print("태그 객체 : ", type(target))
target.send_keys('파이썬')
target.send_keys(Keys.ENTER)
driver.quit()