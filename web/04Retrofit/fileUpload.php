<?php
    header('Content-type:text/plain; charset=utf-8');

    $flie=$_FILES['img']; //'img' 식별자로 보내진 파일의 정보들

    $srcName=$flie['name'];         //원본파일명
    $size=   $flie['size'];         //파일의사이즈
    $tmpName=$flie['tmp_name'];     //임시저장소의 파일주소

    //전달이 잘 되었는지 Android쪽으로 echo(응답)

    echo "$srcName \n";
    echo "$size \n";
    echo "$tmpName";

    //실제 파일데이터는 임시저장소에 있기에 영구적으로 저장하려면
    // 이동시켜야 함. [ 다음예제에서.. 구현.. ]
?>