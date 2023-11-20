<?php

    header('Content-Type:text/html; charset=utf-8');

    $id = $_POST['id'];
    $password = $_POST['pw'];

    //사용자[web browser]에게 출력(응답)
    echo "아이디 : $id <br>";
    echo "비밀번호 : $password";

?>