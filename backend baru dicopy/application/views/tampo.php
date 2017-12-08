<?php
defined('BASEPATH') OR exit('No direct script access allowed');
$this->load->helper('url');


?><!DOCTYPE html>
<html lang="en">
<head  style="font-family: 'Indie Flower', cursive;">
<link rel="icon"  href="<?php echo base_url();?>image/logo.png" />
	<meta charset="utf-8">
<body style="background-image: url('<?php echo base_url();?>image/back.jpg');
   background-repeat: repeat;font-family: 'Indie Flower', cursive;"> 
  
    <div class="container" style="margin-top:180px;height:100%">
    <div  class="col-md-7 col-md-offset-3" style="padding:40px;background-color:gray;opacity:0.9">
    <form method="POST" action="<?php echo site_url('tambahpoint');?>" enctype="multipart/form-data">
 <h1>Silahkan masukan Point yang akan diisi</h1>
  <hr>
    
  <label>Point: </label><br>
  <input type="number" class="col-md-12"placeholder="Jumlah Point" id="point" name="point"  required="" autofocus=""/>
    <input type="text" value="<?php echo $id;?>" name="id"  id="id"  hidden/>
     <input type="text" value="<?php echo $username;?>" name="username"  id="username" hidden />
  <br><br>
   <div id="content" class="text-center col-md-12 col-md-offset-12;">.
     <hr>
    <input type="submit" name="submit" class="submit" id="submit">
    </div>
    </form>
  </div>
  </div>
 </body>


</body>
</html>
