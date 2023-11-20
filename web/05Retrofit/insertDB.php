<?php
    header('Content-Type:text/plain; charset=utf-8');

    //@PartMap으로 전달된 POST방식의 데이터들
    $name= $_POST['name'];
    $title= $_POST['title'];
    $message= $_POST['msg'];
    $price= $_POST['price'];

    //@Part로 전달된 이미지파일
    $file= $_FILES['img'];
    $srcName= $file['name'];      //원본파일명
    $tmpName= $file['tmp_name'];  //임시저장소 경로
    $size   = $file['size'];      //파일사이즈

    //잘 전달되었는지 확인. 2개 정도만 확인
    // echo "$name \n";
    // echo "$srcName \n";

    //이미지파일을 영구적으로 저장하기 위해 임시저장소에서 이동
    $dstName= "./img/IMG_" . date('YmdHis') . $srcName;
    move_uploaded_file($tmpName, $dstName);

    // 메세지나 제목 중에서 특수문자 사용가능성 있음.
    // DB에서 잘못 해석될 수 있음.
    // 특수문자앞에 슬래시를 추가..
    $message= addslashes($message);
    $title= addslashes($title);

    // 데이터가 저장되는 시간
    $now= date("Y-m-d H:i:s");

    // MySQL DB에 데이터 저장 [테이블명 : market]
    $db= mysqli_connect("localhost","tmddn3410","a1s2d3f4!","tmddn3410");
    mysqli_query($db, "set names utf8");

    //데이터들($name, $title, $message, $price, $dstName, $now) 삽입하는 쿼리문
    $sql= "INSERT INTO market(name, title, msg, price, file, date) VALUES('$name','$title','$message','$price','$dstName','$now')";
    $result= mysqli_query($db, $sql);

    if($result) echo "게시글이 업로드 되었습니다.";
    else echo "게시글 업로드에 실패했습니다. 다시 시도해 주세요.";

    mysqli_close($db);
?>