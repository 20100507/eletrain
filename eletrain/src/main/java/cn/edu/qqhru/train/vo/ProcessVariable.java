package cn.edu.qqhru.train.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程变量
 * <p>Title: ProcessVariable</p>
 * <p>Description: </p>
 * <p>School: qiqihar university</p> 
 * @author	BQ
 * @date	2017年9月11日上午12:55:39
 * @version 1.0
 */
public class ProcessVariable implements Serializable{
	
	private static final long serialVersionUID = -6239106001489422717L;
	private String username;
	private Date proDate;
	private String proMessage;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getProDate() {
		return proDate;
	}
	public void setProDate(Date proDate) {
		this.proDate = proDate;
	}
	public String getProMessage() {
		return proMessage;
	}
	public void setProMessage(String proMessage) {
		this.proMessage = proMessage;
	}
	public ProcessVariable() {
		super();
	}
	public ProcessVariable(String username, Date proDate, String proMessage) {
		super();
		this.username = username;
		this.proDate = proDate;
		this.proMessage = proMessage;
	}
	@Override
	public String toString() {
		return "审批人：" + username + "审批时间：" + proDate + "审批意见:" + proMessage+"";
	}

	
}
