
 
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
        
<form action="storeaccount.php" method="POST" name="storeaccount">  
    <input type="hidden" name="restid" value="<?php echo $restid; ?>">
</form>
<form action="storeinfo.php" method="POST" name="storeinfo">  
    <input type="hidden" name="restid" value="<?php echo $restid; ?>">
</form>
<form action="storemenu.php" method="POST" name="storemenu">  
    <input type="hidden" name="restid" value="<?php echo $restid; ?>">
</form>
<form action="storeorderreport.php" method="POST" name="storeorderreport">  
    <input type="hidden" name="restid" value="<?php echo $restid ?>">
</form>
<form action="storesalesreport.php" method="POST" name="storesalesreport">  
    <input type="hidden" name="restid" value="<?php echo $restid ?>">
</form>
<form action="storeinfo.php" method="POST" name="home">  
    <input type="hidden" name="restid" value="<?php echo $restid ?>">
</form>
<form action="../default.php" method="POST" name="logout">
</form>        

<!-- header begins -->
<div id="header">
  <div id="menu">
		<ul>
			<li><a href="#" title="" onclick="document.home.submit();">Home</a></li>
            <li><a href="#" title="" onclick="document.storeinfo.submit();">Store Information</a></li>
			<li><a href="#" title="" onclick="document.storemenu.submit();">Menus</a></li>
			<li><a href="#" title="" onclick="document.storeaccount.submit();">Account</a></li>
			<li><a href="#" title="" onclick="document.storereport.submit();">Report</a></li>
			<li><a href="#" title="" onclick="document.logout.submit();">Logout</a></li>
		</ul>
	</div>
	<div id="logo">
		<h1><a href="http://www.centennialcollege.ca" title="Centennial College Food Order">Centennial College Food Order</a></h1>
		<h2><a href="http://github.com/COMP231-S62G1/FoodOrderSystem062Sec62G1/" id="metamorph">Design by Group 1</a></h2>
	</div>
</div>
<!-- header ends -->
<!-- content begins -->
 <div id="main">
 	<div id="right">
		 <h3>Store Reports</h3><br>

			<p>Please select a link below to view a report:</p>
 
			&nbsp;&nbsp;&nbsp;<li>&nbsp;&nbsp;<a href="#" class="auto-style1" onclick="document.storeorderreport.submit();">Order Report</a></li><br>
            &nbsp;&nbsp;&nbsp;<li>&nbsp;&nbsp;<a href="#" class="auto-style1" onclick="document.storesalesreport.submit();">Sales Report</a></li> 
	
			
	</div>
	<div id="left">
		<a href="#" class="auto-style1" onclick="document.storeinfo.submit();"><h3>Store Information</h3></a>
		<br />
        <a href="#" class="auto-style1" onclick="document.storeaccount.submit();"><h3>Store account</h3></a>
        <br />
        <a href="#" class="auto-style1" onclick="document.storemenu.submit();"><h3>store menus</h3></a>
		<br />	
        <h3>store reports</h3>
        <ul>
            <li><ul>
                <li><a href="#" class="auto-style1" onclick="document.storeorderreport.submit();">Order Report</a></li>
                <li><a href="#" class="auto-style1" onclick="document.storesalesreport.submit();">Sales Report</a></li>
            </ul>
          </li>
        </ul>
        <br />
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

</body>
</html>
