<?php


$clave = "12345";

$claveE = password_hash($clave, PASSWORD_BCRYPT);
echo $claveE;