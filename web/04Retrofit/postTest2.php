<?php
    header('Content-Type:application/json; charset=utf-8');

    $no= $_POST['no'];
    $title= $_POST['title'];
    $message= $_POST['msg'];


    // 잘 전달받았는지 확인하기 위해 echo [단,Retriotif이 응답을 json으로 요구하므로.]
    $arr= array(); //빈 배열
    $arr['no']= $no;
    $arr['title']= $title;
    $arr['msg']= $message;

    //echo는 json문자열로 변환하여..응답
    echo json_encode($arr); //연관배열 --> json문자열

?>
