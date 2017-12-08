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
			    define('UPLOAD_DIR', './image/');
			    $img = $foto;
			    $img = str_replace('data:image/png;base64,', '', $img);
			    $img = str_replace(' ', '+', $img);
			    $data = base64_decode( $img);
			    $file = UPLOAD_DIR . $_POST['username'] . '.png';
			    $success = file_put_contents($file, $data);
			    $datafoto=base_url()."image/".$_POST['username'].".png";
			  
				$data = array(
		  			'nama ' => $_POST["nama"],
					'username ' => $_POST["username"],
					'nomerhp ' => $_POST["nomerhp"],
					'password ' => $password,
					'ktp ' => $_POST["ktp"],
					'email ' => $_POST["email"],
					'alamat ' => $_POST["alamat"],
					'sim ' => $_POST["sim"],
					'noplat ' => $_POST["noplat"],
					'foto ' => $datafoto,
					'fotoktp ' => $_POST["fotoktp"],
					'fotosim ' => $_POST["fotosim"],
					'fotokk ' => $_POST["fotokk"],
					'token ' => $_POST["token"],
					'latitude ' => $_POST["latitude"],
					'sebagai ' => 'bentor',
					'longitude ' => $_POST["longitude"]
				
							);
								$tempass = array(
									'username ' => $_POST["username"],
									'password ' => $_POST['password'],
									'bidang'=> 'bentor'
								
							);
							$aku=$this->dauo->input_data($data,'driver');

									if($aku=='sukses')
									{
								$this->dauo->input_data($tempass,'tempass');
								echo "Registration Success!";
										}else{
											echo $aku;
										}
					  
						}

						 	}
			} else{
							echo "anda tidak berhak!";
							}
		}
	}
