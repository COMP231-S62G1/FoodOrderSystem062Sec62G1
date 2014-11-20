<?php
include '../../dbConnector.php';

$table = 'order';
$orderId = $user_input['orderId'];

$sql = "update orders set status = 1 where orderid=$orderId';
$result = mysql_query($sql);

?>
