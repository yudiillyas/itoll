<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Logout extends CI_Controller {
       function __construct(){
    parent::__construct();    
    $this->load->model('dauo');
    $this->load->helper('url');
    $this->load->library('session');
  }
	public function index()
	{
      
 $username = $_POST["username"];

    $res=$this->dauo->updatestatus($username,'tidak','driver');
  
    if($res=='sukses'){
        echo "oke";
    }else{
      echo "sukses";
    }
     
	
 }
}
