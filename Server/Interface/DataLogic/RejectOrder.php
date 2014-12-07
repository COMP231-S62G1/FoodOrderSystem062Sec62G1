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

$sql = "SELECT SUM(orderline.qty * menu.price) AS total FROM orderline INNER JOIN menu ON orderline.menuid=menu.menuid WHERE orderid=$orderId;";
$total = 0;
$curBal = 0;
$result = mysql_query($sql);
if($row = mysql_fetch_array($result)) {
    $total = $row['total'];
    $total = $total * 0.13;
    $total *= 100;
    $total = round($total);
    $total /= 100;
}
if($total>0){
    $sql="SELECT balance, user.userid AS id FROM user LEFT JOIN orders ON user.userid=orders.userid WHERE idorder=$orderId;";
    $result = mysql_query($sql);
    if($row = mysql_fetch_array($result)) {
        $curBal = $row['balance'];
        $userId = $row['id'];
        $backBal = $curBal + $total;
        $sql="UPDATE user SET balance=$backBal WHERE userid=$userId;";
        $result = mysql_query($sql);
    }
}
/*
echo $sql."<br/>";
echo $result."result";
echo "<br/>Reason is <br/>".$reason;
*/

?>

<script type="text/javascript"> 
    location.replace('../../operator/orderdetail.php?orderid=<?php echo "$orderId"; ?>'); 
</script> 
