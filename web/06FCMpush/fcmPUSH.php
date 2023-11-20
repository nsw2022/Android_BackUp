<?php
    header('Content-Type:text/html; charset=utf-8');
    echo "FCM push서버에 메세지를 전송을 시작합니다.";

    // FCM 서버에 보낼 데이터
    //1. 메세지를 받을 디바이스의 고유 토큰값들 배열 - 원래 이 값들은 DB에 있어야한다
    //2. 메세지 정보.(name, age, address)

    //1) 토큰들
    $tokens= array(
        "fQ3LeK_oRUKj4aLb_gATqW:APA91bHtB6eiQ4c_Dk78J7NT5NHGv4xXacpgvc6COlDV3Ou75SZhebpy4894BqZcggjuAm_N0r5pzOD5JM_OGLualNQpjjYfRTYEByAFhqjsDiIdvEMAY1e1hQtMvDPy_zZbFu4PkNl-"
);

    //2) 메세지 정보
    $name=    $_POST['name']; 
    $age=     $_POST['age'];
    $address= $_POST['address'];
    // 위 3개 변수의 값들을 연관배열로 묶기.
    $data= array("name"=>$name, "age"=>$age, "address"=>$address);
    
    //FCM 서버는 본인에게 보낼 토큰들과 데이터들을 json 문자열로 받기을 원함.
    $postData= array(
        'registration_ids'=>$tokens,
        'data'=<$data
    );

    // 연관배열=> hson
    $postDatahJson = json_encode:($postData);

    // 위 데이터를 FCM에 보내려면 
    // FCM서버에 접속하는 [서버 키] - Firbase console에서 확인 가능
    // 이 서버키를 Body로 보내는 것이 아니라.Header 정보로 보냄
    // FCM 서버에 요청할때 필요한 헤더 정보 설정 
    // 1. 서버키
    // 2. 내가 보낼 데이터들을 json형식으로 보낸다라는 표기
    $serverkey="ex89fcmpushnsw2022";
    $headers=array(
        'Authorization:key=' . $serverkey,
        'Content-Type:application/json'
    );

    // 서버에서 다른 서버에 데이터 전송을 요청하는 php용 라이브러리
    // curl library 를 통해서 전송작업. (php에 내장되었음.)
    
    //1. CURL 초기화
    $ch= curl_init();

    //2. CURL 옵션 설정
    // 2.1) 요청 URL 
    curl_setopt($ch, CURLOPT_URL, "https://fcm.googleapis.com/fcm/send");

    // 2.2) 요청 결과 응답받겠다고 설정
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);

    // 2.3) 헤더설정
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);

    // 2.4) POST 방식으로 보낼 JSON 문자열 설정
    curl_setopt($ch, CURLOPT_POST, TRUE);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $postDatahJson);

    //3. 실행 
    $result = curl_exec($ch);
    if($result==false) echo "실패";
    else echo "전송 성공";

    //4. CURL 기능 닫기
    curl_close($ch);

?>