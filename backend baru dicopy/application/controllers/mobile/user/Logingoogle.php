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
				   $a=explode("@", $email);
			    $pic=$a[0];
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
					'ltuser ' => $_POST["lati"],
					'lguser ' => $_POST["longi"],
					'token ' => $_POST["token"],
		  			'name ' => $_POST["name"],
					'email ' => $_POST["email"],
					'nomerhp ' => $_POST["phone"],
					'username ' => $pic,
					'foto ' => $_POST["foto"]
				
			);
					$res=$this->dauo->input_data($data,'users');
					$user=$this->dauo->pushprofil($email);
					foreach ($user as $row) 
				{
		      		  array_push($response,array("id"=>$row['id'],"name"=>$row['name'],"ltuser"=>$row['ltuser'],"lguser"=>$row['lguser'],
		   			 "username"=>$row['username'],"nomerhp"=>$row['nomerhp'],"email"=>$row['email'],"foto"=>$row['foto']));
				}
				echo json_encode(array("datauser"=>$response));
			}	
	  /* }else{
			echo "anda tidak berhak!";
		}*/
		
	}
}