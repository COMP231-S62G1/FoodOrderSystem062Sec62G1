<?php
include '../dbConnector.php';
$table = 'user';
$userid = $user_input['userid'];

$result = mysql_query("SELECT balance FROM $table where userid='$userid'");

$user = array(
        "result"=>""
    );
if($row = mysql_fetch_array($result)) {
    $user['result'] = $row['balance'];
    $output = json_encode($user);
    print_r($output);
}
?>
