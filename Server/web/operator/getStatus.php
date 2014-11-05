<?php
    function getStatus($ordStat){
        $retString = "Done";
        
        switch($ordStat){
            case 0:
                $retString = "New Order";
                break;
            case 1:
                $retString = "Confirmed";
                break;
            case 2:
                $retString = "Done";
                break;
            case 3:
            default :
                $retString = "Rejected";
                break;
        }
        return $retString;
    }
?>