<?php

include '../dbConnector.php';

$table = 'rest';

//select all category
$result = mysql_query("SELECT * FROM $table ORDER BY idrest");

$rst = array(
        idrest=> '',
        name => '',
        pic=> '',
        des=> '',
);

//output all query
$arr_category = array();
$i = 0;
while ($row = mysql_fetch_array($result)) {
  $i++;
  $rst['idrest'] = $row['idrest'];
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
