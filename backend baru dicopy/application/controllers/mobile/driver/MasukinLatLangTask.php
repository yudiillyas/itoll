<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class MasukinLatLangTask extends CI_Controller {
       function __construct(){
    parent::__construct();    
    $this->load->model('dauo');
    $this->load->helper('url');
    $this->load->library('session');
  }
	public function index()
	{
      
 $username = $_POST["username"];

	$datamasukan = array(
            'latitude' => $_POST['latitude'],
            'longitude' => $_POST['longitude'],
             'speed' => $_POST['speed'],
            'token' => $_POST['token']
            );

    $res=$this->dauo->updatemasukinlatlangtask($username,$datamasukan,'driver');
  
    if($res=='sukses'){
        echo $res;
    }else{
      echo "sukses";
    }
     
	
 }
}
