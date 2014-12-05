<?php
include '../dbConnector.php';
$table = 'user';
$username = $user_input['username'];
$query = "SELECT count(name) AS many FROM $table WHERE name='$username'";
$result = mysql_query($query);

if ($row = mysql_fetch_array($result)) {
  $many = $row['many'];
}

$arr_all = array(
  'result' => $many,
);

$output = json_encode($arr_all);
print_r($output);
?>