<?php
include '../dbConnector.php';
$table = 'giftcard';
$giftcode = $user_input['giftcode'];
//echo "order id is $orderId<br>";

/*
CREATE TABLE `giftcard` (
  `idgiftcard` int(11) NOT NULL,
  `giftcode` varchar(45) DEFAULT NULL,
  `giftbalance` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `usedUserName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idgiftcard`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

*/

$giftCard = array(
        idgiftcard=> '',
        giftcode => '',
        giftbalance=> '',
        canuse=> '',
);


$result = mysql_query("SELECT * FROM $table where giftcode = $giftcode");

if($row = mysql_fetch_array($result)) {
    $giftCard['idgiftcard'] = $row['idgiftcard'];
    $giftCard['giftcode'] = $row['giftcode'];
    $giftCard['giftbalance'] = $row['giftbalance'];
    $giftCard['canuse'] = $row['status'];
}

$output = json_encode($giftCard);
print_r($output);

?>