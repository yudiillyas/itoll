<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Home extends CI_Controller {
function __construct(){
		parent::__construct();		
		$this->load->model('dauo');
		$this->load->helper('url');
		$this->load->library('session');
 
	}
	public function index()
	{  if ($this->input->post() && ($this->input->post('security_code') == $this->session->userdata('mycaptcha'))) 
		{
				$username=$_POST['username'];
				$password=$_POST['password'];
				$datanya=$this->dauo->checklogins($username,$password,'login');
				if($datanya>0){
					$this->session->set_userdata('username',$username);
					$this->session->set_userdata('password',$password);
					$name=$this->dauo->getname($username,$password);
					$status=$this->dauo->getstatus($username);
					$this->session->set_userdata('name',$name);
					$this->session->set_userdata('status',$status);
											$datanya = array(
							    'username' => $username,
							    'password' => $password,
							    'name' => $name
							 );
					if($status=="1"){
						$this->load->view('header');
					$this->load->view('Login_page',$datanya);
					$this->load->view('footer');
				}else if($status=="2"){
						$this->load->view('header2');
					$this->load->view('Login_page2',$datanya);
					$this->load->view('footer2');
					}else{
						echo $status;
						//redirect('/Welcome', 'refresh');
					}
					
				}else{
					echo "<h6>ANDA TIDAK TERDAFTAR..... AKAN DIALIHKAN KE HALAMAN LOGIN </h6>";
					redirect('/Welcome', 'refresh');
				}
		}else{

			 echo '<script>alert("Wrong Captcha!");</script>';

                       $this->load->helper('captcha');
       
                  $vals = array(
                      'img_path'   => './captcha/',
                      'img_url'    => base_url().'captcha/',
                      'img_width'  => '200',
                      'img_height' => 30,
                      'border' => 0, 
                      'expiration' => 7200
                  );
       
                  // create captcha image
                  $cap = create_captcha($vals);
       
                  // store image html code in a variable
                  $data['image'] = $cap['image'];
       
                  // store the captcha word in a session
                  $this->session->set_userdata('mycaptcha', $cap['word']);
       
                  $this->load->view('homie', $data);
		}
	}
}
