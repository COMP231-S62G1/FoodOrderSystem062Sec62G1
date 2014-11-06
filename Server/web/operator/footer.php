<!--footer begins -->
<div id="footer">
<?php
	$curTime = date("Y");
	$compName = "Food Order Centennial";
    $designer = "COMP 231 Group 1";
?>
<?php
	if($curTime == 2014){
		echo "<p>Copyright &copy; $curTime, $compName. Design by $designer.</p>";
	}else{
		echo "<p>Copyright &copy; 2014 - $curTime, $compName. Design by $designer.</p>";
	}
?>
</div>
<!-- footer ends-->

</body>
</html>
