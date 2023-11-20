<?php
    header('Content-Type:application/json; charset=utf-8');

    // @Body로 보낸 json문자열은 $_POST라는 배열에 자동 저장되지 않음.
    // json문자열로 넘어온 데이터는 별도의 임시공간[php://input]에 파일로 저장됨.
    // 임시공간에 있는 파일 데이터(json문자열)를 읽어오기
    $data= file_get_contents("php://input");
    //읽어온 json문자열을 연관배열로 변환 [값들을 분리하기 위해]
    $_POST= json_decode($data, true); //true:연관배열로 할지 여부

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