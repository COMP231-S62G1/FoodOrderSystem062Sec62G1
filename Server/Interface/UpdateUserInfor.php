<?php
include '../dbConnector.php';
$table = 'user';
$name = $user_input['name'];
$pwd = $user_input['pwd'];
$email = $user_input['email'];
$phone = $user_input['phone'];
$userid = $user_input['userid'];


$result = mysql_query("update $table set name='$name',email='$email',phone='$phone',pwd='$pwd' where 
    userid='$userid')");

$arr_all = array(
  'result' => "succ",
);
$fail_all = array(
  'result' => "fail",
);
$output;
if($result==1)
{
$output = json_encode($arr_all);
}
else
{
$output = json_encode($fail_all);
}
print_r($output);
?>
