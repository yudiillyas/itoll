<?php 

class Parangtristis extends CI_Model{
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
  public function getadmin()
		{
			
		     $dataSegmentations=$this->db->query("select * from admin where status='2' ;");
		  return $dataSegmentations->result_array();
	    }

	     public function deletefaktur($nota)
		{
			
		     $this->db->query("DELETE FROM dbo_clt_tnotasub$ WHERE NOTR='$nota'");
		     if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "gagal";
			}
	    }


	     public function ambilhak($username)
		{
			
		     $dataSegmentations=$this->db->query("SELECT * FROM boyhak where adminnya='$username';");
		  return $dataSegmentations->result_array();
	    }

	    public function getsales()
		{
			
		     $dataSegmentations=$this->db->query("select * from CLT_SALES WHERE USNM<>'' and SLID <> '' and SLID <>'OFF';");
		  return $dataSegmentations->result_array();
	    }
	     public function getsales2($slid)
		{
			
		     $dataSegmentations=$this->db->query("select * from CLT_SALES WHERE SLID ='$slid';");
		  return $dataSegmentations->result_array();
	    }

	    public function checkhak($slid)
		{
		     $dataSegmentations=$this->db->query("select * from boyhak where slid='$slid';");
		     return $dataSegmentations->num_rows();
	    }

	    public function deletehak($slid)
		{
			
		     $this->db->query("DELETE  boyhak WHERE slid ='$slid';");
		    if($this->db->affected_rows() > 0)
			{
			    return "sukses";
			}else{
				return "gagal";
			}
	    }
	
}