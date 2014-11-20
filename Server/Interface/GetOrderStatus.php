<?php

include '../dbConnector.php';


$table = "orders";
$cid = $user_input['orderid'];

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
