<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Saveusernametask extends CI_Controller {
function __construct(){
		parent::__construct();		
		$this->load->model('dauo');
		$this->load->helper('url');
		$this->load->library('session');
 		
	}
	public function index()
	{	if(isset($_POST['username']))
	   {
	
			$aku=$this->dauo->update($_POST["username"],$_POST["token"],'users');
			echo $aku;
	   }else{
			echo "anda tidak berhak!";
		}
		
	}
}