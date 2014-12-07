<?php
    $restid = $idRest;
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
    $query .= " SUM(OL.qty) as sales,";
    $query .= " SUM(OL.qty)*menu.price AS amount ";
    $query .= " FROM menu LEFT JOIN (SELECT * FROM ";
    $query .= "(SELECT * from orders WHERE DATE(orders.orderTime)=DATE('$date') ) AS O ";
    $query .= " LEFT JOIN orderline ON orderline.orderid = O.idorder) AS OL ON OL.menuid=menu.menuid ";
    $query .= " WHERE menu.restid=$restid";
//    $query .= " AND status=2";
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
        if($sales["sales"] == null)
            $sales["sales"] = 0;
        if($sales["amount"] == null)
            $sales["amount"] = 0;
        array_push($arrSales, $sales);
    }
    
    
    //$arrPrevSales = array();

    $nCount = count($arrSales);
    for($nCnt = 0; $nCnt < $nCount ; $nCnt++){
        $tempRow = $arrSales[$nCnt];
        $menuid = $tempRow["menuid"];
        $subQuery = "SELECT";
        $subQuery .= " SUM(orderline.qty) / 7 as sales,";
        $subQuery .= " SUM(orderline.qty)*menu.price / 7 AS amount ";
        $subQuery .= " FROM (orderline INNER JOIN orders ON orderline.orderid = orders.idorder)";
        $subQuery .= " INNER JOIN menu ON orderline.menuid=menu.menuid";
        $subQuery .= " WHERE menu.restid=$restid ";
        $subQuery .= " AND (status=2) ";
        $subQuery .= " AND menu.menuid=$menuid";
        $subQuery .= " AND (DATE(orders.orderTime)>=DATE(DATE_ADD(DATE('$date'), INTERVAL -7 day))";
        $subQuery .= " AND (DATE(orders.orderTime)<DATE('$date')) )";
        $subQuery .= " GROUP BY menu.menuid";
        $subQuery .= " ORDER BY amount DESC";
        $subresult = mysql_query($subQuery);
        
        //echo "<br>Sub Query<br><p>$subQuery</p>";
        
        if($row = mysql_fetch_array($subresult)){
            $arrSales[$nCnt]["prevSales"] = $row["sales"];
            $arrSales[$nCnt]["prevAmount"] = $row["amount"];
        }
        else{
            $arrSales[$nCnt]["prevSales"] = 0;
            $arrSales[$nCnt]["prevAmount"] = 0;
        }
        //echo "Name:{$tempRow['name']} Sales: {$tempRow['sales']}, {$tempRow['PrevSales']}";
        
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
