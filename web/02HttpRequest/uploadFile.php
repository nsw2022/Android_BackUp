<?php

    header('Content-Type:text/html; charset=utf-8');

    // 사용자가 File을 보내면 실제 파일데이터들은 임시저장소(tmp)에 임시로 저장되며
    // 이 php문서에는 File에 대한 정보만 전달됨. 그 정보를 $_FILSE[]라는  배열에 저장함.
    $file= $_FILES['img'];

    //$file은 배열임. 즉 $file 배열변수안에 전송된 파일에 대한 여러정보가 있음. 5개 나열
    $srcName=$file['name']; //원본파일명
    $size=   $file['size']; // 파일 사이즈
    $type=   $file['type']; // 파일타입
    $tmpName=$file['tmp_name']; //파일데이터가 저장된 임시저장소의 파일주소(위치)
    $error=  $file['error']; //에러정보

    //제대로 정보가 왔는지 확인해보기 위해 정보들을 출력(응답)

    echo "#srcName <br>";
    echo $size . "<br>"; //php에서 . 은 문자열 결합 연산자 임
    echo $type . "<br>"; 
    echo "$tmpName <br>";
    echo "$error <br>";

    // 정보가 잘 왔는지 확인되었다면.. 서버에 파일은 전송된 것임.
    // 근데 임시정장소($tmpName)에 임시로 파일데이터가 존재하기에..
    // 이 php가 끝나면 사라짐.. 그래서 서버에 영구히 저장하려면..
    // 임시저장소($tmpName)에서 원하는 위치로 이동시켜야 함.
    // 그래서 이동시킬 곳의 목적지 주소를 먼저 정해야 함. [ 단. uploads폴더는 미리 준비 되어 있어야 함 ]
    $dstName="./uploads/" . date('YmdHis') . $srcName; //"./uploads"/20221004135331/koala.jpg
    $result = move_uploaded_file($tmpName,$dstName); //이동 성공여부를 리턴함
    if($result) {
        echo "sucess upload";
    }else{
        echo "fail upload";
    }

    

?>