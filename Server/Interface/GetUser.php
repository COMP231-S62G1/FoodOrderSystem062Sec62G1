<?php
include '../dbConnector.php';
$table = 'user';
$userid = $user_input['userid'];
//echo "order id is $orderId<br>";

$result = mysql_query("SELECT * FROM $table WHERE userid='userid' ");
$arr_category = array();
$user = array(
        "userid"=>"",
        "name"=>"",
        "balance"=>"",
        "email"=>"",
        "phone"=>"",
        "pwd"=>""
    );

if($row = mysql_fetch_array($result)) {
    $user['userid'] = $row['userid'];
    $user['name'] = $row['name'];
    $user['balance'] = $row['balance'];
    $user['email'] = $row['email'];
    $user['phone'] = $row['phone'];
    $user['pwd'] = "";
    
array_push($arr_category,  $user);
}

$output = json_encode($arr_category);
print_r($output);
?>