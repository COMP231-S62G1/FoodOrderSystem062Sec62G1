<?php

$table = "operator";

$query = "SELECT * FROM $table WHERE idrest=$idRest";
$result = mysql_query($query);

//echo $query;

$operator = array(
        "idOper"=>"",
        "name"=>"",
        "empId"=>"",
        "idrest"=>"",
        "isManager"=>""
    );

$arrOperators = array();

while($row = mysql_fetch_array($result)) {
    $operator['idOper'] = $row['idOper'];
    $operator['name'] = $row['name'];
    $operator['empId'] = $row['empId'];
    $operator['idrest'] = $row['idrest'];
    $operator['isManager'] = $row['isManager'];
    array_push($arrOperators, $operator);
}


?>