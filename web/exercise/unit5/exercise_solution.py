#파일명 : exercise_solution.py
import requests
from bs4 import BeautifulSoup
import re
for n in range(1,6):
    req = requests.get('https://movie.daum.net/moviedb/grade?movieId=3126&type=netizen&page='+str(n))
    html = req.text
    soup = BeautifulSoup(html, 'html.parser')
    grades = soup.select('.emph_grade' )
    reviews = soup.select('.desc_review' )

    movie_grade = []
    movie_review = [] 
    for dom in grades:
        movie_grade.append(dom.text)
    for dom in reviews:
        movie_review.append(dom.text.strip())
    
    commentLength = len(movie_grade)   
    for i in range(commentLength):
        if(len(movie_review[i]) != 0):
            print(movie_grade[i]+"\t"+movie_review[i])
    
