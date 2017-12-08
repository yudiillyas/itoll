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
	{	if(isset($_POST['username']))
	   {

				 $user  = $_POST["username"];
				$pass = $_POST["password"];
				$response=array();
				   $a=explode("@", $user);
			    $pic=$a[0];
				$password=md5($pass);
				$token = $_POST["token"];
				$ada=$this->dauo->checklogins($pic,$password,'users');
			if($ada>0){
					$this->dauo->updatestatus($user,'login','users',$token);
					$harga=$this->dauo->ambilharga();
					$datauser=$this->dauo->pushprofil($user);
					if(empty($datauser)){
						echo "gagal anda blum terdaftar atau username dan password salah";
					}else
					{
						foreach ($datauser as $row) {
				        array_push($response,array("id"=>$row['id'],"nama"=>$row['name'],"latitude"=>$row['ltuser'],"longitude"=>$row['lguser'],
					    "username"=>$row['username'],"nomerhp"=>$row['nomerhp'],"email"=>$row['email'],"foto"=>$row['foto']));
							}
							echo json_encode(array("datauser"=>$response));
					}
			}else{
					echo "gagal anda blum terdaftar atau username dan password salah";
			}	

		}else{
			echo "anda tidak berhak!";
		}
	   
		
	}
}