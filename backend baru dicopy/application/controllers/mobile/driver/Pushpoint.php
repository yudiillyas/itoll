<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Pushpoint extends CI_Controller {
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
				   $response=array();
      $res=$this->dauo->pushbentor($username);
      if(empty($res)){
      	echo "gagal ambil data";
      }else{
      	 foreach ($res as $row) {
        array_push($response,array("id"=>$row['id'],"nama"=>$row['nama'],"nomerhp"=>$row['nomerhp'],"ktp"=>$row['ktp'],
	    "email"=>$row['email'],"alamat"=>$row['alamat'],"sim"=>$row['sim'],"noplat"=>$row['noplat'],"foto"=>$row['foto'],
	    "point"=>$row['point'],"latitude"=>$row['latitude'],"longitude"=>$row['longitude']));
			}
			echo json_encode(array("databentor"=>$response));
		
      }
     
	}
}
}