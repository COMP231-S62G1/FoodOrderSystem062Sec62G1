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
<form id="form1" runat="server">
<div id="content">
	<div id="back_all">
<!-- header begins -->
<div id="header">
  <div id="menu">
		<ul>
			<li><a href="" title="">Home</a></li>
			<li><a href="storeinfo.php"  title="">Store Info</a></li>
			<li><a href="storemenu.php" title="">Menus</a></li>
			<li><a href="storeaccount.php"  title="">Account</a></li>
			<li><a href="storereport.php"  title="">Report</a></li>
			<li><a href="" title="">Logout</a></li>
		</ul>
	</div>
	<div id="logo">
		<h1><a href="http://www.centennialcollege.ca" title="Centennial College Food Order">Centennial College Food Order</a></h1>
		<h2><a href="http://github.com/COMP231-S62G1/FoodOrderSystem062Sec62G1/" id="metamorph">Design by Group 1</a></h2>
	</div>
</div>
<!-- header ends -->
<!-- content begins -->
<?php
    include "orderReport.php";
?>
 <div id="main">
 	<div id="right">
		<h3>Welcome To Centennial College Food Order</h3><br />
			<h4>Order Report</h4>
            <form action='./storeorderreport.php' method='get' id='acceptForm'>
            <input type="hidden" name="restid" value="<?php echo $restid ?>"/>
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

    for($nCnt = 0; $nCnt < $nCount ; $nCnt++){
        $tempRow = $arrSales[$nCnt];
        $prevRow = $arrPrevSales[$nCnt];
        echo "<tr>";
        echo "<td align='center'><img src=".$tempRow['pic']." width='60px'></td>";
        echo "<td align='center'>{$tempRow["name"]}</td>";
        echo "<td align='center'>";
        echo    "{$tempRow["sales"]}<br/>";
        if($prevRow["sales"] <= 0){
            $gap = $tempRow["sales"]-$prevRow["sales"];
            echo "<font color='red'>&infin; &#37;";
            echo " (&uarr; $gap)</font>";
        }else if($prevRow["sales"] > $prevRow["sales"] ){
            $gap = $tempRow["sales"]-$prevRow["sales"];
            $percent = round($gap / $prevRow["sales"] * 10000);
            $percent = $percent /100;
            $gap = abs($gap);
            echo "<font color='red'>$percent &#37; (&uarr; $gap)</font>";
        }else if($prevRow["sales"] == $prevRow["sales"] ){
            echo "<font color='blue'>- &#37; (- 0)</font>";
        }else{
            $gap = $tempRow["sales"]-$prevRow["sales"];
            $percent = round($gap / $prevRow["sales"] * 10000);
            $percent = $percent /100;
            $gap = abs($gap);
            echo "<font color='blue'>$percent &#37; (&darr; $gap)</font>";
        }
        echo "</td>";
        echo "<td align='center'>";
        echo "$ {$tempRow["amount"]}<br/>";
        if($prevRow["amount"] <= 0){
            $gap = $tempRow["amount"]-$prevRow["amount"];
            echo "<font color='red'>&infin; &#37;";
            echo " (&uarr; $ $gap)</font>";
        }else if($prevRow["amount"] > $prevRow["amount"] ){
            $gap = $tempRow["amount"]-$prevRow["amount"];
            $percent = round($gap / $prevRow["amount"] * 10000);
            $percent = $percent /100;
            $gap = abs($gap);
            echo "<font color='red'>$percent &#37; (&uarr; $ $gap)</font>";
        }else if($prevRow["amount"] == $prevRow["amount"] ){
            echo "<font color='blue'>- &#37; (- $ 0)</font>";
        }else{
            $gap = $tempRow["amount"]-$prevRow["amount"];
            $percent = round($gap / $prevRow["amount"] * 10000);
            $percent = $percent /100;
            $gap = abs($gap);
            echo "<font color='blue'>$percent &#37; (&darr; $ $gap)</font>";
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
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<br />
	    </p>
		<br />
	
			
	</div>
	<div id="left">
		<h3>Store Information</h3>
		<br />
		<a href="storeaccount.php" class="auto-style1"><h3>Store account</h3>
		<br />
			<a href="storeinfo.php" class="auto-style1"><h3>store menus</h3></a>
		<br />	
			<a href="storereport.php" class="auto-style1"><h3>store reports</h3>
			<ul>
				<li><ul>
					<li><a href="storeorderreport.php">Order Report</a></li>
					<li><a href="storereport.php">Sales Report</a></li>
				</ul>
			  </li>
			</ul>
			<br />
</a>
		
	</div>
	

<!--content ends -->
	</div>
<!--footer begins -->
	</div>
</div>
<div id="footer">
<p>Copyright &copy; 2014. Design by COMP 231 Group 1</p>
	</div>
<!-- footer ends-->
</form>
</body>
</html>