
<?php include '../dbConnector.php';?>
<?php
    $restid = $user_input['restid'];
    $restname = $user_input['name'];
    $desc = $user_input['desc'];
    if(!isset($user_input['submit'])){
        echo "<script>
                alert('Error. Go back to previous page.');
                window.history.back();
            </script>";
    }
    else{
        $url = "http://husion.ca/comp231/picture/";
        $pic = $url.basename($_FILES["fileToUpload"]["name"]);
        
        if (strlen($restid)<=0) {
            echo "<script>
                    alert('Cannot find Restaurant ID. Go back to previous page.');
                    window.history.back();
                </script>";
        }else if (strlen($restname)<=0) {
            echo "<script>
                    alert('Item name is mandatory. Go back to previous page.');
                    window.history.back();
                </script>";
        }else if (strlen($desc)<=0) {
            echo "<script>
                    alert('Description of item is mandatory. Go back to previous page.');
                    window.history.back();
                </script>";
        }
        
        mysql_query("BEGIN");
        $uploadOk = true;
        $table = "rest";
        
        if(strlen($_FILES["fileToUpload"]["name"])<=0){
            $query = "update $table set name='$restname', des='$desc' where idrest='$restid'";
        }else{
            $query = "update $table set name='$restname', des='$desc' pic='$pic' where idrest='$restid'";
        }
        $result = mysql_query($query);
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
                    alert('Sorry please check your uploading file is in right picture format. Go back to previous page.');
                    window.history.back();
                </script>";
        // if everything is ok, try to upload file
        } else {
            if(strlen($_FILES["fileToUpload"]["name"])>0){
                if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
                    mysql_query("COMMIT");
                    echo "<script>
                    alert('Successfully uploaded');
                    location.replace('./storeinfo.php'); 
                </script>";
                } else {
                    mysql_query("ROLLBACK");
                    echo "<script>
                    alert('Sorry error occured while uploading. Go back to previous page.');
                    window.history.back();
                </script>";
                }
            }else{
                mysql_query("COMMIT");
                echo "<script>
                    alert('Successfully updated');
                    location.replace('./storeinfo.php'); 
                </script>";
            }
        }
    }
    
        

?>       