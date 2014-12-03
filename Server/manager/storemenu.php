
<?php
    $table = "menu";
    $restid = $idRest;
    $menuid = $user_input['menuid'];

    $query = "SELECT * FROM $table WHERE restid=$restid";

    $result = mysql_query($query); 

    //init order data structure
    $aMenu = array(
            "menuid"=>"",
            "restid"=>"",
            "name"=>"",
            "pic"=>"",
            "des"=>"",
            "price"=>""
        );
    //init order list array
    $arrMenu = array();

    // load data to order array
    while ($row = mysql_fetch_array($result)) {
        $aMenu["menuid"] = $row["menuid"];
        $aMenu["restid"] = $row["restid"];
        $aMenu["name"] = $row["name"];
        $aMenu["pic"] = $row["pic"];
        $aMenu["des"] = $row["des"];
        $aMenu["price"] = $row["price"];
        array_push($arrMenu, $aMenu);
    }

    $nCount = count($arrMenu);


    if($menuid != null && $menuid != 0){
        $query = $query . " AND menuid=$menuid";
        $result = mysql_query($query); 
        if ($row = mysql_fetch_array($result)) {
            $aMenu["menuid"] = $row["menuid"];
            $aMenu["restid"] = $row["restid"];
            $aMenu["name"] = $row["name"];
            $aMenu["pic"] = $row["pic"];
            $aMenu["des"] = $row["des"];
            $aMenu["price"] = $row["price"];
        }
    }

    
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
    
<div id="contents">
	<div id="back_all">
<?php 
    include './storeheader.php';
?>
<!-- header ends -->
<!-- content begins -->
 <div id="main">
 	<div id="rights">
		<h3>Welcome To Centennial College Food Order</h3><br />
		<p id="result"></p>
			<h4>Maintain Menu</h4>
			<form action='./editMenu.php' method='post' id='editForm'  enctype="multipart/form-data">
<?php
    if($menuid != null && $menuid != 0){
        echo "<input type='hidden' name='menuid' value={$aMenu['menuid']} />";
    }
?>
                <input  name="restid" type="hidden" 
<?php
    if($menuid != null && $menuid != 0){
        echo " value={$aMenu['restid']}";
    }else{
        echo " value=$restid";
    }
?>
                           />
			<table style="width: 100%">
				<tr>
					<td style="width: 90px; height: 18px">Item Name:</td>
					<td style="height: 18px">
					<input name="name" style="width: 207px" type="text"
<?php
    if($menuid != null && $menuid != 0){
        echo " value='{$aMenu['name']}'";
    }
?>                           
                           /></td>
				</tr>
				<tr>
					<td style="width: 90px; height: 18px">Price:</td>
					<td style="height: 18px">
					<input name="price" style="width: 207px" type="text" 
<?php
    if($menuid != null && $menuid != 0){
        echo " value={$aMenu['price']}";
    }
?>
                           /></td>
				</tr>
				<tr>
					<td style="width: 90px">Picture Path:</td>
					<td><input id="fileToUpload" name="fileToUpload" style="width: 300px" type="file" /></td>
				</tr>
				<tr>
					<td style="width: 90px">Description:</td>
					<td>
					<textarea name="des" style="width: 220px; height: 63px">
<?php
    if($menuid != null && $menuid != 0){
        echo "{$aMenu['des']}";
    }
?>
                        </textarea></td>
				</tr>
				<tr>
				</tr>
				<tr>
					<td style="width: 90px">&nbsp;</td>
					<td>
                        <button type='submit' name='btnEdit' value='Edit' style='font-size:10px; margin:15px; padding: 10px;'>Edit</button>
                        <button type='submit' name='btnSave' value='Save as New' style='font-size:10px; margin:15px; padding: 10px;'>Save as New</button>
                        <button type='submit' name='btnDelete' value='Delete' style='font-size:10px; margin:15px; padding: 10px;'>Delete</button>
					<td>
					</td>
				</tr>
		</table>
		</form>
 

		<p><br />
	    </p>
			
			<p class="date">&nbsp;</p>
			
		<br />
	
			
	</div>
	<div id="lefts">
	<!-- <form>
	Enter Item Name: <input name="itemNameS" style="width: 207px" type="text" />
	<button name="btnSearch">Search</button></br></br>
	</form/> -->
        <H2>Menu items</H2>
		<table style="width: 50%">
            <tr>
            <th style="height: 18px">Name</th>
            <th style="height: 18px">Photo</th>
            <th style="height: 18px">Price</th>
            </tr>
<?php
    for($nCnt = 0 ; $nCnt < $nCount ; $nCnt++){
        $tempRow = $arrMenu[$nCnt];
        echo "<tr>";
        echo "<td align='center'>";
        echo "<a href='./storemenu.php?restid=$restid&menuid={$tempRow['menuid']}'>".$tempRow['name']."</a>";
        echo "</td>";
        echo "<td align='center'>";
        echo "<img src='{$tempRow['pic']}' height='50px'>";
        echo "</td>";
        echo "<td align='center'>";
        echo "$ ".$tempRow['price']."<br>";
        echo $temprow['menuid'];
        echo "</td>";
        echo "</tr>";
    }
?>
		</table>
	
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
