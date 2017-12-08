<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Pushorderankurir extends CI_Controller {
       function __construct(){
    parent::__construct();    
    $this->load->model('dauo');
    $this->load->helper('url');
    $this->load->library('session');
  }
	public function index()
	{
      if($_POST['username']){
      $usernameuser=$_POST['username'];
      $response=array();
      $res=$this->dauo->pushorderankurir($usernameuser);
      foreach ($res as $row) {
      array_push($response,array("id"=>$row['id'],"usernameuser"=>$row['usernameuser'],"namadriver"=>$row['namadriver'],"fotobarang"=>$row['fotobarang'],
		"alamatjemput"=>$row['alamatjemput'],
		"alamattujuan"=>$row['alamattujuan'],"latitudejemput"=>$row['latitudejemput'],"longitudejemput"=>$row['longitudejemput'],
		"latitudetujuan"=>$row['latitudetujuan'],"longitudetujuan"=>$row['longitudetujuan'],
		"chatroom"=>$row['chatroom'],"jarak"=>$row['jarak'],"harga"=>$row['harga'],"nomerhpdriver"=>$row['nomerhpdriver'],
		"fotodriver"=>$row['fotodriver'],"namabarang"=>$row['namabarang']));
}
echo json_encode(array("orderan"=>$response));
      }else{
        echo "anda tidak berhak!";
       }
  }
}
