<?php
include '../dbConnector.php';
$table = 'rejection';
$orderId = $user_input['orderid'];
//echo "order id is $orderId<br>";

$result = mysql_query("SELECT rejectReason FROM $table where orderId = $orderId");

$reason = 'Error';
if($row = mysql_fetch_array($result)) {
    $reason = $row['rejectReason'];
}

print_r($reason);

?>