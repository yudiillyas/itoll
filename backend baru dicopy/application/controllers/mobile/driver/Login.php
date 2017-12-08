<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Login extends CI_Controller {
function __construct(){
		parent::__construct();		
		$this->load->model('dauo');
		$this->load->helper('url');
		$this->load->library('session');
 		
	}
	public function index()
	{	/*if(isset($_POST['username']))
	   {*/

				 $user  = $_POST["username"];
				$pass = $_POST["password"];
				$password=md5($pass);
				$token=$_POST['token'];
				
			/*	/*$user='boyke';
				$pass='aku';*/
				$ada=$this->dauo->checklogin($user,$password,'driver');
			if($ada>0){
				$this->dauo->updatestatus($user,'login','driver',$token);
					echo "Login succes...";
			}else{
					echo "gagal username/password salah(sudah diapprove?)";
			}	
	  /* }else{
			echo "anda tidak berhak!";
		}*/
		
	}
}