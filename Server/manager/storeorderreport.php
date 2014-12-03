<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Centennial College Food Order</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="../styles.css" rel="stylesheet" type="text/css" media="screen" />
<style type="text/css">
.auto-style1 {
	text-decoration: none;
}
</style>
</head>


    
<body>

<?php
include "orderReport.php";
?>
<div id="content">
	<div id="back_all">
<!-- header begins -->
<?php 
    include './storeheader.php';
?>
<!-- header ends -->
<!-- content begins -->
 <div id="main">
 	<div id="right">
		<h3>Welcome To Centennial College Food Order</h3><br />
			<h4>Order Report</h4>
            <br/>
            <form action='./storeorderreport.php' method='POST' id='acceptForm'>
            <input type="hidden" name="restid" value="<?php echo $restid; ?>"/>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;Select date to report&nbsp;:&nbsp;&nbsp;<input type="date"  id="dateon" name="dateon" value=<?php echo $date ?> max='<?php echo date("Y-m-d")?>' />&nbsp;&nbsp;&nbsp;<input type="submit" value="Apply Now" /></p>
            </form>
		<p>
		<table style="width: 100%">
			<tr>
				<th style="height: 18px">Image</th>
                <th style="height: 18px">Menu</th>
                <th style="height: 18px">Sales (Daily)</th>
                <th style="height: 18px">Amount (Daily)</th>
			</tr>
<?php
    
    if($nCount <= 0){
        echo "<tr><td colspan=4 align='center'>No sales result</td></tr>";
    }
    $totSales = 0;
    $totAmt = 0;
    $prevTotSales = 0;
    $prevTotAmt = 0;
    for($nCnt = 0; $nCnt < $nCount ; $nCnt++){
        echo "<tr><td colspan='4'>&nbsp;</td></tr>";
        $tempRow = $arrSales[$nCnt];
        $totSales += $tempRow["sales"];
        $totAmt += $tempRow["amount"];
        $prevTotSales += $tempRow["prevSales"];
        $prevTotAmt += $tempRow["prevAmount"];
        echo "<tr>";
        echo "<td align='center'><img src=".$tempRow['pic']." width='60px'></td>";
        echo "<td align='center'>{$tempRow["name"]}</td>";
        echo "<td align='center'>";
        echo    "{$tempRow["sales"]}<br/>";
        if($tempRow["prevSales"] <= 0){
            $gap = $tempRow["sales"];
            if($tempRow["prevSales"] == $tempRow["sales"] ){
                echo "<font color='gray'>- &#37;<br/>(- 0)</font>";
            }else{
                echo "<font color='red'>&infin; &#37;<br/>";
                echo " (&uarr; $gap)</font>";
            }
        }else if($tempRow["prevSales"] < $tempRow["sales"] ){
            $gap = $tempRow["sales"] -$tempRow["prevSales"];
            $percent = round($gap / $tempRow["prevSales"] * 10000);
            $percent = $percent /100;
            $gap = abs($gap);
            $gap = round($gap*100);
            $gap /= 100;
            echo "<font color='red'>$percent &#37;<br/>(&uarr; $gap)</font>";
        }else if($tempRow["prevSales"] == $tempRow["sales"] ){
            echo "<font color='gray'>- &#37;<br/>(- 0)</font>";
        }else{
            $gap = $tempRow["prevSales"] -$tempRow["sales"];
            $percent = round($gap / $tempRow["prevSales"] * 10000);
            $percent = $percent /100;
            $gap = abs($gap);
            $gap = round($gap*100);
            $gap /= 100;
            echo "<font color='blue'>$percent &#37;<br/>(&darr; $gap)</font>";
        }
        echo "</td>";
        echo "<td align='center'>";
        echo "$ {$tempRow["amount"]}<br/>";
        if($tempRow["prevAmount"] <= 0){
            $gap = $tempRow["amount"];
            if($tempRow["prevAmount"] == $tempRow["amount"] ){
                echo "<font color='gray'>- &#37;<br/>(- $ 0)</font>";
            }else{
                echo "<font color='red'>&infin; &#37;<br/>";
                echo "(&uarr; $ $gap)</font>";
            }
        }else if($tempRow["prevAmount"] < $tempRow["amount"] ){
            $gap = $tempRow["amount"] - $tempRow["prevSales"];
            $percent = round($gap / $tempRow["prevAmount"] * 10000);
            $percent = $percent /100;
            $gap = abs($gap);
            $gap = round($gap*100);
            $gap /= 100;
            echo "<font color='red'>$percent &#37; (&uarr; $ $gap)</font>";
        }else if($tempRow["prevAmount"] == $tempRow["amount"] ){
            echo "<font color='gray'>- &#37;<br/>(- $ 0)</font>";
        }else{
            $gap = $tempRow["prevSales"] -$tempRow["amount"];
            $percent = round($gap / $tempRow["prevAmount"] * 10000);
            $percent = $percent /100;
            $gap = abs($gap);
            $gap = round($gap*100);
            $gap /= 100;
            echo "<font color='blue'>$percent &#37;<br/>(&darr; $ $gap)</font>";
        }
        echo "</td>";
        echo "</tr>";
        
    }
    /*
    $sales["menuid"] = $row["menuid"];
        $sales["name"] = $row["name"];
        $sales["pic"] = $row["pic"];
        $sales["price"] = $row["price"];
        $sales["sales"] = $row["sales"];
        $sales["amount"] = $row["amount"];
    */
?>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td><B>Total</B></td>
				<td>&nbsp;</td>
				<td align="center"><?php echo "$totSales";?></td>
				<td align="center"><?php echo "$ $totAmt";?></td>
			</tr>
            <tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
            <tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
            <tr>
				<td colspan="4">* Increase/Decrease is comparing value with last 7 days statistics</td>
			</tr>
		</table>
		<br />
	    </p>
		<br />
	
			
	</div>
<?php
    include './storeleftreport.php';
?>
	

<!--content ends -->
	</div>
<!--footer begins -->
	</div>
</div>
<div id="footer">
<p>Copyright &copy; 2014. Design by COMP 231 Group 1</p>
	</div>
<!-- footer ends-->
</body>
</html>
