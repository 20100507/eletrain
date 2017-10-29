package cn.edu.qqhru.train.vo;

import java.util.List;

import cn.edu.qqhru.train.pojo.Plan;

public class PlanVo extends Plan {
	private String adminName;
	private List<String> devNames;
	private List<String> bookNames;
	private List<String> teacherNames;
	private List<String> classesNames;
	private List<Integer> teacherIds;
	private List<Integer> bookIds;
	private List<Integer> devIds;
	private List<Integer> classesIds;
	
	
	public List<Integer> getTeacherIds() {
		return teacherIds;
	}
	public void setTeacherIds(List<Integer> teacherIds) {
		this.teacherIds = teacherIds;
	}
	public List<Integer> getBookIds() {
		return bookIds;
	}
	public void setBookIds(List<Integer> bookIds) {
		this.bookIds = bookIds;
	}
	public List<Integer> getDevIds() {
		return devIds;
	}
	public void setDevIds(List<Integer> devIds) {
		this.devIds = devIds;
	}
	public List<Integer> getClassesIds() {
		return classesIds;
	}
	public void setClassesIds(List<Integer> classesIds) {
		this.classesIds = classesIds;
	}
	public List<String> getClassesNames() {
		return classesNames;
	}
	public void setClassesNames(List<String> classesNames) {
		this.classesNames = classesNames;
	}
	public List<String> getTeacherNames() {
		return teacherNames;
	}
	public void setTeacherNames(List<String> teacherNames) {
		this.teacherNames = teacherNames;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public List<String> getDevNames() {
		return devNames;
	}
	public void setDevNames(List<String> devNames) {
		this.devNames = devNames;
	}
	public List<String> getBookNames() {
		return bookNames;
	}
	public void setBookNames(List<String> bookNames) {
		this.bookNames = bookNames;
	}
	
	
}
