<?php
defined('BASEPATH') OR exit('No direct script access allowed');
$this->load->helper('url');
$this->load->library('session');

    $this->load->helper(array('captcha','url'));
?><!DOCTYPE html>
<html lang="en">
<head>
<link rel="icon"  href="image/logo.png" />
	<meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

  <link href="https://fonts.googleapis.com/css?family=El+Messiri" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
 <link rel="stylesheet"
    href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css?n=1" />
    <script src="http://code.jquery.com/jquery-1.12.4.js?n=1"></script>
    <script src="http://code.jquery.com/ui/1.12.1/jquery-ui.js?n=1"></script>
  <link href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
<link href="http://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js?n=1" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="http://code.jquery.com/ui/1.12.1/jquery-ui.js?n=1"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Vibur" rel="stylesheet">
</head>
<body style="background-image: url('<?php echo base_url();?>image/back.jpg');
   background-repeat: repeat;"> 
   <div class="navbar navbar-default " style="inline-color:blue">
        <div class="navbar-header">
            <a class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="glyphicon glyphicon-th-list"></span>      
                </a>       
        </div>
        <div class="container" >
            <nav class="navbar-collapse collapse " style="line-height:40px; height:40px;">
                <ul class="nav navbar-nav" style="display:inline-block;">
                 <li>
               		  <img src="<?php echo base_url();?>image/logo.png" class="logo img-responsive" style="margin-top:-10px">           		 
                  </li>
                  <li>
                    <h3 style="color:white;font-family: 'El Messiri', sans-serif;"> Goontravel BACKEND SYSTEM </h3>
                  </li>
                </ul>
            </nav>
        </div>
    </div>

<div class="container" style="padding-left:100px;padding-right:100px">
	<form name="form1" method="post" action="<?php echo site_url('Home')?>">
	  <div class="wrapper">
      <h2 class="form-signin-heading" style="font-family: 'Vibur', cursive;text-align:center;background-color:lightblue;color:black;margin-top:100px;">Please login</h2>
      <input type="text" class="form-control" name="username" placeholder="Username" required="" autofocus=""  />
      <input type="password" class="form-control" name="password" placeholder="Password" required="" style="margin-top:30px;"/> 
   <div  class="col-md-2 col-md-offset-5">
    <label style="font-family: 'Vibur', cursive;width:200px;text-align:center;background-color:blue;color:white;margin-top:40px;margin-left:-20px">Enter The Captcha: </label>
       <?php if ($this->session->flashdata('message'))echo $this->session->flashdata('message');?>
        <div style="margin-left:-80px;"><h1><?=$image;?></h1> </div> 
         <input type="text" style="margin-left:-20px;" class="form-control"   name="security_code" placeholder="Captcha" required="" autofocus="" >
        </div>
      <button class="btn btn-lg btn-primary btn-block" type="submit" style="margin-top:200px;font-family: 'Vibur', cursive;">Login</button> 
      </div>
      </form>
      </div> 
    
  
	
<footer class="navbar-fixed-bottom" style="text-align:center;background-color:black;color:red;">
	<p style="color:red;font-family: 'El Messiri', sans-serif;">(c) 2017 Goontravel</p>
</footer>
</div>

</body>
</html>
