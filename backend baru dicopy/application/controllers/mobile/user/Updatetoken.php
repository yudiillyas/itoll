<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Updatetoken extends CI_Controller {
function __construct(){
		parent::__construct();		
		$this->load->model('dauo');
		$this->load->helper('url');
		$this->load->library('session');
 		
	}
	public function index()
	{	/*if(isset($_POST['username']))
	   {*/

				 $email  = $_POST["email"];
				$token = $_POST["token"];
				$res=$this->dauo->updatetoken($email,$token,'users');
			if($res==="sukses"){
					echo $res;
			}else{
					echo $res;
			}	
	  /* }else{
			echo "anda tidak berhak!";
		}*/
		
	}
}