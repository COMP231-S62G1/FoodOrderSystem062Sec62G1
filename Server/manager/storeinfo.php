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
.auto-style2 {
	text-align: center;
}
</style>
</head>
<body>
<div id="content">
	<div id="back_all">
<?php 
    include './storeheader.php';
    include './getStoreInfo.php';
?>
<!-- header ends -->
<!-- content begins -->
 <div id="main">
 	<div id="right">
		<h3>Welcome To Centennial College Food Order</h3><br />
			<h4>Please fill in this form to register:</h4>
        <form action="editRestaurant.php" method="POST"   enctype="multipart/form-data">
            <input type="hidden" name="restid" value="<?php echo $idRest; ?>"/>
		<table style="width: 100%">
			<tr>
				<td style="width: 168px">Restaurant Name:</td>
				<td><input name="name" type="text" style="width: 256px" value="<?php echo $restName;?>" /></td>
			</tr>
			<tr>
				<td style="width: 168px">Restaurant Description:</td>
				<td>
				<textarea name="desc" style="width: 313px; height: 123px;"><?php echo $restDesc;?></textarea></td>
			</tr>
			<tr>
				<td style="width: 168px; height: 22px;">Restaurant Logo:</td>
				<td style="height: 22px">
                <img src="<?php echo $restPic;?>" width="120px"><br>
				Change the above to a new file: <input id="fileToUpload" name="fileToUpload" style="width: 300px" type="file" />
                </td>
            </tr>
			<tr>
			
				<td style="width: 168px">&nbsp;</td>
				<td class="auto-style2">				
                    <input type="submit" value="Update" name="submit" />&nbsp;&nbsp;&nbsp;
                    <input type="reset" value="Clear"/>&nbsp;&nbsp;
                </td>
				
			</tr>
			<tr>
				<td style="width: 168px">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="width: 168px">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
        </form>
		<br />
	
			
	</div>
<?php
    include './storeleft.php';
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