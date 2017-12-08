<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Tampo extends CI_Controller {
function __construct(){
		parent::__construct();		
		$this->load->model('dauo');
		$this->load->helper('url');
		$this->load->library('session');
 		
	}
	public function index()
	{

		//echo $_POST['username'];
if(isset($_POST['id'])){
				$data = array
				(
					'id' 	=> $_POST['id'],
					'username' => $_POST['username'],
					'nama'	=> $_POST['nama']
				);
						$this->load->view('header');
				         $this->load->view('tampo',$data);
				         $this->load->view('footer');			         			      

					}else{
						echo "anda tidak berhak";
					}

		}
}