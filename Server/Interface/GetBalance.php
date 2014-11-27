<?php
include '../dbConnector.php';
$table = 'user';
$userid = $user_input['userid'];
//echo "order id is $orderId<br>";

$result = mysql_query("SELECT balance FROM $table where userid='$userid')");

$user = array(
        "balance"=>""
    );

if($row = mysql_fetch_array($result)) {
    $user['balance'] = $row['balance'];
    $output = json_encode($user);
    print_r($output);
}
?>
