<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Kurirselesai extends CI_Controller {
function __construct(){
		parent::__construct();		
		$this->load->model('dauo');
		$this->load->helper('url');
		$this->load->library('session');
 		
	}
	public function index()
	{

/*$namadriver=$_POST['namadriver'];
$pesan=$_POST['pesan'];
$usernameuser=$_POST['usernameuser'];
$alamatjemput=$_POST['alamatjemput'];
$alamattujuan=$_POST['alamattujuan'];
$jarak=$_POST['jarak'];
$harga=$_POST['harga'];
$namabarang=$_POST['namabarang'];
$this->dauo->updatestatus2($namadriver,'tidak','driver');
$this->dauo->updatekurirberjalan('sudah',$usernameuser,$namadriver,'kurirberjalan');*/

  $kebakaran='AIzaSyDNqVa4baw5TUzYvGQM-jirqEBDQ7KWncM';
  $token='flOcCNppiBY:APA91bEGMr-i508FHna4RnrGw1KxpmvPtg2K1lK1Q90nfxeGpCkYOEvlxzzqUY5QfZoZhqeIR-IjoUiz-bN93XP2nNQGMREMd6MYXhzlxgjThUGQDFJDFvJx8cf-MitTSndejpjFgjDI';
//$token=$this->dauo->ambiltoken($usernameuser);


		
										$reg_token = array($token);		
										$msg = array
										(
											'message' 	=> 'hai',
											'title'		=> 'Admin',
											'subtitle'	=> 'Kurir Telah Selesai',
											'tickerText'	=> 'ada pesen',
											'vibrate'	=> 1,
											'sound'		=> 1,
											'nama'		=> 'boyke',		
											'usernameuser'	=> 'boyke',						
											/*'namadriver'		=> $namadriver,		
											'namabarang'		=> $namabarang,		
											'alamatjemput'		=> $alamatjemput,	
											'alamattujuan'		=> $alamattujuan,	*/
											/*'jarak'		=> $jarak,
											'harga'		=> $harga,		*/				
											'hasilnya'		=> 'kurirselesai',	
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
										curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
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

										echo "sukses";
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