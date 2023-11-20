<?php

    header('Content-Type:text/html; charset=utf-8');

    //MySQL DB접속
    $db= mysqli_connect("localhost","tmddn3410","a1s2d3f4!","tmddn3410"); //DB서버주소,DB접속아이디,DB접속비번,DB파일명

    //한글깨짐 방지
    mysqli_query($db,"set names utf8");

    //board 테이블의 데이터를 모두 읽어오기 [SELECT 쿼리문]
    $sql= "SELECT * FROM board"; 
    $result= mysqli_query($db, $sql); //쿼리문의 요청을 수행하고 그 결과표를 리턴해줌
    //$result는 검색결과표를 가진 객체 or false
    if($result){

        //총 레코드(한줄:row) 수
        $rowNum= mysqli_num_rows($result);

        for($i=0; $i<$rowNum; $i++){
            //한줄의 데이터들을 배열로 리턴해줘. 단, 배열을 연관배열로 주세요. [연관배열: 배열 방번호 대신에 칸 이름으로 된 배열]
            $row= mysqli_fetch_array($result, MYSQLI_ASSOC);

            echo $row['no']    . "<br>";
            echo "<h2>" . $row['title'] . "</h2>";                        
            echo $row['msg']   . "<br>";                        
            echo $row['date']  . "<br>";

            $img= $row['file'];
            if($img != null) echo "<img src='$img' width='50%'><br>";
            echo "=========================<br><br>";
        }

    }else{
        echo "검색실패";
    }

    mysqli_close($db);
?>