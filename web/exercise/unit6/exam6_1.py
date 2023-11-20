#파일명 : exam6_1.py
from selenium import webdriver
from selenium.webdriver.common.keys import Keys 

driver = webdriver.Chrome('C:/Temp/chromedriver')
print("WebDriver 객체 : ", type(driver))

driver.get('http://www.google.com/ncr') 
target=driver.find_element_by_css_selector("[name = 'q']")
print("찾아온 태그 객체 : ", type(target))
target.send_keys('파이썬')
target.send_keys(Keys.ENTER)
#driver.quit()
