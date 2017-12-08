<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Welcome extends CI_Controller {
 function __construct()
    {
        parent::__construct();      
       $this->load->helper('url');
        $this->load->helper(array('url'));
$this->load->library('session');

    }
	public function index()
	{
/*$this->session->sess_destroy();
*/    $usernamenya=$this->session->userdata('username');
    $passwordnya=$this->session->userdata('password');
    $nama=$this->session->userdata('name');
    if($usernamenya==""){
      $usernamenya="kosong";
    }
    $status=$this->session->userdata('status');
    if($usernamenya!=="kosong"){
      echo $status;
         if($status=="1"){
            $this->load->view('header');
          $this->load->view('Login_page');
          $this->load->view('footer');
        }else if($status=="2"){
            $this->load->view('header2');
          $this->load->view('Login_page2');
          $this->load->view('footer2');
          }else{
            echo "anda bukan admin".$usernamenya;
            redirect('/Welcome', 'refresh');
          }
          
        }else{
		 $this->load->helper('captcha');
       
                                  $vals = array(
                                      'img_path'   => './captcha/',
                                      'img_url'    => base_url().'captcha/',
                                      'img_width'  => '260',
                                      'img_height' => 60,
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
