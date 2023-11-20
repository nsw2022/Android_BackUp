<?php

    header('Content-Type:text/plain; charset=utf-8');

    // DB 읽어오기
    $db=mysqli_connect("localhost","tmddn3410","a1s2d3f4!","tmddn3410");
    mysqli_query($db,"set names utf8");

    $sql = "SELECT * FROM board2"; 
    $result=mysqli_query($db,$sql);

    //총 레코드(Row)의 수
    $rowNum= mysqli_num_rows($result);

    // 그 row의 갯수만큼 한줄씩 데이터를 배열로 읽어와서 echo
    for($i=0; $i<$rowNum; $i++){
        $row=mysqli_fetch_array($result,MYSQLI_ASSOC);//연관배열 한줄 읽어오기

        echo $row['no'] ."," . $row['title'] .",". $row['msg'] .",". $row['date'] . "&";
    }

    mysqli_close($db);

    


?>