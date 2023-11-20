<?php

    header('Content-Type:text/plain; charset=utf-8');

    //Android로 부터 GET방식으로 보내 온 데이터 받기
    $title=$_GET['title'];
    $message=$_GET['msg'];

    //잘 받았는지 Android로 다시 응답[echo] 해주기
    echo "제목 : $title \n";
    echo "메세지 : $message" ;

?>