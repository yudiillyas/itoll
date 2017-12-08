<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Kurirterimapanggilan extends CI_Controller {
function __construct(){
		parent::__construct();		
		$this->load->model('dauo');
		$this->load->helper('url');
		$this->load->library('session');
 		
	}
	public function index()
	{

$namadriver=$_POST['namadriver'];
$usernameuser=$_POST['usernameuser'];
$chatroom=$_POST['chatroom'];
$this->dauo->updatestatus2($namadriver,'terima','driver');
$usernameuser=$_POST['usernameuser'];
  $kebakaran='AIzaSyCoZrzo9XDSh0_X23rEFerJQ20ui-EhFBc';
$token=$this->dauo->ambiltoken($usernameuser);


		
										$reg_token = array($token);		
											$msg = array
										(
											'message' 	=> 'Panggilan Diterima Driver',
											'title'		=> $namadriver,
											'subtitle'	=> 'panggilan diterima',
											'tickerText'	=> 'panggilan diterima',
											'vibrate'	=> 1,
											'sound'		=> 1,									
											'nama'		=>$namadriver,		
											'usernameuser'	=> $usernameuser,												
											'hasilnya'		=> 'panggilanditerimakurir',	
											'largeIcon'	=> 'large_icon',
											'smallIcon'	=> 'small_icon'
										);
										
										//Creating a new array fileds and adding the msg array and registration token array here 
										$fields = array
										(
											'registration_ids' 	=> $reg_token,
											'data'			=> $msg
										);
										
										//Adding the api key in one more array header 
										$headers = array
										(
											'Authorization: key=' . $kebakaran,
											'Content-Type: application/json'
										); 
										
										//Using curl to perform http request 
										$ch = curl_init();
										curl_setopt( $ch,CURLOPT_URL, 'https://android.googleapis.com/gcm/send' );
										curl_setopt( $ch,CURLOPT_POST, true );
										curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
										curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
										curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
										curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
										
										//Getting the result 
										$result = curl_exec($ch );
										curl_close( $ch );
										
										//Decoding json from result 
										$res = json_decode($result);

										
										//Getting value from success 
										$flag = $res->success;
										
										//if success is 1 means message is sent 
										if($flag == 1){

												echo 'sukses';
								
												}
										else
												{
													echo $namadriver;	
											//Redirecting back to our form with a request failure 
										//	header('Location: index.php?failure');
												}
									         		
									         			      

					

		}
}