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
	$this->session->sess_destroy();
	echo "<h6>ANDA TELAH LOGOUT.... AKAN DIALIHKAN KE HALAMAN LOGIN </h6>";
			redirect('/Welcome', 'refresh');
	}
}
