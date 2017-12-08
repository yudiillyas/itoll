<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Cekstatusdriver extends CI_Controller {
       function __construct(){
    parent::__construct();    
    $this->load->model('dauo');
    $this->load->helper('url');
    $this->load->library('session');
  }
	public function index()
	{
      
 $namadriver = $_POST["namadriver"];

    $res=$this->dauo->cektatusdriver($namadriver,'driver');
  
    if($res>0){
       $logoutin= $this->dauo->updatestatus2($namadriver,'tidak','driver');
       echo $logoutin;
    }else{
      echo "aman";
    }
     
	
 }
}
