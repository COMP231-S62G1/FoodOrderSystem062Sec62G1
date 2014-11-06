<?php

//get user input
$user_input = empty($_POST)?$_GET:$_POST;


//connect to database
mysql_connect("localhost", "root", "wechao") or
  die("Could not connect: " . mysql_error());

//select a database
mysql_select_db("foodorder");

//echo "dbConnector.php echo <br>";

?>