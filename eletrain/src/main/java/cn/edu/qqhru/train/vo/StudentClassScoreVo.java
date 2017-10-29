package cn.edu.qqhru.train.vo;

public class StudentClassScoreVo {
	private String className;
	private String classNo;
	private float theoryscore;
	private float practicescore;
	private float total;
	private Integer sign;
	private String certificateNo;
	
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public float getTheoryscore() {
		return theoryscore;
	}
	public void setTheoryscore(float theoryscore) {
		this.theoryscore = theoryscore;
	}
	public float getPracticescore() {
		return practicescore;
	}
	public void setPracticescore(float practicescore) {
		this.practicescore = practicescore;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public Integer getSign() {
		return sign;
	}
	public void setSign(Integer sign) {
		this.sign = sign;
	}
	
}
