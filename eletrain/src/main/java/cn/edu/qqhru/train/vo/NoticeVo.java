package cn.edu.qqhru.train.vo;

import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.Notice;

public class NoticeVo extends Notice{
	private Admin admin;

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
}
