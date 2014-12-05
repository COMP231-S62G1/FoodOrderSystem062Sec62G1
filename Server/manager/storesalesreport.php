<?php
include './session.php';
?>
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
include "salesReport.php";
?>
<div id="content">
	<div id="back_all">
        
<?php 
    include './storeheader.php';
?>
<!-- header ends -->
<!-- content begins -->
 <div id="main">
 	<div id="right">
		<h3>Welcome To Centennial College Food Order</h3><br />
			<h4>Sales Report</h4>
			<p>&nbsp;</p>
		<p>
		<table style="width: 100%">
			<tr>
				<th style="height: 18px">Date</th>
                <th style="height: 18px">Number of Order </th>
                <th style="height: 18px">Sales Amount</th>

			</tr>
<?php
        for($nCnt = 0; $nCnt < $nCount ; $nCnt++){
            $tempRow = $arrSales[$nCnt];
			echo "<tr>";
            echo "<td align='center'>";
            echo $tempRow['date'];
            echo "</td>";
            echo "<td align='center'>";
            echo $tempRow['sales'];
            echo "</td>";
            echo "<td align='center'>";
            echo "$ ".$tempRow['amount'];
            echo "</td>";
			echo "</tr>";
        }
?>
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
