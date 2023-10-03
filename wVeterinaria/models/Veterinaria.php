<?php

require_once 'Conexion.php';

class Veterinaria extends Conexion{
  
  private $access;

  public function __CONSTRUCT(){
    $this->access = parent::getConnection();

  }

  public function addClientes($data = []){
    try{
      $query = $this->access->prepare("CALL spu_add_clientes(?,?,?,?)");
      $query->execute(
        array(
          $data['apellidos'],
          $data['nombres'],
          $data['dni'],
          $data['claveacceso'],
        )
      );

    }catch(Exception $e){
      die($e->getMessage());
  
    }
  
  }

  public function addMascotas($data = []){
    try{
      $query = $this->access->prepare("CALL spu_add_mascotas(?,?,?,?,?,?)");
      $query->execute(
        array(
          $data['idcliente'],
          $data['idraza'],
          $data['nombre'],
          $data['fotografia'],
          $data['color'],
          $data['genero']
        )
      );
  
    }catch(Exception $e){
      die($e->getMessage());
  
    }
  
  }


  public function buscarMascota($dni = 0){
    try{
      $query = $this->access->prepare("CALL spu_buscar_mascota(?)");
      $query->execute(array($dni));
      return $query->fetch(PDO::FETCH_ASSOC);
      
    }catch(Exception $e){
      die($e->getMessage());
    }

  }


  public function buscarCliente($dni = 0){
    try{
      $query = $this->access->prepare("CALL spu_buscar_cliente(?)");
      $query->execute(array($dni));
      return $query->fetch(PDO::FETCH_ASSOC);
      
    }catch(Exception $e){
      die($e->getMessage());
    }

  }

  public function getMascotas(){
    try{
      $query = $this->access->prepare("CALL spu_getMascotas()");
      $query->execute();
      return $query->fetchAll(PDO::FETCH_ASSOC);
      
    }catch(Exception $e){
      die($e->getMessage());
    }

  }


  public function getRazas(){
    try{
      $query = $this->access->prepare("CALL spu_getRazas()");
      $query->execute();
      return $query->fetchAll(PDO::FETCH_ASSOC);
      
    }catch(Exception $e){
      die($e->getMessage());
    }

  }







}





?>