<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Aktifkan extends CI_Controller {
function __construct(){
		parent::__construct();		
		$this->load->model('dauo');
		$this->load->helper('url');
		$this->load->library('session');
 		
	}
	public function index()
	{	/*if(isset($_POST['username']))
	   {*/

				 $username  = $_POST["username"];
				$aktif = $_POST["aktif"];
				
			/*	/*$user='boyke';
				$pass='aku';*/
				$ada=$this->dauo->setaktif($username,$aktif,'driver');
				echo $aktif;
			
	  /* }else{
			echo "anda tidak berhak!";
		}*/
		
	}
}