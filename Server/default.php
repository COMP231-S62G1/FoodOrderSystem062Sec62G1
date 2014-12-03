<?php
include 'login.php'; // Includes Login Script
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Centennial College Food Order</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="styles.css" rel="stylesheet" type="text/css" media="screen" />
<style type="text/css">
.auto-style1 {
	text-align: right;
}
</style>
</head>
<body>

<div id="content">
	<div id="back_all">
<!-- header begins -->
<div id="header">
  <div id="menu">
		<ul>
			<li><a href="./default.php">Home</a></li>
            <li><a href="http://www.centennialcollege.ca" title="Centennial College">Centennial College</a></li>
			<li><a href="http://www.timhortons.com">Tim Hortons</a></li>
            <li><a href="http://www.subway.ca/">SUBWAY</a></li>
            <li><a href="http://www.pizzapizza.ca/">Pizza Pizza</a></li>
		<!--	<li><a href="" title="">Contact</a></li> -->
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
		<h3>Welcome To Centennial College Food Order</h3><br />
			<h4>From the COMP231 Group 
			One </h4>
			<p>Centennial College Campus Special offers a unique way for college students to save money on ordering
			food. You see, we've partnered with vendors that are located near over 500 
			colleges nationwide which provide their services at a reduced cost to those who need it most: the often cash-strapped student. We then compiled these savings into a coupon book, mobile app, and website that serves millions of college students each year. The best part is it's completely free for students and contains vendors they use most. Amazingly, Campus Special provides these students an average of $2,500 in savings per year.</p>
		<p><br />
	    </p>
			<h4><a href=#>The cash gift card</a></h4><br />
			<p>The Centennial College provides the cash gift card for college students 
			to purchase food by ordering online. We aim to drive business through our clients' doors while helping college students save money on everyday purchases. </p>
			<p class="date">&nbsp;</p>
			
			<h4><a href=#>Mobile App</a></h4><br />
		   In 2014, The Campus Special launched a FREE mobile application for Android phones. Students can 
		get the hottest discounts, deals and coupons for places around their college, redeem 
		gift cards, and even place online orders. The Campus Special app has quickly become one of the most popular apps for 18-24 year old students with top rankings in the Android Market.
		<br />
	
			
	</div>
	<div id="left">
		<h3>Vendor Login</h3>
		<p>
        <form action="" method="POST">
		<table style="width:90%" >
		<tr>
		<td style="height: 37px; width: 193px;" >User Name&nbsp; :</td>
		<td colspan="2" style="height: 37px; width: 120px;"> 
			<input name="username" id="username" size="20" tabindex="1" type="text" style="height: 25px"/></td>
		
		</tr>
		<tr>
		<td style="height: 37px; width: 193px;" >Password&nbsp; :</td>
		<td colspan="2" style="height: 37px; width: 140px;"> 
			<input name="password" id="password" size="20" tabindex="2"  type="password" style="height: 25px" /></td>
		
		</tr>
		<tr>
			<td colspan="4" class="auto-style1">
            <input name="submit" type="submit" value=" Login ">
			</td>
		</tr>
        <tr><td colspan="4"><font color='red'><?php echo $error; ?></font></td></tr>
		</table>
        </form>
		</p>
		<br />
			<h3>Restaurants List</h3>
			<ul>
				<li><ul>
					<li><a href="http://www.timhortons.com">Tim Hortons</a></li>
					<li><a href="http://www.subway.ca/">SUBWAY</a></li>
					<li><a href="http://www.pizzapizza.ca/">Pizza Pizza</a></li>
				</ul>
			  </li>
			</ul>
			<br />
			<h3>Current New</h3>
			<ul>
				<li><ul>
                                                <li><a href="http://54.213.167.5/COMP/Server/web/operator/orderoperate.html">Food Order App release</a></li>
					</ul>
		       </li>
		  </ul>
	</div>
	

<!--content ends -->
	</div>
<!--footer begins -->
	</div>
</div>
<div id="footer">
<p>Copyright &copy; 2014. Designed by COMP 231 Group 1</p>
	</div>
<!-- footer ends-->
</body>
</html>
