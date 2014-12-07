<?php

include '../../dbConnector.php';

$table = 'orders';
$orderId = $user_input['orderId'];

//echo "order id is $orderId<br>";

$sql = "update $table set status = 2 where idorder=$orderId";
$result = mysql_query($sql);

echo $sql."<br/>";
echo $result."result";

?>


<script type="text/javascript"> 
    location.replace('../../operator/orderdetail.php?orderid=<?php echo "$orderId"; ?>'); 
</script> 