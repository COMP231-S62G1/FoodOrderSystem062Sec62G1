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
<div id="content">
	<div id="back_all">
<!-- header begins -->
<?php 
    include './storeheader.php';
    
    if(isset($user_input['submit'])){
        $name = $user_input['name'];
        $empnum = $user_input['empnum'];
        if($user_input['conf'] == "manager")
            $isManager = true;
        else
            $isManager = false;
        if(!isset($user_input['empnum'])){
            echo "<script> alert('Employee number is required'); </script>";
        }else if(strlen($user_input['empnum']) <= 0){
            echo "<script> alert('Employee number is required'); </script>";
        }else if(!isset($user_input['name'])){
            echo "<script> alert('User name is required'); </script>";
        }else if(strlen($user_input['name'])<=0){
            echo "<script> alert('User name is required'); </script>";
        }else if(!isset($user_input['pwd1'])){
            echo "<script> alert('Password is required'); </script>";
        }else if(strlen($user_input['pwd1'])<=0){
            echo "<script> alert('Password is required'); </script>";
        }else if(!isset($user_input['pwd2'])){
            echo "<script> alert('Password is required'); </script>";
        }else if(strlen($user_input['pwd2'])<=0){
            echo "<script> alert('Password is required'); </script>";
        }else if($user_input['pwd1'] != $user_input['pwd2']){
            echo "<script> alert('Please fill in same password'); </script>";
            
        }else{
            $pwd = $user_input['pwd1'];
            $table = 'operator';
            if($isManager == true)
                $man = "true";
            else
                $man = "false";
            $query = "INSERT INTO $table (`name`, `empId`, `idrest`, `isManager`, `pwd`) VALUES ('$name', '$empnum', $idRest, $man, MD5('$pwd'))";
            $result = mysql_query($query);
            if (!$result) {
   echo "<script> alert('Create account failed'); </script>";
}else{
header("location: ./manager/storeaccount.php"); // Redirecting To manage account page

}
        }  
    }
?>
        
<!-- header ends -->
<!-- content begins -->
 <div id="main">
 	<div id="right">
		<h3>Welcome To Centennial College Food Order</h3><br />
			<h4>Account Maintance</h4>
            <form action='./storeaccount.php' method='post'>
                <input type="hidden" name="restid" value="<?php echo $idRest; ?>"/>
			<table style="width: 100%">
                <tr>
					<td style="width: 146px; height: 18px">Employee number:</td>
					<td style="height: 18px">
					<input name="empnum" style="width: 207px" type="text" value="<?php echo $empnum; ?>"/></td>
				</tr>
				<tr>
					<td style="width: 146px; height: 18px">User Name:</td>
					<td style="height: 18px">
					<input name="name" style="width: 207px" type="text"  value="<?php echo $name; ?>"/></td>
				</tr>
				<tr>
					<td style="width: 146px; height: 18px">User Password:</td>
					<td style="height: 18px">
					<input name="pwd1" style="width: 207px" type="password" /></td>
				</tr>
				<tr>
					<td style="width: 146px">Password Confirm:</td>
					<td>
					<input name="pwd2" style="width: 207px" type="password" /></td>
				</tr>
				<tr>
					<td style="width: 146px">User Role:</td>
					<td>
<?php
                if(!isset($isManager)){
                    echo '<input id="conf" name="conf" type="radio" value="manager" /> Manage &nbsp;&nbsp;&nbsp;&nbsp;';
                    echo '<input name="conf" type="radio" value="clerk" checked="checked" /> Clerk</td>';
                }else if($isManager == false){
                    echo '<input id="conf" name="conf" type="radio" value="manager" /> Manage &nbsp;&nbsp;&nbsp;&nbsp;';
                    echo '<input name="conf" type="radio" value="clerk" checked="checked" /> Clerk</td>';
                }else{
                    echo '<input id="conf" name="conf" type="radio" value="manager" checked="checked" /> Manage &nbsp;&nbsp;&nbsp;&nbsp;';
                    echo '<input name="conf" type="radio" value="clerk"/> Clerk</td>';
                }
?>
				</tr>
				<tr>
					<td style="width: 146px">&nbsp;</td>
					<td>
                        <input type="submit" name="submit" value="Add new account" />&nbsp;&nbsp;&nbsp;
                        <input type="reset" value="Clear form" />
					</td>
				</tr>
            </table>
            </form>
		<p><br />
	    </p>
			<h4>All Accounts</h4><br />
			<table style="width: 100%">
				<tr>
				<th style="height: 18px">Employee number</th>
                <th style="height: 18px; width: 209px;">User Name</th>
                <th style="height: 18px">User Role</th>
				</tr>
<?php
    include './getAccounts.php';
    
    $nCount = count($arrOperators);

    if($nCount <=0){
        echo "<tr><td colspan='3'>Cannot find account for your restaurant</td></tr>";
    }

    for($nCnt = 0 ; $nCnt < $nCount ; $nCnt++){
        $tempRow = $arrOperators[$nCnt];
        echo "<tr><td colspan='3'>&nbsp;</td></tr>";
        echo "<tr>";
        echo "<td align='center'>";
        echo $tempRow['empId'];
        echo "</td>";
        echo "<td align='center'>";
        echo $tempRow['name'];
        echo "</td>";
        echo "<td align='center'>";
        if($tempRow['isManager'] == false)
            echo "Operator";
        else
            echo "Manager";
        echo "</td>";
        echo "</tr>";
    }
?>
            <tr><td colspan='3'>&nbsp;</td></tr>
            <tr><td colspan='3'>&nbsp;</td></tr>
		</table>
			<p class="date">&nbsp;</p>
			
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
