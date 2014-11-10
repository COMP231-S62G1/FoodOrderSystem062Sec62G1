<?php

$user_input = empty($_POST)?$_GET:$_POST;
$table = 'order';

if($_POST){
        $kv = array();
        foreach($_POST as $key => $value){
                $kv[] = "$key=$value";
        }
        $queryString = join ("&", $kv);
}

$url = "http://$_SERVER[HTTP_HOST]?".$queryString;
$query_str = parse_url($url, PHP_URL_QUERY);
parse_str($query_str, $query_params);
//print_r($query_params);

$userid = $user_input['userid'];
$username = $user_input['username'];

//connect to database
mysql_connect("localhost", "root", "wechao") or
  die("Could not connect: " . mysql_error());

//select a database
mysql_select_db("foodorder");
$sql = "insert into orders(userid,ordertime,username,status)
values('$userid',Now(),'$username',0)";
$result = mysql_query($sql);
$ret = mysql_insert_id();

foreach ($query_params as $k => $v) {
    if($k!='userid'&&$k!='username')
	{
	$sql1 = "INSERT INTO orderline (menuid,qty,orderid) VALUES ('$k','$v','$ret');";
	$result1 = mysql_query($sql1);
	}
}

$arr_all = array(
  'result' => $ret,
);
$output = json_encode($arr_all);
print_r($output);
?>
