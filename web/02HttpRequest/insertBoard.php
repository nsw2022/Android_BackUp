<?php

    header('Content-Type:text/html; charset=utf-8');

    //사용자로 부터 전달된 데이터와 파일정보 받기
    $title= $_POST['title'];
    $message= $_POST['msg'];

    $file= $_FILES['img'];
    //파일의 세부정보 중 필요한 것들만..
    $srcName= $file['name'];      //원본파일명
    $tmpName= $file['tmp_name'];  //임시저장소 파일경로 및 파일명

    //영구히 저장될 파일의 위치와 파일명 결정[중복된 파일명이 아니어야 하기에 날짜 이용]
    $dstName= "./uploads/" . date('YmdHis') . $srcName;

    //임시저장소($tmpName) 파일을 원하는 위치($dstName)로 이동
    $moveResult= move_uploaded_file($tmpName, $dstName);
    if($moveResult) echo "upload success <br>";
    else echo "upload fail <br>";

    echo "<br>";
    echo $message;

    // 저장되는 시간..날짜
    $now= date("Y-m-d H:i:s"); //2022-10-04 15:16:23

    // 전달받은 데이터를 Database에 저장
    // 저장할 데이터들($title, $message, $dstName, $now)을 DB에 저장
    // dothome 호스팅서버는 이미 Database 프로그램이 설치되어 있음.
    
    // MySQL 이라는 DBMS를 이용하여 데이터를 저장

    // 1. MySQL DB에 접속하기
    $db= mysqli_connect("localhost","tmddn3410","a1s2d3f4!","tmddn3410"); //DB서버주소, DB접속아이디, DB접속비밀번호, DB파일명
    //$db : 연결된 DB를 제어하는 객체

    // DB안에 한글데이터가 깨지지 않도록.
    mysqli_query($db, "set names utf8");

    // 2. 원하는 쿼리문(SQL문법)을 요청
    // 데이터들($title,$message,$dstName,$now)을 "board"라는 이름의 테이블(표)에 삽입하는 명령어[SQL]
    $sql= "INSERT INTO board(title,msg, file, date) VALUES('$title','$message','$dstName','$now')";
    $result= mysqli_query($db,$sql); //쿼리문실행결과를 리턴해줌
    
    if( $result ) echo "insert success";
    else echo "insert fail";

    // 3. MySQL과의 연결을 종료
    mysqli_close($db);



?>