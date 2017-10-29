package cn.edu.qqhru.train.vo;

import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.Teacher;

public class ClassesVo {
	private Classes classes;
	private Teacher classesTeacher;
	private Integer classesCount;
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public Teacher getClassesTeacher() {
		return classesTeacher;
	}
	public void setClassesTeacher(Teacher classesTeacher) {
		this.classesTeacher = classesTeacher;
	}
	public Integer getClassesCount() {
		return classesCount;
	}
	public void setClassesCount(Integer classesCount) {
		this.classesCount = classesCount;
	}
	
}
