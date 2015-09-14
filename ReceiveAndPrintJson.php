<?php
    
    if (isset($_POST["json"])) {
        // Decode json received
        $data = json_decode($_POST["json"]);
        // Response to client
        echo json_encode($data);
    }
    else {
        echo "Hello World";
    }
    
?>
