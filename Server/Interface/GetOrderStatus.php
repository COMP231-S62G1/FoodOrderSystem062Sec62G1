<?php

$user_input = empty($_POST)?$_GET:$_POST;
$table = "orders";
$cid = $user_input['orderid'];


//connect to database
mysql_connect("localhost", "comp231", "comp231") or
  die("Could not connect: " . mysql_error());

//select a database
mysql_select_db("foodorder");

//select orderId
$result = mysql_query("SELECT * FROM $table WHERE idorder=$cid");

$order = array(
            "result"=>""
        );
$row = mysql_fetch_array($result);
$order["result"] = $row["status"];



$output = json_encode($order);
print_r($output);


?>
