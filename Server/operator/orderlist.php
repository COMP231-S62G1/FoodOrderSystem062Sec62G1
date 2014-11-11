<html>
    <head>
        <title>Food Order, order processing system</title>
        <meta http-equiv="refresh" content="5" />
        
        <script type="text/javascript">
            function ChangeColor(tableRow, highLight){
                if (highLight){
                    tableRow.style.backgroundColor = '#dcfac9';
                }else{
                    tableRow.style.backgroundColor = 'white';
                }
            }

            function DoNav(orderId){
                var url = "./orderdetail.php?orderid=";
                var finalUrl = url.concat(orderId);
                window.location = finalUrl;
            }
        </script> 
    </head>
    <body>


<?php

$user_input = empty($_POST)?$_GET:$_POST;
$table = "orders";
$nPage = $user_input['page'];



//connect to database
mysql_connect("localhost", "root", "admin1234") or
  die("Could not connect: " . mysql_error());

//select a database
mysql_select_db("foodorder");
//select all category
$result = mysql_query("SELECT * FROM $table WHERE status=0 ORDER BY orderTime"); //ORDER BY idrest");

//userid,ordertime,username,status
$order = array(
				"orderId"=>"test",
				"userId"=>"test ID",
				"orderTime"=>"when?",
				"userName"=>"my name",
				"orderStatus"=>"init order"
			);


//output all query
$arrOrder = array();
$nCnt = 0;


// temp for test
/*
for($cnt=0;$cnt<10;$cnt++){
    $order["orderId"] = $cnt;
    array_push($arrOrder, $order);
}
*/


while ($row = mysql_fetch_array($result)) {
	$nCnt++;
	$order["orderId"] = $row["idorder"];
	$order["userId"] = $row["userid"];
	$order["orderTime"] = $row["orderTime"];
	$order["userName"] = $row["username"];
	$order["orderStatus"] = $row["status"];
	array_push($arrOrder, $order);
}



?>
<table border="1px" style="width:100%" border-spacing="15px">
<tr>
	<td>Order Number</td>
	<td>User Account</td>
	<td>Ordered Time</td>
	<td>Order Status</td>
    <td>Price</td>
    <td>Tax</td>
    <td>Price (with Tax)</td>
</tr>

<?php
	$nCount = count($arrOrder);
	echo date("h").":".date("m").":".date("s");

	if($nCount > 0)
	{
		for($nCnt = 0; $nCnt < $nCount ; $nCnt++){
			$tempRow = $arrOrder[$nCnt];
            echo "<tr onmouseover='ChangeColor(this, true);'\n
                    onmouseout='ChangeColor(this, false);'\n
                    onclick='DoNav(\"".$tempRow['orderId']."\");'>";
			echo "<td> {$tempRow['orderId']} </td>";
			echo "<td> {$tempRow['userName']} </td>";
			echo "<td> {$tempRow['orderTime']} </td>";
			echo "<td> {$tempRow['orderStatus']} </td>";
            echo "<td> Price </td>";
            echo "<td> Tax </td>";
            echo "<td> P+T </td>\n</tr>";
		}
	}
	else{
		echo"<td colspan='4'> There is no unprocessed order</td>";
	}
	
?>

</table>
<br/>
<br/>
<?php include 'footer.php';?>


    </body>
</html>
