package cn.edu.qqhru.train.vo;

import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.Course;
import cn.edu.qqhru.train.pojo.Score;
import cn.edu.qqhru.train.pojo.Student;
import cn.edu.qqhru.train.pojo.Teacher;

public class ScoreVo extends Score{
	private Teacher teacher;
	private Classes clazz;
	private Student student;
	private Course course;
	
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public Classes getClazz() {
		return clazz;
	}
	public void setClazz(Classes clazz) {
		this.clazz = clazz;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	
}
