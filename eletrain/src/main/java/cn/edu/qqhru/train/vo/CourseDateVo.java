package cn.edu.qqhru.train.vo;

import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.Course;
import cn.edu.qqhru.train.pojo.Teacher;

public class CourseDateVo extends Course{
	private String date;
	private Course amCourse;
	private Course pmCourse;
	private Course niCourse;
	private Teacher amTeacher;
	private Teacher pmTeacher;
	private Teacher niTeacher;
	private Classes classes;
	private Integer syllabusId;
	
	
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public Integer getSyllabusId() {
		return syllabusId;
	}
	public void setSyllabusId(Integer syllabusId) {
		this.syllabusId = syllabusId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Course getAmCourse() {
		return amCourse;
	}
	public void setAmCourse(Course amCourse) {
		this.amCourse = amCourse;
	}
	public Course getPmCourse() {
		return pmCourse;
	}
	public void setPmCourse(Course pmCourse) {
		this.pmCourse = pmCourse;
	}
	public Course getNiCourse() {
		return niCourse;
	}
	public void setNiCourse(Course niCourse) {
		this.niCourse = niCourse;
	}
	public Teacher getAmTeacher() {
		return amTeacher;
	}
	public void setAmTeacher(Teacher amTeacher) {
		this.amTeacher = amTeacher;
	}
	public Teacher getPmTeacher() {
		return pmTeacher;
	}
	public void setPmTeacher(Teacher pmTeacher) {
		this.pmTeacher = pmTeacher;
	}
	public Teacher getNiTeacher() {
		return niTeacher;
	}
	public void setNiTeacher(Teacher niTeacher) {
		this.niTeacher = niTeacher;
	}
}
