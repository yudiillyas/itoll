<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Register extends CI_Controller {
function __construct(){
		parent::__construct();		
		$this->load->model('dauo');
		$this->load->helper('url');
		$this->load->library('session');
 		
	}
	public function index()
	{	if(isset($_POST['username']))
	   {


	   		$email=$_POST['email'];
	   	$cekemail=$this->dauo->checkemail($email,'driver');
	   	if($cekemail>0){
	   		echo "Email Sudah Terpakai silahkan rubah";
	   	}else{


		   	$cekusername=$this->dauo->cekusername($_POST['username']);
		   	if($cekusername>0){
		   		echo "Username Sudah Terpakai silahkan rubah";
		   	}else
		   		{
	   			$pw=$_POST['password'];
	   			$password=md5($pw);
	   			$foto=$_POST['foto'];
			    // requires php5
			    $a=explode("@", $_POST["email"]);
			    $pic=$a[0];
			    define('UPLOAD_DIR', './users/');
			    $img = $foto;
			    $img = str_replace('data:image/png;base64,', '', $img);
			    $img = str_replace(' ', '+', $img);
			    $data = base64_decode( $img);
			    $file = UPLOAD_DIR . "usr".$pic . '.png';
			    $success = file_put_contents($file, $data);
			    
			    $datafoto=base_url()."users/usr".$pic.".png";
				$data = array(
	  			 'name ' => $_POST["fullname"],
				'token ' => $_POST["token"],
				'ltuser ' => $_POST["latitude"],
				'lguser ' => $_POST["longitude"],
				'username ' => $pic,
				'nomerhp ' => $_POST["nomerhp"],
				'email ' => $_POST["email"],
				'foto ' =>  $datafoto,
				'password ' => $password
				
			);

								$tempass = array(
									'username ' => $_POST["username"],
									'password ' => $_POST['password'],
									'bidang'=> 'user'
								
							);
			$aku=$this->dauo->input_data($data,'users');
			if($aku=='sukses'){
				$this->dauo->input_data($tempass,'tempass');
				echo "Registration Success!";
			}else{
				echo $aku;
			}
	   
			}

		}
	}else{
			echo "anda tidak berhak!";
		}
}
}