<?php
require_once '../models/Usuario.php';

if(isset($_GET['op'])){
    $user = new Usuario();

    if($_GET['op'] == 'login'){

        $access = [
            "sesion" => false,
            "idcliente" => 0,
            "mensaje" => ""
        ];

        $data = $user->login($_GET['dni']);        
        if($data){
            $clave = $_GET['claveacceso'];
            if(password_verify($clave, $data['claveacceso'])){
                $access["idcliente"] = $data["idcliente"];
                $access["sesion"] = true;            
            }else{
                $access["mensaje"] = "La contraseña es incorrecta";
            
            }
        }else{
            $access["mensaje"] = "El dni es incorrecto";
        }

        echo json_encode($access);
    }
}
?>
