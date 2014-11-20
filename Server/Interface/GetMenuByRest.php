<?php

$user_input = empty($_POST)?$_GET:$_POST;
$table = 'menu';
$cid = $user_input['restid'];


//connect to database
mysql_connect("localhost", "root", "wechao") or
  die("Could not connect: " . mysql_error());

//select a database
mysql_select_db("foodorder");

//select all category
$result = mysql_query("SELECT * FROM $table where restid = $cid ORDER BY menuid");

$rst = array(
        menuid=> '',
        restid => '',
        name=> '',
        pic=> '',
        des => '',
);

//output all query
$arr_category = array();
$i = 0;
while ($row = mysql_fetch_array($result)) {
  $i++;
  $rst['menuid'] = $row['menuid'];
  $rst['restid'] = $row['restid'];
  $rst['name'] = $row['name'];
  $rst['pic'] = $row['pic'];
 $rst['des'] = $row['des'];
 array_push($arr_category, $rst);
}


$arr_all = array(
  'result' => "succ",
  'data' => $arr_category,
);

$output = json_encode($arr_category);
print_r($output);


?>
