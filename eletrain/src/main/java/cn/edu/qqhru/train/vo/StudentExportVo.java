package cn.edu.qqhru.train.vo;

import cn.edu.qqhru.train.pojo.Student;

public class StudentExportVo extends Student{
	private String className;
	private Float theoryscore;
	private Float practicescore;
	private Float total;
	private String certificateNo;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Float getTheoryscore() {
		return theoryscore;
	}
	public void setTheoryscore(Float theoryscore) {
		this.theoryscore = theoryscore;
	}
	public Float getPracticescore() {
		return practicescore;
	}
	public void setPracticescore(Float practicescore) {
		this.practicescore = practicescore;
	}
	public Float getTotal() {
		return total;
	}
	public void setTotal(Float total) {
		this.total = total;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	
}
