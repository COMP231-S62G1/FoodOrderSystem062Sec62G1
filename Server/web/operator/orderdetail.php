
<!-- include header -->
<?php include './operatorDetailHeader.php';?>

<!-- include db connector -->
<?php include '../dbConnector.php';?>


<!-- get parameters and set db table -->
<?php
    $table = "orderline";
    $user_input = empty($_POST)?$_GET:$_POST;
    $orderId = $user_input['orderid'];
?>

<!-- load data from database server -->
<?php
    //select all orders
    $query = "SELECT * FROM $table WHERE orderid=$orderId";
    $result = mysql_query($query);

   
    // init order detail data structure
    // this structure includes orderline data and menu data together
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

    while ($row = mysql_fetch_array($result)) {
        //echo "<p>more detail info from menu table</p>";
        $orderLine["des"] = $row["des"];
        $orderLine["menuid"] = $row["menuid"];
        $orderLine["qty"] = $row["qty"];
        $orderLine["idorderline"] = $row["idorderline"];
        array_push($arrOrders, $orderLine);
    }

    $nItemCount = count($arrOrders);

    $table = "orders";
    $status = 100;
    $result = mysql_query("SELECT status FROM $table WHERE idorder=$orderId");
    while ($row = mysql_fetch_array($result)) {
        $status = $row["status"];
    }


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
            array_push($arrLines, $item);
        }

    }

    //echo "<H1><p>Order detail of Order number: ".$orderId."</p></H1>";


?>


<!-- content begins -->
    <div id="main">
<!-- user search filtering option -->
<?php include './orderDetailRight.php';?>
        
<!-- data view table -->
<?php include './operatorLeftMenu.php';?>
        
    
    <!--div main ends -->
    </div>

    <!--div back-all ends -->
	</div>

<!--div content ends -->
</div>

<!--footer begins -->

<?php include './footer.php';?>





