<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Panggildriver extends CI_Controller {
function __construct(){
		parent::__construct();		
		$this->load->model('dauo');
		$this->load->helper('url');
		$this->load->library('session');
 		
	}
	public function index()
	{

$ambilfinal=$_POST['ambilfinal'];
$tujuanfinal=$_POST['tujuanfinal'];
$latitudejemput=$_POST['latitudejemput'];
$longitudejemput=$_POST['longitudejemput'];
$latitudetujuan=$_POST['latitudetujuan'];
$longitudetujuan=$_POST['longitudetujuan'];
$latitudedriver=$_POST['latitudedriver'];
$longitudedriver=$_POST['longitudedriver'];
$namauser=$_POST['namauser'];
$chatroom=$_POST['chatroom'];
$username=$_POST['username'];
$namadriver=$_POST['namadriver'];
$fotodriver=$_POST['fotodriver'];
  $kebakaran='AIzaSyCoZrzo9XDSh0_X23rEFerJQ20ui-EhFBc';
  $this->dauo->updatestatus2($namadriver,'ada','driver');
$token=$this->dauo->ambiltokendriver($namadriver);


		
										$reg_token = array($token);		
							$message ="Ada yang Manggil ojek";
										
										//Creating a message array 
										$msg = array
										(
											'message' 	=> $message,
											'title'		=> $username,
											'subtitle'	=> 'Ada yang Manggil ojek',
											'tickerText'	=> 'Dipanggil',
											'vibrate'	=> 1,
											'sound'		=> 1,
										
											'ambilfinal' => $ambilfinal,
											'tujuanfinal'	=> $tujuanfinal,
											'latitudejemput'	=> $latitudejemput,
											'longitudejemput'		=> $longitudejemput,
											'latitudetujuan'   => $latitudetujuan,
											'longitudetujuan'	=> $longitudetujuan,
											'latitudedriver'	=> $latitudedriver,
											'longitudedriver'	=> $longitudedriver,
											'namauser'	=> $namauser,
											//'fotodriver'	=> $fotodriver,
											'chatroom'		=> $chatroom,								
											'hasilnya'		=> 'panggilan',	
											'username'	=> $username,
											'namadriver'	=> $namadriver
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

											echo 'suksespanggil';
											//Redirecting back to our form with a request success 
										//	header('Location: index.php?success');
														}
										else
												{
														echo '<script>alert("gagal kirim notif")</script>';
											//Redirecting back to our form with a request failure 
										//	header('Location: index.php?failure');
												}
									         			      

					

		}
}