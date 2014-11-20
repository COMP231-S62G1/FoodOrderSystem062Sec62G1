<?php

include '../dbConnector.php';

$table = 'menu';
$cid = $user_input['restid'];

//select all category
$result = mysql_query("SELECT * FROM $table where restid = $cid ORDER BY menuid");

$rst = array(
        menuid=> '',
        restid => '',
        name=> '',
        pic=> '',
        des => '',
        price => '',
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
    $rst['price'] = $row['price']; 
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
