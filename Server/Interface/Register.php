<?php
include '../dbConnector.php';
$table = 'user';

$username = $user_input['name'];
$email = $user_input['email'];
$pswd = $user_input['pwd'];
$phone = $user_input['phone'];

if($phone ==NULL)
$phone='';

$sql = "insert into user(name,email,pwd,balance,lastlogin,phone)
values('$username','$email',md5('$pswd'),0,Now(),'$phone')";
$result = mysql_query($sql);
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
