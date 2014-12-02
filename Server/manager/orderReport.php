<?php
    include '../dbConnector.php';
    $restid = $user_input['restid'];
    $table = "orderline";
    $date = $user_input['dateon'];
    if($date == null)
        $date = date("Y-m-d");

    // show today's sales report
    $query = "SELECT";
//    $query .= " DATE(orders.orderTime) as orderDay,";
    $query .= " menu.menuid, ";
    $query .= " menu.name, ";
    $query .= " menu.pic, ";
    $query .= " menu.price, ";
    $query .= " SUM(orderline.qty) as sales,";
    $query .= " SUM(orderline.qty)*menu.price AS amount ";
    $query .= " FROM (orderline INNER JOIN orders ON orderline.orderid = orders.idorder)";
    $query .= " INNER JOIN menu ON orderline.menuid=menu.menuid";
    $query .= " WHERE menu.restid=$restid";
//    $query .= " AND status=2";
    $query .= " AND DATE(orders.orderTime)=DATE('$date')";
    $query .= " GROUP BY menu.menuid";
    $query .= " ORDER BY amount DESC";

    $sales = array(
        "menuid"=>"",
        "name"=>"",
        "pic"=>"",
        "price"=>"",
        "sales"=>"",
        "amount"=>"",
        "prevSales"=>"",
        "prevAmount"=>"",
    );

    $arrSales = array();

    $result = mysql_query($query);

    //echo "<p>$query</p>";

    while ($row = mysql_fetch_array($result)) {
        //echo "<p>more detail info from menu table</p>";
        $sales["menuid"] = $row["menuid"];
        $sales["name"] = $row["name"];
        $sales["pic"] = $row["pic"];
        $sales["price"] = $row["price"];
        $sales["sales"] = $row["sales"];
        $sales["amount"] = $row["amount"];
        array_push($arrSales, $sales);
    }
    
    //$arrPrevSales = array();

    $nCount = count($arrSales);
    for($nCnt = 0; $nCnt < $nCount ; $nCnt++){
        $tempRow = $arrSales[$nCnt];
        $menuid = $tempRow["menuid"];
        $subQuery = "SELECT";
        $subQuery .= " SUM(orderline.qty) / 30 as sales,";
        $subQuery .= " SUM(orderline.qty)*menu.price / 30 AS amount ";
        $subQuery .= " FROM (orderline INNER JOIN orders ON orderline.orderid = orders.idorder)";
        $subQuery .= " INNER JOIN menu ON orderline.menuid=menu.menuid";
        $subQuery .= " WHERE menu.restid=$restid ";
        //$subQuery .= " AND (status=2) ";
        $subQuery .= " AND menu.menuid=$menuid";
        $subQuery .= " AND (DATE(orders.orderTime)>=DATE(DATE_ADD(NOW(), INTERVAL -30 day))";
        $subQuery .= " AND (DATE(orders.orderTime)<DATE(NOW())) )";
        $subQuery .= " GROUP BY menu.menuid";
        $subQuery .= " ORDER BY amount DESC";
        $subresult = mysql_query($subQuery);
        
        //echo "<br>Sub Query<br><p>$subQuery</p>";
        
        if($row = mysql_fetch_array($subresult)){
            $arrPrevSales[$nCnt]["prevSales"] = $row["sales"];
            $arrPrevSales[$nCnt]["prevAmount"] = $row["amount"];
        }
        else{
            $arrPrevSales[$nCnt]["prevSales"] = 0;
            $arrPrevSales[$nCnt]["prevAmount"] = 0;
        }
        
        //echo "<p> MenuID:$menuid, Sales:{$tempRow["sales"]} (Ave:{$arrPrevSales[$nCnt]["prevSales"]})</p>";
    }

/*
SELECT 
    DATE(orders.orderTime) as orderDay, 
    SUM(orderline.qty) as sales, 
    menu.name, 
    menu.pic, 
    menu.price, 
    SUM(orderline.qty)*menu.price AS amount, 
    DATE(DATE_ADD(NOW(), INTERVAL -1 day)) 
FROM (orderline INNER JOIN orders ON orderline.orderid = orders.idorder)
    INNER JOIN menu ON orderline.menuid=menu.menuid
WHERE menu.restid=1 AND (status=0 OR status=1)
    AND DATE(orders.orderTime)>=DATE(DATE_ADD(NOW(), INTERVAL -60 day))
GROUP BY menu.menuid, DATE(orders.orderTime)
ORDER BY SUM(orderline.qty) DESC
*/


/*
    Order Report: Order of the day
    Sales Report: Accumulated sales report
*/

?>
