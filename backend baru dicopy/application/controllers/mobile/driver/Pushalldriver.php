<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Pushalldriver extends CI_Controller {
       function __construct(){
    parent::__construct();    
    $this->load->model('dauo');
    $this->load->helper('url');
    $this->load->library('session');
  }
	public function index()
	{
     
      $response=array();
      $res=$this->dauo->pushdriver();
      foreach ($res as $row) {
        array_push($response,array("id"=>$row['id'],"nama"=>$row['nama'],"nomerhp"=>$row['nomerhp'],"fotosendiri"=>$row['fotosendiri'],
    "latitude"=>$row['latitude'],"longitude"=>$row['longitude'],"username"=>$row['username']));
}
echo json_encode(array("driver"=>$response));
     
  }
}
