<!-- div right start -->

    <h3>Order Operation</h3><br />
        <h4>Order Details: order#<?php echo "$orderId" ?></h4>
    <p>&nbsp;</p>

<?php
    echo "<form action='../Interface/DataLogic/AcceptOrder.php' method='post' id='acceptForm'>";
    echo "<input type='hidden' name='orderId' value='$orderId'>";
    echo "</form>";
    echo "<form action='../Interface/DataLogic/RejectOrder.php' method='post' id='rejectForm'>";
    echo "<input type='hidden' name='orderId' value='$orderId'>";
    echo "</form>";
    echo "<form action='./orderoperate.php' method='post' id='returnBack'>";
    echo "<input type='hidden' name='orderId' value='$orderId'>";
    echo "</form>";

    include './getStatus.php';
    $strStatus = getStatus($status);
    echo "<table style='width: 100%'>";
    echo "<tr align='center'><td colspan='5'><H2>$strStatus</H2></td><td> </td></tr>";
?>
        
        <!-- order list table header start -->

        <tr>
            <th style="height: 18px; width: 61px;">Image</th>
            <th style="height: 18px; width: 73px;">Item</th>
            <th style="height: 18px; width: 94px;">Item Code</th>
            <th style="height: 18px; width: 42px;">Unit Price</th>
            <th style="height: 18px; width: 42px;">Quantity</th>
            <th style="height: 18px; width: 42px;">Total</th>
        <!-- remove action column
            <th style="height: 18px; width: 182px;">Action</th>
        -->
        </tr>
        <!-- order detail table header end -->
        <!-- order detail table content start -->
<?php
    $subTotal = 0;
    $nCount = count($arrLines);
    if($nCount > 0)
	{
		for($nCnt = 0; $nCnt < $nCount ; $nCnt++){
			$tempRow = $arrLines[$nCnt];
            $picLink = '';
            if($tempRow['pic'] != ''){
                $picLink = "<img src=".$tempRow['pic']." width='60px'>";
            }
            $totPrice = $tempRow['qty'] * $tempRow['price'];
            $subTotal += $totPrice;
            echo "<tr align='center'>";
			echo "<td> $picLink </td>";
			echo "<td> {$tempRow['name']} </td>";
			echo "<td> {$tempRow['menuid']} </td>";
			echo "<td> {$tempRow['price']} </td>";
            echo "<td> {$tempRow['qty']} </td>";
            echo "<td> $totPrice </td>\n</tr>";
		}
	}
	else{
		echo"<tr><td colspan='6'> No order details</td></tr>";
	}
    echo "<tr><td colspan='6'> <hr> </td></tr>";
    echo "<tr><td colspan='2'> </td><td colspan='2' align='right'><h3>SubTotal</h3></td><td></td><td align='left'><h2>$subTotal</h2></td></tr>";
    $taxAmount = $subTotal * 0.13;
    $taxAmount *= 100;
    $taxAmount = round($taxAmount);
    $taxAmount /= 100;
    echo "<tr><td colspan='2'></td><td colspan='2' align='right'><h3>Tax</h3></td><td></td><td align='left'><h2>$taxAmount</h2></td></tr>";
    $finalAmount = $subTotal + $taxAmount;
    echo "<tr><td colspan='2'></td><td colspan='2' align='right'><h2>Final Total</h2></td><td></td><td align='left'><h2>$finalAmount</h2></td></tr>";
    echo "<tr><td colspan='6'> <hr> </td></tr>";
    echo "<tr><td colspan='3' align='center'>";
    if($status == 0){        
        echo "<button type='submit' form='rejectForm' value='Reject the order' style='font-size:10px; margin:15px; padding: 10px;'>";
        echo "<H2>Reject the order</H2>";
        echo "</button>";
    }
    echo "</td><td colspan='3' align='center'>";
    if($status == 0){        
        echo "<button type='submit' form='acceptForm' value='Confirm the order' style='font-size:10px; margin:15px; padding: 10px;'>";
        echo "<H2>Confirm the order</H2>";
        echo "</button>";
    }
    echo "</td></tr>";
    echo "<tr><td colspan='6' align='center'>";
    echo "<button type='submit' form='returnBack' value='Back to the List' style='font-size:10px; margin:15px; padding: 10px;'>";
    echo "<H2>Back to the List</H2>";
    echo "</button>";
    echo "</td></tr>";

    echo "</table>";

?>

    <!-- order detail table end -->
    <p><br /></p>


<!-- div right end -->


