<?php
defined('BASEPATH') OR exit('No direct script access allowed');
$this->load->helper('url');
  $this->load->library('session');
 $name=$this->session->userdata('name');
  $username=$this->session->userdata('username');

   $status=$this->session->userdata('status');

if($status!=="1"){
  echo "maaf anda bukan Admin, anda tidak berhak mengakses halaman ini";
  redirect('/Welcome', 'refresh');
}
?><!DOCTYPE html>
<html lang="en">
<title>Goontravel</title>
<head  style="font-family: 'Indie Flower', cursive;">
  <meta charset="utf-8">
<link href="https://fonts.googleapis.com/css?family=Indie+Flower" rel="stylesheet">
    <link rel="stylesheet" id="ibooster-style-css" href="./style.css" type="text/css" media="all">
  <link href="https://fonts.googleapis.com/css?family=El+Messiri" rel="stylesheet">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
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
 
   <div class="navbar navbar-inverse  navbar-fixed-top">
        <div class="navbar-header">
            <a class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="glyphicon glyphicon-th-list"></span>
            </a>
        </div>
        <div class="container" >
            

            <nav class="navbar-collapse collapse " style="line-height:40px; height:40px;">
                <ul class="nav navbar-nav" style="display:inline-block;">
                  <li style="margin-top:-16px;">
                 <a href="<?php echo base_url();?>">
                    <img src="<?php echo base_url();?>image/logo.png" class="img-rounded" class="logo img-responsive" width="100px" height="100px">           
               </a>
               </li>
               <li class="active" style="margin-top:20px"><a href="<?php echo base_url();?>">Home</a></li>
              
              

                <li style="margin-top:20px">
                 <a href="<?php echo site_url('optam');?>">Driver Manajemen</a>
              </li>
               
               
                </ul>
                  <ul>
                 <div class="navbar-header pull-right">
                <h5 style="color:white;font-family: 'El Messiri', sans-serif;">hello <?php echo $name;?></h5>
                 <a href="<?php echo site_url('Logout');?>"><span class="glyphicon glyphicon-log-in"></span> Logout</a>
              </ul>
            </nav>
        </div>
    </div>
    </head>