<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Optam extends CI_Controller {
       function __construct(){
    parent::__construct();    
    $this->load->model('dauo');
    $this->load->helper('url');
    $this->load->library('session');
  }
	public function index()
	{
        $data=$this->dauo->amdriver();
        $datanya['data']=$data;
       
        $this->load->view('header');
         $this->load->view('optam',$datanya);
         $this->load->view('footer');
	
 }
}
