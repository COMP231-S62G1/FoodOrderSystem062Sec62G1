<?php

include '../dbConnector.php';


$table = "orderline";
$orderId = $user_input['orderid'];
$query = "SELECT * FROM $table WHERE orderid=$orderId";
$result = mysql_query($query);


// init order detail data structure
// this structure includes orderline data and menu data together
$orderLine = array(
                "qty" => '',
                "menuid"=> '',
                "des" => '',
                "orderid"=> '',
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
    $orderLine["des"] = $row["des"];
    $orderLine["menuid"] = $row["menuid"];
    $orderLine["qty"] = $row["qty"];
    $orderLine["idorderline"] = $row["idorderline"];
    array_push($arrOrders, $orderLine);
}

$output = json_encode($arrOrders);
print_r($output);

?>
