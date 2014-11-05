
<!-- include header -->
<?php include './operatorHeader.php';?>

<!-- include db connector -->
<?php include '../dbConnector.php';?>


<!-- get parameters and set db table -->
<?php
    $user_input = empty($_POST)?$_GET:$_POST;
    $table = "orders";
    $nPage = $user_input['page'];
    $rdoType = $_GET['type'];
    $chkOption = $_GET['check'];
?>

<!-- load data from database server -->
<?php
    //select all orders

    if($rdoType == "all"){
        $result = mysql_query("SELECT * FROM $table ORDER BY orderTime"); //ORDER BY idrest");
    }else if($rdoType == "active"){
        if($chkOption == "all"){
            $result = mysql_query("SELECT * FROM $table WHERE (status=0 OR status=1) ORDER BY orderTime");
        }else if($chkOption == "new"){
            $result = mysql_query("SELECT * FROM $table WHERE status=0 ORDER BY orderTime");
        }else if($chkOption == "confirmed"){
            $result = mysql_query("SELECT * FROM $table WHERE status=1 ORDER BY orderTime");
        }else{
            $result = mysql_query("SELECT * FROM $table WHERE status=100");
        }
    }else if($rdoType == "inactive"){
        if($chkOption == "all"){
            $result = mysql_query("SELECT * FROM $table WHERE (status=2 OR status=3) ORDER BY orderTime");
        }else if($chkOption == "completed"){
            $result = mysql_query("SELECT * FROM $table WHERE status=2 ORDER BY orderTime");
        }else if($chkOption == "rejected"){
            $result = mysql_query("SELECT * FROM $table WHERE status=3 ORDER BY orderTime");
        }else{
            $result = mysql_query("SELECT * FROM $table WHERE status=100");
        }
    }else{
        // default status
        $result = mysql_query("SELECT * FROM $table WHERE (status=0 OR status=1) ORDER BY orderTime");
        $rdoType ="active";
        $chkOption = "all";
    }
    

    //init order data structure
    $order = array(
                    "orderId"=>"",
                    "userId"=>"",
                    "orderTime"=>"",
                    "userName"=>"",
                    "orderStatus"=>""
                );
    //init order list array
    $arrOrder = array();

    // load data to order array
    while ($row = mysql_fetch_array($result)) {
        $order["orderId"] = $row["idorder"];
        $order["userId"] = $row["userid"];
        $order["orderTime"] = $row["orderTime"];
        $order["userName"] = $row["username"];
        $order["orderStatus"] = $row["status"];
        array_push($arrOrder, $order);
    }

?>


<!-- content begins -->
    <div id="main">
<!-- user search filtering option -->
<?php include './operatorRightMenu.php';?>
        
<!-- data view table -->
<?php include './operatorLeftMenu.php';?>
        
    
    <!--div main ends -->
    </div>

    <!--div back-all ends -->
	</div>

<!--div content ends -->
</div>

<!--footer begins -->

<script type="text/javascript">
        function ChangeColor(tableRow, highLight){
            if (highLight){
                tableRow.style.backgroundColor = '#dcfac9';
            }else{
                tableRow.style.backgroundColor = 'white';
            }
        }

        function DoNav(orderId){
            var url = "./orderdetail.php?orderid=";
            var finalUrl = url.concat(orderId);
            window.location = finalUrl;
        }
        
        function addParameters(url){
            var paramUrl = url.concat("?type=");
            var typeAll = document.getElementById("typeAll").checked;
            var typeActive = document.getElementById('typeActive').checked;
            var typeInactive = document.getElementById('typeInactive').checked
            if(typeAll) {
                //show all radio button is checked
                paramUrl = paramUrl.concat("all");
            }else if(typeActive) {
                //show active radio button is checked
                paramUrl = paramUrl.concat("active");
                var newOrder = document.getElementById('activeNew').checked;
                var confirmed = document.getElementById('activeConfirmed').checked;
                if(newOrder && confirmed) {
                    paramUrl = paramUrl.concat("&check=all");
                }else if (newOrder){
                    paramUrl = paramUrl.concat("&check=new");
                }else if (confirmed){
                    paramUrl = paramUrl.concat("&check=confirmed");
                }
            }else{
                //show inactive radio button is checked
                paramUrl = paramUrl.concat("inactive");
                var completed = document.getElementById('inactiveCompleted').checked;
                var rejected = document.getElementById('inactiveRejected').checked;
                if(completed && rejected) {
                    paramUrl = paramUrl.concat("&check=all");
                }else if (completed){
                    paramUrl = paramUrl.concat("&check=completed");
                }else if (rejected){
                    paramUrl = paramUrl.concat("&check=rejected");
                }
            }
            return paramUrl;
        }
    
        function resetChkBox(){
            var typeAll = document.getElementById("typeAll").checked;
            var typeActive = document.getElementById('typeActive').checked;
            var typeInactive = document.getElementById('typeInactive').checked
            if(typeAll) {
                //show all radio button is checked
                document.getElementById('activeNew').checked = false;
                document.getElementById('activeConfirmed').checked = false;
                document.getElementById('inactiveCompleted').checked = false;
                document.getElementById('inactiveRejected').checked = false;
            }else if(typeActive) {
                //show active radio button is checked
                document.getElementById('activeNew').checked = true;
                document.getElementById('activeConfirmed').checked = true;
                document.getElementById('inactiveCompleted').checked = false;
                document.getElementById('inactiveRejected').checked = false;
            }else{
                //show inactive radio button is checked
                document.getElementById('activeNew').checked = false;
                document.getElementById('activeConfirmed').checked = false;
                document.getElementById('inactiveCompleted').checked = true;
                document.getElementById('inactiveRejected').checked = true;
            }
        }
        
        function reLocate(){
            var paramUrl = addParameters(location.protocol + '//' + location.host + location.pathname);
            //window.alert(paramUrl);
            window.location = paramUrl;
        }
        
        //setTimeout("javascript function", milliseconds);
        setTimeout(reLocate, 10000);
        
    </script> 

<?php include './footer.php';?>


