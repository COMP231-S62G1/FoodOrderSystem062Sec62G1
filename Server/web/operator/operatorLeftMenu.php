	<div id="left">
		<h3>Options</h3>
		<p class="auto-style2">
		<input name="listType" type="radio" value="all" id="typeAll"
<?php 
    if($rdoType == "all")
        echo " checked";
?>
               /> Show all orders</p>
        <p class="auto-style2">
		<input name="listType" type="radio" value="active" id="typeActive" 
<?php 
    if($rdoType == "active")
        echo " checked";
?>
               /> Show all active orders</p>
            <p class="auto-style2">
            &nbsp;&nbsp;&nbsp;&nbsp;<input name="new" type="checkbox" id="activeNew" 
<?php 
    if($rdoType == "active"){
        if($chkOption == "all" || $chkOption == "new")
            echo " checked";
    }
?>
                                           /> New orders</p>
            <p class="auto-style2">
            &nbsp;&nbsp;&nbsp;&nbsp;<input name="confirmed" type="checkbox" id="activeConfirmed" 
<?php 
    if($rdoType == "active"){
        if($chkOption == "all" || $chkOption == "confirmed")
            echo " checked";
    }
?>
                                           /> Under processing orders</p>
        <p class="auto-style2">
		<input name="listType" type="radio" value="inactive" id="typeInactive"
<?php 
    if($rdoType == "inactive")
        echo " checked";
?>
               /> Show all inactive orders</p>
            <p class="auto-style2">
            &nbsp;&nbsp;&nbsp;&nbsp;<input name="completed" type="checkbox" id="inactiveCompleted" 
<?php 
    if($rdoType == "inactive"){
        if($chkOption == "all" || $chkOption == "complteded")
            echo " checked";
    }
?>
                                           /> Completed orders</p>
            <p class="auto-style2">
            &nbsp;&nbsp;&nbsp;&nbsp;<input name="rejected" type="checkbox" id="inactiveRejected" 
<?php 
    if($rdoType == "inactive"){
        if($chkOption == "all" || $chkOption == "rejected")
            echo " checked";
    }
?>
                                           /> Rejected orders</p>
		<p class="auto-style3">
		<button name="btnSearch" onclick="reLocate()">
		<div >	Search </div>
		</button>
		&nbsp;
		</p>
		<br />
	</div>

