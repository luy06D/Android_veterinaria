<?php

require_once 'Conexion.php';

class Usuario extends Conexion{
  
  private $access;

  public function __CONSTRUCT(){
    $this->access = parent::getConnection();

  }

  public function login ($dni = ""){
    try{
        $query = $this->access->prepare("CALL spu_login(?)");
        $query->execute(array($dni));

        return $query->fetch(PDO::FETCH_ASSOC);

    }
    catch(Exception $err){
        die($err->getMessage());

    }

  }


}





?>