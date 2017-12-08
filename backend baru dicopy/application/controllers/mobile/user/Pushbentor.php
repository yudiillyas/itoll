<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Pushbentor extends CI_Controller {
       function __construct(){
    parent::__construct();    
    $this->load->model('dauo');
    $this->load->helper('url');
    $this->load->library('session');
  }
	public function index()
	{
     
      $response=array();
      $harga=$this->dauo->ambilharga();
      $res=$this->dauo->pushbentors();
      if(empty($res)){
        echo "kosong";
      }else{
         foreach ($res as $row) {
        array_push($response,array("id"=>$row['id'],"nama"=>$row['nama'],"latitude"=>$row['latitude'],"longitude"=>$row['longitude'],
              "username"=>$row['username'],"nomerhp"=>$row['nomerhp'],"noplat"=>$row['noplat'],"email"=>$row['email'],"foto"=>$row['foto'],
              "sebagai"=>$row['sebagai'],"speed"=>$row['speed'],"harga"=>$harga));
}
echo json_encode(array("databentor"=>$response));
      }
     
     
  }
}
