<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Pushprofil extends CI_Controller {
       function __construct(){
    parent::__construct();    
    $this->load->model('dauo');
    $this->load->helper('url');
    $this->load->library('session');
  }
	public function index()
	{
      if($_POST['username']){
      $username=$_POST['username'];

      $response=array();
      $res=$this->dauo->pushprofil($username);
      foreach ($res as $row) {
         array_push($response,array("id"=>$row['id'],"name"=>$row['name'],"foto"=>$row['foto']));
      }
      echo json_encode(array("profil"=>$response));
      }else{
        echo "anda tidak berhak!";
       }
  }
}
