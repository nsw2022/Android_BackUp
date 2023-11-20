<?php

    header('Content-Type:application/json; charset=utf-8');

    //MySQL DB에서 market 테이블의 모든 값들을 가져와서 json 문자열로 응답
    $db= mysqli_connect("localhost","tmddn3410","a1s2d3f4!","tmddn3410");
    mysqli_query($db,"set names utf8");

    $sql= "SELECT * FROM market";
    $result = mysqli_query($db,$sql);

    //결과표($result)로 부터 총 레코드(row) 수
    $row_num= mysqli_num_rows($result);

    //여러줄을 읽어야 하기에 각 줄($row 배열)을 요소로 가질 빈 배열 준비
    $rows=array();
    for($i=0;$i<$row_num;$i++){
        $row=mysqli_fetch_array($result,MYSQLI_ASSOC); //한줄을 연관배열로
        $rows[$i]=$row;
    }
    //2차원배열($rows)--> json array 문자열로 변환
    echo json_encode($rows);

    mysqli_close($db);

?>