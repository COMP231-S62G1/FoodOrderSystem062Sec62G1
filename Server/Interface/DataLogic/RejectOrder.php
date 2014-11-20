<?php
include '../../dbConnector.php';

$table = 'orders';
$orderId = $user_input['orderId'];
$reason = $user_input['reason'];

//echo "order id is $orderId<br>";

$sql = "update $table set status = 3 where idorder=$orderId";
$result = mysql_query($sql);

$table = 'rejection';
$sql = "INSERT INTO $table (orderId,rejectReason) VALUES ($orderId,'$reason');";
$result = mysql_query($sql);

/*
echo $sql."<br/>";
echo $result."result";
echo "<br/>Reason is <br/>".$reason;
*/

?>

<script type="text/javascript"> 
    location.replace('../../operator/orderdetail.php?orderid=<?php echo "$orderId"; ?>'); 
</script> 
