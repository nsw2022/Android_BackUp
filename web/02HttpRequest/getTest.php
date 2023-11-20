<?php

    //한글깨짐 방지 및 echo하는 결과 콘텐츠가 html 형식이라는 설정
    header('Content=Type:text/html; charset=utf-8');

    // php에서 변수는 $로 만듦.
    // 사용자가 GET방식으로 보낸 값들은 &_GET[] 이라는 슈퍼전역변수에 저장됨
    $title = $_GET['title'];
    $message = $_GET['msg'];

    // 받은 결과값을 출력(응답:Respones)
    echo "제목 : $title <br>";
    echo "메세지 : $message";

?>