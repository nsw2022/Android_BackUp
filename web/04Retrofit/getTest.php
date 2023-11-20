<?php
    header('Content:text/plain; charset=utf-8');

    $title= $_GET['title'];
    $message=$_GET['msg'];

    // 잘 받았는지 Andriod쪽으로 응답해주기.. 
    // 단, Retrofit이 결과를 json문자열로 달라고 함으로..
    // 그래서 $title, $message의 값을 json문자열로 만들어서 echo
    // php언어에는 연관배열을 json문자열로 변환하는 기능함수가 존재함
    $arr=array();//빈배열
    $arr['title']=$title; //배열 'title'칸이 만들어지고 $title 변수값 저장
    $arr['msg']=$message;  //배열 'msg'칸이 만들어지고 $message 변수값 저장


    //연관배열-->JSON문자열로 변환하여 echo
    echo json_encode($arr);

    
    
?>