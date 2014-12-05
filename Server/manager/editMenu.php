
<?php include '../dbConnector.php';?>
<?php

    $restid = $user_input['restid'];
    $menuid = $user_input['menuid'];
    $name = $user_input['name'];
    $des = $user_input['des'];
    $price = $user_input['price'];

    $name = stripslashes($name);
    $name = mysql_real_escape_string($name);
    $des = stripslashes($des);
    $des = mysql_real_escape_string($des);
    $price = stripslashes($price);
    $price = mysql_real_escape_string($price);
    
    $url = "http://husion.ca/comp231/picture/";
    $pic = $url.basename($_FILES["fileToUpload"]["name"]);

    if (strlen($restid)<=0) {
        echo "<script>
                alert('Cannot find Restaurant ID. Go back to previous page.');
                window.history.back();
            </script>";
    }else if (strlen($name)<=0) {
        echo "<script>
                alert('Item name is mandatory. Go back to previous page.');
                window.history.back();
            </script>";
    }else if (strlen($des)<=0) {
        echo "<script>
                alert('Description of item is mandatory. Go back to previous page.');
                window.history.back();
            </script>";
    }else if (strlen($price)<=0) {
        echo "<script>
                alert('Price information is mandatory. Go back to previous page.');
                window.history.back();
            </script>";
    }

    if(isset($user_input['btnEdit'])){
        $action = "edit";
    }else if(isset($user_input['btnSave'])){
        $action = "save";
    }else if(isset($user_input['btnDelete'])){
        $action = "delete";
    }


    if($action != "save"){
        if (strlen($menuid)<=0) {
            echo "<script>
                    alert('Cannot find menu item ID. Go back to previous page.');
                    window.history.back();
                </script>";
        }
    }
    

    mysql_query("BEGIN");
    $uploadOk = true;
    $table = "menu";

    if($action == "save"){
        if(strlen($_FILES["fileToUpload"]["name"])<=0){
            echo "<script>
                    alert('No image file. Go back to previous page.');
                    window.history.back();
                </script>";
            $uploadOk = false;
        }else{
            $query = "insert into $table(des, name, pic, price, restid)  ";
            $query .= "values('$des', '$name', '$pic', '$price', '$restid')";
        }
        
        $result = mysql_query($query);
        $menuid = mysql_insert_id();
    }else if($action == "edit"){
        if(strlen($_FILES["fileToUpload"]["name"])<=0){
            $query = "update $table set des='$des', name='$name', price=$price where menuid='$menuid'";
        }else{
            $query = "update $table set des='$des', name='$name', price=$price, pic='$pic' where menuid='$menuid'";
        }
        $result = mysql_query($query);
    }else if($action == "delete"){
        $query = "delete from $table where menuid='$menuid'";
        $result = mysql_query($query);
    }


    if($result == FALSE){
        $uploadOk = false;
    }



    if($uploadOk != false){
        if(strlen($_FILES["fileToUpload"]["name"])>0){
            $target_dir = "../picture/";
            $target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
            $imageFileType = pathinfo($target_file,PATHINFO_EXTENSION);
            // Check if image file is a actual image or fake image
            if(isset($_POST["submit"])) {
                $check = getimagesize($_FILES["itemPic"]["tmp_name"]);
                if($check !== false) {
                    $uploadOk = true;
                } else {
                    $uploadOk = false;
                }
            }
            // Check if file already exists
            if (file_exists($target_file)) {
               unlink($target_file);
            }
            // Allow certain file formats
            if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg") {
                echo "Sorry, only JPG, JPEG & PNG files are allowed.";
                $uploadOk = false;
            }
        }
    }
    
    // Check if $uploadOk is set to 0 by an error
    if ($uploadOk == false) {
        mysql_query("ROLLBACK");
        echo "<script>
                alert('Sorry error occured while uploading. Go back to previous page.');
                window.history.back();
            </script>";
    // if everything is ok, try to upload file
    } else {
        echo "<script>alert('test temp name {$_FILES["fileToUpload"]["tmp_name"]}');</script>";
        echo "<script>alert('test file name {$_FILES["fileToUpload"]["name"]}');</script>";
        echo "<script>alert('test target {$target_file}');</script>";
        if(strlen($_FILES["fileToUpload"]["name"])>0){
            if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
                mysql_query("COMMIT");
                echo "<script>
                alert('Successfully uploaded');
                location.replace('./storemenu.php?restid=$restid&menuid=$menuid'); 
            </script>";
            } else {
                mysql_query("ROLLBACK");
                echo "<script>
                alert('Sorry error occurred while uploading. Go back to previous page.');
                window.history.back();
            </script>";
            }
        }else{
            mysql_query("COMMIT");
            echo "<script>
                alert('Successfully uploaded');
                location.replace('./storemenu.php?restid=$restid&menuid=$menuid'); 
            </script>";
        }
        
            
        
    }
?>       
