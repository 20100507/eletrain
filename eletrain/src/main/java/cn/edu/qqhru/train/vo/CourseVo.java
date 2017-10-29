package cn.edu.qqhru.train.vo;

import cn.edu.qqhru.train.pojo.Course;
import cn.edu.qqhru.train.pojo.Teacher;

public class CourseVo extends Course{
	private Teacher teacher;
	
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}
