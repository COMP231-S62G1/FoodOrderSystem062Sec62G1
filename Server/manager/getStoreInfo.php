<?php

$table = "rest";

$query = "SELECT * FROM $table WHERE idrest=$idRest";
$result = mysql_query($query);
if($row = mysql_fetch_array($result)) {
    $restName = $row['name'];
    $restPic = $row['pic'];
    $restDesc = $row['des'];
}


?>