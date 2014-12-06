<?php

include '../dbConnector.php';

$table = 'order';

if($_POST){
        $kv = array();
        foreach($_POST as $key => $value){
                $kv[] = "$key=$value";
        }
        $queryString = join ("&", $kv);
}

$url = "http://$_SERVER[HTTP_HOST]?".$queryString;
$query_str = parse_url($url, PHP_URL_QUERY);
parse_str($query_str, $query_params);
//print_r($query_params);

$userid = $user_input['userid'];
$username = $user_input['username'];
$amount = $user_input['amount'];

$sql = "SELECT balance FROM user WHERE userid=$userid";
$result = mysql_query($sql);
if($row = mysql_fetch_array($result)) {
    $myBalance = $row["balance"];
}

if( ($amount*100) > $myBalance ){
    $arr_all = array(
      'result' => "Not Enough Balance",
    );
    $output = json_encode($arr_all);
    print_r($output);
}
else{
    $myBalance = $myBalance - ($amount*100);
    $sql = "UPDATE user SET balance=$myBalance WHERE userid=$userid";
    $result = mysql_query($sql);
    $sql = "INSERT INTO orders(userid,ordertime,username,status) VALUES('$userid',Now(),'$username',0)";
    $result = mysql_query($sql);
    $ret = mysql_insert_id();

    foreach ($query_params as $k => $v) {
        if($k!='userid'&&$k!='username' && $k!='amount')
        {
            $sql1 = "INSERT INTO orderline (menuid,qty,orderid) VALUES ('$k','$v','$ret');";
            $result1 = mysql_query($sql1);
        }
    }
    

    $arr_all = array(
      'result' => $ret,
    );
    $output = json_encode($arr_all);
    print_r($output);
}

?>
