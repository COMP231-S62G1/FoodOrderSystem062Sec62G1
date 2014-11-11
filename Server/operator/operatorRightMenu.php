<!-- div right start -->
<div id="right">
    <h3>Order Operation</h3><br />
        <h4>Order Lists</h4>
    <p>&nbsp;</p>

    <!-- Order list table start -->
    <table style="width: 100%">
        <!-- order list table header start -->
        <tr>
            <th style="height: 18px; width: 61px;">Order ID</th>
            <th style="height: 18px; width: 73px;">Order Time&nbsp;</th>
            <th style="height: 18px; width: 94px;">Customer Name</th>
            <th style="height: 18px; width: 42px;">Status</th>
        <!-- remove action column
            <th style="height: 18px; width: 182px;">Action</th>
        -->
        </tr>
        <!-- order list table header end -->
        <!-- order list table content start -->
<?php
	$nCount = count($arrOrder);

    include './getStatus.php';
    

	if($nCount > 0)
	{
		for($nCnt = 0; $nCnt < $nCount ; $nCnt++){
			$tempRow = $arrOrder[$nCnt];
            $status = getStatus($tempRow['orderStatus']);
            $timestamp = date('H:i', strtotime($tempRow['orderTime']));
            // create table row
            echo "<form id='form{$tempRow['orderId']}' runat='server'>";
            
            echo "<tr align='center' onmouseover='ChangeColor(this, true);'\n
                    onmouseout='ChangeColor(this, false);'\n
                    onclick='DoNav(\"".$tempRow['orderId']."\");'>";
            // fill data in a row
			echo "<td style='width: 61px'> {$tempRow['orderId']} </td>";
			echo "<td style='width: 94px'> $timestamp </td>";
			echo "<td style='width: 73px'> {$tempRow['userName']} </td>";
			echo "<td style='width: 42px'> $status </td>";
            /*//remove action column
            echo "<td class='auto-style3' style='width: 182px'>";
            echo "  <input name='btnOK' type='button' value='Accepte' style='width: 54px' class='auto-style4' />&nbsp;";
            echo "  <input name='btnNo' type='button' value='Reject' />";
            echo "  <input name='btnNote' type='button' value='Notification' />";
            echo "</td>";
            */
            echo "</tr>\n</form>";
            //echo "<td> Price </td>";
            //echo "<td> Tax </td>";
            //echo "<td> P+T </td>\n</tr>";
		}
	}
	else{
		echo"<td colspan='5'> There is no unprocessed order</td>";
	}
?>
        <!-- order list table content end -->
    </table>
    
    <!-- order list table end -->
    <p><br /></p>
    
<?php 
    /*
    <h4>Order Details</h4><br />
    <!-- Order detail table start -->
    <table style="width: 100%">
        <tr>
        <th style="height: 18px">Line ID</th>
        <th style="height: 18px">Item ID</th>
        <th style="height: 18px">Item Name</th>
        <th style="height: 18px">Item Quantity</th>
        </tr>
        <tr>
            <td style="height: 18px"></td>
            <td style="height: 18px"></td>
            <td style="height: 18px"></td>
        </tr>
    </table>
    <!-- orer detail table end -->
    <p>&nbsp;</p>
    <br />
    */
?>

<!-- div right end -->
</div>