<?php
    session_start();
    if(isset($_SESSION['login_user']))
        unset($_SESSION['login_user']);  //Is Used To Destroy Specified Session
    if(isset($_SESSION['idrest']))
        unset($_SESSION['idrest']);  //Is Used To Destroy Specified Session
    if(session_destroy()) // Destroying All Sessions
    {
        header("Location: default.php"); // Redirecting To Home Page
    }
?>