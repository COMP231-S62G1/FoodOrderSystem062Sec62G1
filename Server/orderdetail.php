<html>
    <head>
        <title>Food Order, order processing system</title>
    </head>
    <body>


<?php

$table = "orderline";
$user_input = empty($_POST)?$_GET:$_POST;
$orderId = $user_input['orderid'];

// just to test
//echo "<p>query: $table, $orderId</p>";


//connect to database
mysql_connect("localhost", "root", "admin1234") or
  die("Could not connect: " . mysql_error());

//select a database
mysql_select_db("foodorder");
//select all category
$query = "SELECT * FROM $table WHERE orderid=$orderId";


//echo "<p>$query</p>";



$result = mysql_query($query);

//userid,ordertssxxxxime,username,status
$orderLine = array(
                "des" => '',
                "idorderline" => '',
				"menuid"=> '',
				"orderid"=> '',
                "qty" => '',
                "restid" => '',
                "name"=> '',
                "pic"=> '',              
                "price" => 5.7
			);
//output all query
$arrOrders = array();
$nCnt = 0;


while ($row = mysql_fetch_array($result)) {
    //echo "<p>more detail info from menu table</p>";
	$nCnt++;
    $orderLine["des"] = $row["des"];
	$orderLine["menuid"] = $row["menuid"];
	$orderLine["qty"] = $row["qty"];
    $orderLine["idorderline"] = $row["idorderline"];
	array_push($arrOrders, $orderLine);
}

$nItemCount = count($arrOrders);

//echo "<p>nCnt=$nCnt,   nItemCount=$nItemCount</p>";


// fill menu's own data
$table = "menu";
$arrLines = array();
if($nItemCount > 1){
    foreach ($arrOrders as $item) {
        $result = mysql_query("SELECT * FROM $table where menuid={$item["menuid"]}");
        $row = mysql_fetch_array($result);
        $item['restid'] = $row['restid'];
        $item['name'] = $row['name'];
        $item['pic'] = $row['pic'];
        //$item['des'] = $row['des'];
        //echo "<p>Something to add!!! id:{$item["menuid"]}, name:{$item['name']}, pic{$item['pic']}</p>";
        array_push($arrLines, $item);
    }

}

echo "<H1><p>Order detail of Order number: ".$orderId."</p></H1>";



?>

        
<table border="1" width="100%">
<tr>
    <td>Image</td>
	<td>Item</td>
	<td>Item Code</td>
	<td>Unit Price</td>
    <td>Ordered Quantity</td>
    <td>Price</td>
</tr>

<?php
	$nCount = count($arrLines);
	echo date("h").":".date("m").":".date("s");

/*                "des" => '',
                "idorderline" => '',
				"menuid"=> '',
				"orderid"=> '',
                "qty" => '',
                "restid" => '',
                "name"=> '',
                "pic"=> '',              
                "price" => 5.7*/

	if($nCount > 0)
	{
		for($nCnt = 0; $nCnt < $nCount ; $nCnt++){
			$tempRow = $arrLines[$nCnt];
            $picLink = '';
            if($tempRow['pic'] != ''){
                $picLink = "<img src=".$tempRow['pic']." >";
            }
            $totPrice = $tempRow['qty'] * $tempRow['price'];
            echo "<tr>";
			echo "<td> $picLink </td>";
			echo "<td> {$tempRow['name']} </td>";
			echo "<td> {$tempRow['menuid']} </td>";
			echo "<td> {$tempRow['price']} </td>";
            echo "<td> {$tempRow['qty']} </td>";
            echo "<td> $totPrice </td>\n</tr>";
		}
	}
	else{
		echo"<td colspan='6'> No order details</td>";
	}
	
?>
    

</table>

<br/>
<br/>
<?php include 'footer.php';?>


    </body>
</html>
