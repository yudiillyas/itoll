<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Logingoogle extends CI_Controller {
function __construct(){
		parent::__construct();		
		$this->load->model('dauo');
		$this->load->helper('url');
		$this->load->library('session');
 		
	}
	public function index()
	{	/*if(isset($_POST['username']))
	   {*/
	   			$response=array();
				 $token  = $_POST["token"];
				$email = $_POST["email"];
				$name = $_POST["name"];
				$phone=$_POST["phone"];
				$foto=$_POST["foto"];
				$ada=$this->dauo->checkemail($email,'users');
			if($ada>0){
				$user=$this->dauo->pushprofil($email);
					foreach ($user as $row) 
				{
		      		  array_push($response,array("id"=>$row['id'],"name"=>$row['name'],"ltuser"=>$row['ltuser'],"lguser"=>$row['lguser'],
		   			 "username"=>$row['username'],"nomerhp"=>$row['nomerhp'],"email"=>$row['email'],"foto"=>$row['foto']));
				}
				echo json_encode(array("datauser"=>$response));
			}else{
				$data = array(
					'token ' => $_POST["token"],
		  			'name ' => $_POST["name"],
					'email ' => $_POST["email"],
					'nomerhp ' => $_POST["phone"],
					'foto ' => $_POST["foto"]
				
			);
					$res=$this->dauo->input_data($data,'users');
					echo $res;
			}	
	  /* }else{
			echo "anda tidak berhak!";
		}*/
		
	}
}