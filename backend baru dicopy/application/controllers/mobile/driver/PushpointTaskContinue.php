<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class PushpointTaskContinue extends CI_Controller {
function __construct(){
		parent::__construct();		
		$this->load->model('dauo');
		$this->load->helper('url');
		$this->load->library('session');
 		
	}
	public function index()
	{	if(isset($_POST['username']))
	   {
				$username=$_POST['username'];
				$ada=$this->dauo->getpoint($username);
				echo $ada;
		
	}
}
}