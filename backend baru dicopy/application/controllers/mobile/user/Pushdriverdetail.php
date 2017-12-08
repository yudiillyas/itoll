<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Pushdriverdetail extends CI_Controller {
       function __construct(){
    parent::__construct();    
    $this->load->model('dauo');
    $this->load->helper('url');
    $this->load->library('session');
  }
	public function index()
	{
      if($_POST['namadriver']){
      $namadriver=$_POST['namadriver'];
      $response=array();
      $res=$this->dauo->pushdriverdetail($namadriver);
      foreach ($res as $row) {
       array_push($response,array("id"=>$row['id'],"latitude"=>$row['latitude'],"longitude"=>$row['longitude']));
        }
        echo json_encode(array("driverdetail"=>$response));
      }else{
        echo "anda tidak berhak!";
       }
  }
}
