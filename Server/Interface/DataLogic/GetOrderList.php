<?php
$table = 'order';
$orderId = $user_input['orderId'];

//connect to database
mysql_connect("localhost", "root", "wechao") or
  die("Could not connect: " . mysql_error());
//select a database
mysql_select_db("foodorder");
$sql = "select idorder,userid,ordertime,username,'status' from orders';
$result = mysql_query($sql);

?>
