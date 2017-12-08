<?php 

class Dauo extends CI_Model{
	function tampil_data(){
		return $this->db->get('user');
	}

	function input_data($data,$table){
		$query=$this->db->insert($table,$data);
		 if($this->db->affected_rows() > 0)
			{
			    return "sukses"; // to the controller
			}else{
				return "gagal";
			}
	}

	public function checklogin($username,$password,$table)
		{
		     $dataSegmentations=$this->db->query("select * from $table where username='$username' and password='$password'  and approval='diterima';");
		     return $dataSegmentations->num_rows();
	    }

	    public function checkemail($email,$table)
		{
		     $dataSegmentations=$this->db->query("select * from $table where email='$email';");
		     return $dataSegmentations->num_rows();
	    }

	    public function cekusername($username)
		{
		     $dataSegmentations=$this->db->query("select * from driver where username='$username';");
		     return $dataSegmentations->num_rows();
	    }
	      public function cekusername2($username)
		{
		     $dataSegmentations=$this->db->query("select * from users where username='$username';");
		     return $dataSegmentations->num_rows();
	    }

 function updatetokenss($email,$token,$table)
		{
		     $this->db->where('email',$email);
		     $this->db->set('token',$token);
		    $datadsa= $this->db->update($table);
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "gagal";
			}
	    }

	      public function ambilharga()
		{
			
		     $dataSegmentations=$this->db->query("select harga from harga where id='1';");
		     $ret = $dataSegmentations->row();
		     return $ret->harga;
	    }

	     public function getname($username='kosong',$password='kosong')
		{
			
		     $dataSegmentations=$this->db->query("select name from login where username='$username' and password='$password';");
		     $ret = $dataSegmentations->row();
		     return $ret->name;
	    }
   public function pushprofil($email)
		{
			
		     $dataSegmentations=$this->db->query("select * from users where email='$email';");
		    return $dataSegmentations->result_array();
	    }

	      public function update($username,$data,$table)
		{
		     $this->db->query("update $table set token='$data' where username='$username';");
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "sukses";
			}
	    }


	     public function pushdriver()
		{
			
		     $dataSegmentations=$this->db->query("select * from driver where statuslogin='login' and point>50 and approval='diterima' and adapanggilan='tidak';");
		    return $dataSegmentations->result_array();
	    }	    

	     public function pushbentors()
		{
			
		     $dataSegmentations=$this->db->query("select * from driver where statuslogin='login' and point>50 and approval='diterima' and adapanggilan='tidak' and sebagai='bentor';");
		    return $dataSegmentations->result_array();
	    }	    

	     public function updatestatus($username,$data,$table,$token)
		{
		     $this->db->query("update $table set statuslogin='$data',token='$token' where username='$username';");
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "gagal";
			}
	    }


	    public function getpoint($username)
		{
			
		     $dataSegmentations=$this->db->query("SELECT point as pt from driver where username='$username' ;");
		     $ret = $dataSegmentations->row();
		     return $ret->pt;
	    }

	     function updatemasukinlatlangtask($username,$data,$table)
		{
		     $this->db->where('username',$username);
		    $datadsa= $this->db->update($table,$data);
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "sukses";
			}
	    }

	    public function cektatusdriver($namadriver,$table)
		{
		     $dataSegmentations=$this->db->query("select * from $table where nama='$namadriver' and adapanggilan='tidak';");
		     return $dataSegmentations->num_rows();
	    }

	    public function updatestatus2($namadriver,$data,$table)
		{
		     $this->db->query("update $table set adapanggilan='$data' where nama='$namadriver';");
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "gagal";
			}
	    }

	    public function ambiltoken($username)
		{
			
		     $dataSegmentations=$this->db->query("select token as pt from users  where username='$username';;");
		      $ret = $dataSegmentations->row();
		     return $ret->pt;
	    }


	    public function updatetoken($namadriver,$data,$table)
		{
		     $this->db->query("update $table set token='$data' where nama='$namadriver';");
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "sukses";
			}
	    }

	    public function ambiltokendriver($namadriver)
		{
			
		     $dataSegmentations=$this->db->query("select token as pt from driver  where nama='$namadriver';");
		      $ret = $dataSegmentations->row();
		     return $ret->pt;
	    }



	    public function pushfotobarang($usernameuser)
		{
		     $dataSegmentations=$this->db->query("select * from kurirberjalan where usernameuser='$usernameuser' and status='belum';");
		    return $dataSegmentations->result_array();
	    }


	      public function updatekurirberjalan($status,$usernameuser,$namadriver,$table)
		{
		     $this->db->query("update  $table set status='$status' where usernameuser='$usernameuser' and namadriver='$namadriver' ;");
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "gagal";
			}
	    }

	     public function updateorderberjalan($status,$usernameuser,$table)
		{
		     $this->db->query("update  $table set status='$status' where usernameuser='$usernameuser' ;");
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "gagal";
			}
	    }
  public function pushbentor($username)
		{
			
		     $dataSegmentations=$this->db->query("select * from driver where username='$username' and sebagai='bentor';");
		    return $dataSegmentations->result_array();
	    }

	    public function pushdriverdetail($namadriver)
		{
			
		     $dataSegmentations=$this->db->query("select * from driver where nama='$namadriver';");
		    return $dataSegmentations->result_array();
	    }

	    public function pushorderankurir($usernameuser)
		{
			
		     $dataSegmentations=$this->db->query("select * from kurirberjalan where usernameuser='$usernameuser' and status='belum';");
		    return $dataSegmentations->result_array();
	    }
	    public function pushorderan($usernameuser)
		{
			
		     $dataSegmentations=$this->db->query("select * from orderberjalan where usernameuser='$usernameuser' and status='belum';");
		    return $dataSegmentations->result_array();
	    }
    public function updateorderankurir($fotodriver,$namadriver)
		{
		     $this->db->query("UPDATE kurirberjalan SET fotodriver='$fotodriver' where namadriver='$namadriver' and status='belum';");
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "sukses";
			}
	    }

	    public function setaktif($username,$aktif,$table)
		{
		     $this->db->query("UPDATE $table SET adapanggilan='$aktif' where username='$username';");
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "sukses";
			}
	    }
public function checkloginweb($username,$password,$table)
		{
		     $dataSegmentations=$this->db->query("select * from $table where username='$username' and password='$password';");
		     return $dataSegmentations->num_rows();
	    }

 public function getstatus($username)
		{
			
		     $dataSegmentations=$this->db->query("select status as pt from login  where username='$username';");
		      $ret = $dataSegmentations->row();
		     return $ret->pt;
	    }

 public function amdriver()
		{
			
		     $dataSegmentations=$this->db->query("select * from driver ;");
		    return $dataSegmentations->result_array();
	    }	 


	    public function updateapprove($id,$status)
		{
		     $this->db->query("update driver set approval='$status' where id='$id';");
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "gagal";
			}
	    }


	      public function ambiltokendrivers($username)
		{
			
		     $dataSegmentations=$this->db->query("select token as pt from driver  where username='$username';");
		      $ret = $dataSegmentations->row();
		     return $ret->pt;
	    }


	     function hapusdriver($id)
		{
		     $this->db->where('id',$id);
		    $datadsa= $this->db->delete('driver');
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "gagal";
			}
	    }


	    public function tambahpoint($id,$point)
		{
		     $this->db->query("update driver set point='$point' where id='$id';");
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "gagal";
			}
	    }

	    public function ambilpoint($id)
		{
			
		     $dataSegmentations=$this->db->query("select point as pt from driver  where id='$id';");
		      $ret = $dataSegmentations->row();
		     return $ret->pt;
	    }

	     function deleteorder($username,$driver)
		{
		     $this->db->where('usernameuser',$username);
		      $this->db->where('namadriver',$driver);
		    $datadsa= $this->db->delete('kurirberjalan');
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "gagal";
			}
	    }


	    public function checklogins($username,$password,$table)
		{
		     $dataSegmentations=$this->db->query("select * from $table where username='$username' and password='$password';");
		     return $dataSegmentations->num_rows();
	    }
}