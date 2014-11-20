<?php
include '../../dbConnector.php';

$table = 'order';
$orderId = $user_input['orderId'];

$sql = "select idorder,userid,ordertime,username,'status' from orders';
$result = mysql_query($sql);

?>
