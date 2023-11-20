<?php

    header('Content-Type:text/plain; charset=utf-8');

    $title= $_POST['title'];
    $message= $_POST['msg'];
    $now= date('Y-m-d H:i:s'); //"2022-10-05 13:23:52"

    // MYSQL DB의 board2 테이블에 데이터들($title,$message,$now) 저장
    //1. DB서버에 접속
    $db= mysqli_connect("localhost","tmddn3410","a1s2d3f4!","tmddn3410");

    //2. 한글깨짐 방지
    mysqli_query($db, "set names utf8");

    //3. 원하는 쿼리문 작성
    $sql= "INSERT INTO board2(title,msg,date) VALUES('$title','$message','$now')";

    //4. 쿼리문 실행
    $result= mysqli_query($db, $sql);

    if($result) echo "insert success";
    else echo "insert fail";

    //5. DB연결 닫기
    mysqli_close($db);

?>