<?php

include '../dbConnector.php';


$orderId = $user_input['orderid'];
$table = 'orderline';
$join_table = 'menu';
$query = "SELECT idorderline, $table.menuid as menuid, name, pic, price, qty FROM $table  JOIN $join_table ON  $table.menuid=$join_table.menuid WHERE orderid=$orderId";
$result = mysql_query($query);


// init order detail data structure
// this structure includes orderline data and menu data together
$orderLine = array(
            "idorderline" => '',
            "menuid"=> '',
            "name" => '',
            "pic"=> '',
            "price"=> '',
            "qty"=> '',
        );
//output all query
$arrOrders = array();


/*
private String idorderline;
	private String qty;
	private String menuId;
	private String des;
	private String orderid;
*/

while ($row = mysql_fetch_array($result)) {
    //echo "<p>more detail info from menu table</p>";
    $orderLine["idorderline"] = $row["idorderline"];
    $orderLine["menuid"] = $row["menuid"];
    $orderLine["name"] = $row["name"];
    $orderLine["pic"] = $row["pic"];
    $orderLine["qty"] = $row["qty"];
    $orderLine["price"] = $row["price"];
    array_push($arrOrders, $orderLine);
}

$output = json_encode($arrOrders);
print_r($output);

?>
