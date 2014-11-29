<?php
include '../dbConnector.php';
$table = 'user';
$name = $user_input['name'];
$pwd = $user_input['pwd'];
//echo "order id is $orderId<br>";

$result = mysql_query("SELECT * FROM $table where (name='$name' AND pwd=MD5('$pwd'))");
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
    
array_push($arr_category, $rst);
}

$output = json_encode($arr_category);
print_r($output);


?>
