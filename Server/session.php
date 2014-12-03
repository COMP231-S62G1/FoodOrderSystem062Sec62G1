<?php
    include './dbConnector.php';
    // Establishing Connection with Server by passing server_name, user_id and password as a parameter
    
    $table = "operator";
    
    session_start();// Starting Session
    // Storing Session
    $user_check=$_SESSION['login_user'];
    // SQL Query To Fetch Complete Information Of User
    $query = "SELECT * FROM $table WHERE name='$user_check'";
    $result = mysql_query($query);  

    if($row = mysql_fetch_array($result)) {
        $login_session = $row["name"];
        $idRest = $row["idrest"];
    }

    if(!isset($login_session) || !isset($idRest) ){
        header('Location: ./default.php'); // Redirecting To Home Page
    }
?>