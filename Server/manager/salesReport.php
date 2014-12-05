<?php
    $restid = $idRest;
    $table = "orderline";
    $date = $user_input['dateon'];
    if($date == null)
        $date = date("Y-m-d");

/*
SELECT SUM(OL.amount) as amount,
SUM(OL.sales) as sales,
DATE(orders.orderTime) as date
from orders LEFT JOIN 
(
    SELECT orderline.idorderline
	, SUM(orderline.qty)*menu.price AS amount
    , SUM(orderline.qty) AS sales
	, orderline.orderid 
	FROM menu LEFT JOIN orderline
	ON menu.menuid=orderline.menuid
	GROUP BY orderline.idorderline
) AS OL
 ON OL.orderid=orders.idorder
WHERE DATE(orders.orderTime)>DATE(DATE_ADD(DATE(NOW()), INTERVAL -7 day))
GROUP BY DATE(orders.orderTime)
*/

    // show today's sales report
    $query = "SELECT";
    $query .= " SUM(OL.amount) as amount, ";
    $query .= " SUM(OL.sales) as sales, ";
    $query .= " DATE(orders.orderTime) as date ";
    $query .= " from orders LEFT JOIN (";
    $query .= " SELECT orderline.idorderline ";
    $query .= " , SUM(orderline.qty)*menu.price AS amount ";
    $query .= " , SUM(orderline.qty) AS sales ";
    $query .= " , orderline.orderid ";
    $query .= " FROM menu LEFT JOIN orderline ON menu.menuid=orderline.menuid ";
    $query .= " WHERE menu.restid=$restid ";
    $query .= " GROUP BY orderline.idorderline ";
    $query .= " ) AS OL ON OL.orderid=orders.idorder ";
    $query .= " WHERE DATE(orders.orderTime)>DATE(DATE_ADD(DATE(NOW()), INTERVAL -7 day)) ";
//    $query .= " AND status=2";
    $query .= " GROUP BY DATE(orders.orderTime) ";
    $query .= " ORDER BY DATE(orders.orderTime) DESC";

    $sales = array(
        "amount"=>"",
        "sales"=>"",
        "date"=>""
    );

    $arrSales = array();

    $result = mysql_query($query);

    //echo "<p>$query</p>";

    while ($row = mysql_fetch_array($result)) {
        //echo "<p>more detail info from menu table</p>";
        $sales["amount"] = $row["amount"];
        $sales["sales"] = $row["sales"];
        $sales["date"] = $row["date"];
        if($sales["sales"] == null)
            $sales["sales"] = 0;
        if($sales["amount"] == null)
            $sales["amount"] = 0;
        array_push($arrSales, $sales);
    }
    
    $nCount = count($arrSales);
    
 

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
