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
    <div  class="col-md-12 col-md-offset-1" style="padding:40px;background-color:gray;opacity:0.9;">
 <table class="table" >
 <thead > <caption class="text-center" style="opacity:0.8;color: #8080ff;background-color:black;"/>
        <strong> Data Driver </strong><caption>

          <tr style="background-color:blue;color:yellow">
            <th>Nama driver</th>
            <th>KTP</th>
             <th>Nomer HP</th>
             <th>Point</th>
             <th>Approval?</th>
               <th>Foto</th>
                <th>KTP</th>
                 <th>SIM</th>
                   <th>KK</th>
                 <th></th>
                  <th></th>
          </tr>
             </thead>
		 <?php foreach ($data as $datanya) { $approve=$datanya['approval']; ?>
      <tr>
      <td><?php echo $datanya['nama'];?></td>
    <td><?php echo $datanya['ktp'];?></td>
    <td><?php echo $datanya['nomerhp'];?></td>
    <td><?php echo $datanya['point'];?></td>
      <td><?php echo $datanya['approval'];?></td>
     <td> <img src="<?php echo $datanya['foto'];?>"  id="image" style="cursor: pointer; cursor: hand; " name="image" width="80" height="80" alt="noPicture"
      onclick="showImg(this.src,600,480,'This is Image1');">
    	</td>
     <td> <img src="data:image/jpeg;base64,<?php echo base64_decode(base64_encode($datanya['fotoktp']));?>" id="image" style="cursor: pointer; cursor: hand; " 
     name="image" width="80" height="80" alt="noPicture" onclick="showImg(this.src,1024,768,'This is Image1');">
     </td>
      <td> <img src="data:image/jpeg;base64,<?php echo base64_decode(base64_encode($datanya['fotosim']));?>" id="image" style="cursor: pointer; cursor: hand; " name="image" width="80" height="80" alt="noPicture" onclick="showImg(this.src,600,480,'This is Image1');">
      </td>      </td>
       <td> <img src="data:image/jpeg;base64,<?php echo base64_decode(base64_encode($datanya['fotokk']));?>" id="image" style="cursor: pointer; cursor: hand; " name="image" width="80" height="80" alt="noPicture" onclick="showImg(this.src,600,480,'This is Image1');">
      </td>
        <?php if($approve=="tidak"){ ?>
        <form action = "<?php echo site_url('approve');?>" method = "POST"> 
     <td><input type="text" value="<?php echo $datanya['username'];?>" id="username" name="username" hidden/>
         <input type="text" value="<?php echo $datanya['id'];?>" id="id" name="id" hidden/>
         <input type = "submit" name = "tambah"  value = "Approve" id="tambah" class = " edit" >
          </td>
        </form>
          <form action = "<?php echo site_url('tolak');?>" method = "POST"> 
     <td><input type="text" value="<?php echo $datanya['username'];?>" id="username" name="username" hidden/>
         <input type="text" value="<?php echo $datanya['id'];?>" id="id" name="id" hidden/>
         <input type = "submit" name = "tolak"  value = "Tolak" id="tolak" class = " edit" >
          </td>
        </form>
           <form action = "<?php echo site_url('edit');?>" method = "POST"> 
     <td><input type="text" value="<?php echo $datanya['username'];?>" id="username" name="username" hidden/>
         <input type="text" value="<?php echo $datanya['id'];?>" id="id" name="id" hidden/>
         <input type = "submit" name = "tambah"  value = "Edit" id="tambah" class = " edit" >
          </td>
        </form>
        <?php
         }else if($approve=="diterima"){

          ?>
           <form action = "<?php echo site_url('suspend');?>" method = "POST"> 
     <td><input type="text" value="<?php echo $datanya['username'];?>" id="username" name="username" hidden/>
         <input type="text" value="<?php echo $datanya['id'];?>" id="id" name="id" hidden/>
         <input type = "submit" name = "suspend"  value = "Suspend" id="suspend" class = " edit" >
          </td>
        </form>
        <form action = "<?php echo site_url('tampo');?>" method = "POST"> 
     <td><input type="text" value="<?php echo $datanya['username'];?>" id="username" name="username" hidden/>
         <input type="text" value="<?php echo $datanya['id'];?>" id="id" name="id" hidden/>
          <input type="text" value="<?php echo $datanya['nama'];?>" id="nama" name="nama" hidden/>
         <input type = "submit" name = "tampo"  value = "Tambah Point" id="tampo" class = " edit" >
          </td>
        </form>

        <?php
         }else if($approve=="suspended"){

          ?>

          <form action = "<?php echo site_url('hapus');?>" method = "POST"> 
     <td><input type="text" value="<?php echo $datanya['username'];?>" id="username" name="username" hidden/>
         <input type="text" value="<?php echo $datanya['id'];?>" id="id" name="id" hidden />
         <input type = "submit" name = "hapus"  value = "Hapus" id="hapus" class = " edit" >
          </td>
        </form>
         <form action = "<?php echo site_url('unsuspend');?>" method = "POST"> 
     <td><input type="text" value="<?php echo $datanya['username'];?>" id="username" name="username" hidden/>
         <input type="text" value="<?php echo $datanya['id'];?>" id="id" name="id" hidden/>
         <input type = "submit" name = "Unsuspend"  value = "Unsuspend" id="Unsuspend" class = " edit" >
          </td>
        </form>
        <?php }

        else{ 


         ?>
<form action = "<?php echo site_url('hapus');?>" method = "POST"> 
     <td><input type="text" value="<?php echo $datanya['username'];?>" id="username" name="username" hidden/>
         <input type="text" value="<?php echo $datanya['id'];?>" id="id" name="id" hidden />
         <input type = "submit" name = "hapus"  value = "Hapus" id="hapus" class = " edit" >
          </td>
        </form>
         <form action = "<?php echo site_url('approve');?>" method = "POST"> 
     <td><input type="text" value="<?php echo $datanya['username'];?>" id="username" name="username" hidden/>
         <input type="text" value="<?php echo $datanya['id'];?>" id="id" name="id" hidden/>
         <input type = "submit" name = "tambah"  value = "Approve" id="tambah" class = " edit" >
          </td>
        </form>
          <?php 

          } 

          ?>
      
        
      </tr>
      <?php } ?>
  </table>
  </div>
  </div>
 </body>


</body>
</html>
<script type="text/javascript">

function showImg(imgSrc, H, W, Caption) {
var newImg = window.open("","myImg",config="height="+H+",width="+W+"")
newImg.document.write("<title>"+ Caption +"</title>")
newImg.document.write("<img src='"+ imgSrc +"' height='"+ H +"' width='"+ W +"' onclick='window.close()' style='position:absolute;left:0;top:0'>")
newImg.document.write("<script type='text/javascript'> document.oncontextmenu = new Function(\"return false\") </sc"+"ript>")
newImg.document.close()
}

</script>