<?php
include '../dbConnector.php';
$table = 'giftcard';
$giftcode = $user_input['giftcode'];
$userid = $user_input['userid'];

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
    
    if( $giftCard['canuse'] == 1){
        $table = 'user';
        $result = mysql_query("SELECT * FROM $table where userid=$userid");
        $user = array(
                "userid"=>"",
                "name"=>"",
                "balance"=>"",
                "email"=>"",
                "phone"=>"",
                "pwd"=>""
            );
        if($row = mysql_fetch_array($result)) {
            $user['userid'] = $row['userid'];
            $user['name'] = $row['name'];
            $user['balance'] = $row['balance'];
            $user['email'] = $row['email'];
            $user['phone'] = $row['phone'];
            $user['pwd'] = "";

            $updateBalance = $user['balance'] + $giftCard['giftbalance'];

            $sql = "update $table set balance = $updateBalance where userid=$userid";
            mysql_query($sql);

            $table = 'giftcard';
            $sql = "update $table set status = 0 where giftcode=$giftcode";
            mysql_query($sql);

            $arr_all = array(
              'result' => $updateBalance,
            );
            $output = json_encode($arr_all);
            print_r($output);
        }
    }
}
?>