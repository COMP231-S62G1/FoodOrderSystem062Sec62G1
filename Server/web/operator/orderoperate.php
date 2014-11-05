
<!-- include header -->
<?php include './operatorHeader.php';?>

<!-- include db connector -->
<?php include '../dbConnector.php';?>


<!-- get parameters and set db table -->
<?php
    $user_input = empty($_POST)?$_GET:$_POST;
    $table = "orders";
    $nPage = $user_input['page'];
?>

<!-- load data from database server -->
<?php
    //select all orders
    $result = mysql_query("SELECT * FROM $table WHERE (status=0 OR status=1) ORDER BY orderTime"); //ORDER BY idrest");

    //init order data structure
    $order = array(
                    "orderId"=>"",
                    "userId"=>"",
                    "orderTime"=>"",
                    "userName"=>"",
                    "orderStatus"=>""
                );
    //init order list array
    $arrOrder = array();

    // load data to order array
    while ($row = mysql_fetch_array($result)) {
        $order["orderId"] = $row["idorder"];
        $order["userId"] = $row["userid"];
        $order["orderTime"] = $row["orderTime"];
        $order["userName"] = $row["username"];
        $order["orderStatus"] = $row["status"];
        array_push($arrOrder, $order);
    }

?>


<!-- content begins -->
    <div id="main">
<!-- user search filtering option -->
<?php include './operatorRightMenu.php';?>
        
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