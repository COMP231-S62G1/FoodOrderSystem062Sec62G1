<?php
    include './dbConnector.php';
    session_start(); // Starting Session
    $error=''; // Variable To Store Error Message
    
    if (isset($user_input['submit'])) {
        if (empty($user_input['username']) || empty($user_input['password'])) {
            $error = "Please fill the form";
            //echo "<br>no Info error<br>";
        }
        else
        {
            // Define $username and $password
            $username=$user_input['username'];
            $password=$user_input['password'];
            
            //echo "<br>Submit action, $username and $password<br>";
            
            // To protect MySQL injection for Security purpose
            $username = stripslashes($username);
            $username = mysql_real_escape_string($username);
            
            $table = "operator";
            
            // SQL query to fetch information of registerd users and finds user match.
            $query = "SELECT * FROM $table WHERE name='$username' AND pwd=MD5('$password')";
            //echo "<br>$query<br>"; 
            $result = mysql_query($query);  
            if ($result != null) {
                if($row = mysql_fetch_array($result)) {
                    $login_session = $row["name"];
                    $isManager = $row["isManager"];
                    $idRest = $row["idrest"];
                    $_SESSION['login_user']=$login_session; // Initializing Session
                    $_SESSION['idrest']=$idRest; // Initializing Session
                    if($isManager){
                        $error ="manager";
                        header("location: ./manager/storeorderreport.php"); // Redirecting To Other Page
                    }else{
                        $error ="operator";
                        header("location: ./operator/orderoperate.php"); // Redirecting To Other Page
                    }
                }else{
                    $error = "Username or Password is invalid";
                }
                
            } else {
                $error = "Username or Password is invalid";
            }
        }
    }
?>