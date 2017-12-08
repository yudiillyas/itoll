<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Pushfotobarang extends CI_Controller {
       function __construct(){
    parent::__construct();    
    $this->load->model('dauo');
    $this->load->helper('url');
    $this->load->library('session');
  }
	public function index()
	{

$usernameuser=$_POST['usernameuser'];


$response=array();
$res=$this->dauo->pushfotobarang($usernameuser);
foreach ($res as $row) {
 array_push($response,array("id"=>$row['id'],"fotobarang"=>$row['fotobarang']));
}
echo json_encode(array("fotobarang"=>$response));

 }
}
