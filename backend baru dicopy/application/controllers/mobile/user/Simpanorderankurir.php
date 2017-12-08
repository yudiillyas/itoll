<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Simpanorderankurir extends CI_Controller {
function __construct(){
		parent::__construct();		
		$this->load->model('dauo');
		$this->load->helper('url');
		$this->load->library('session');
 		
	}
	public function index()
	{
/*$data = array(
	  			'usernameuser ' =>  $_POST["usernameuser"],
				'namadriver ' =>  $_POST["namadriver"],
				'fotodriver ' =>  $_POST["fotodriver"],
				'fotobarang ' =>  $_POST["fotobarang"],
				'namabarang ' =>  $_POST["namabarang"],
				'alamatjemput ' =>  $_POST["alamatjemput"],
				'alamattujuan ' =>  $_POST["alamattujuan"],
				'latitudejemput ' =>  $_POST["latitudejemput"],
				'longitudejemput ' =>  $_POST["longitudejemput"],
				'latitudetujuan ' =>  $_POST["latitudetujuan"],
				'longitudetujuan' =>  $_POST["longitudetujuan"],
				'chatroom' =>  $_POST["chatroom"],
				'jarak' =>  $_POST["jarak"],
				'harga' =>  $_POST["harga"],
				'nomerhpdriver' =>  $_POST["nomerhpdriver"]
			);
  $aku=$this->dauo->input_data($data,'kurirberjalan');*/
			echo 'sukses';
		}
}