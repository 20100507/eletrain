package cn.edu.qqhru.train.service;

import java.util.List;

import cn.edu.qqhru.train.pojo.Course;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.CourseVo;

/**
 * <p>Title: CourseService</p>
 * <p>Description: 课程模块的service层</p>
 * <p>School: qiqihar university</p> 
 * @author	白鹏飞
 * @date	2017年9月1日下午6:33:41
 * @version 1.0
 */
public interface CourseService {

	PageBean<CourseVo> getCourseListPageBean(int page, int rows);

	boolean addCourse(Course course);

	Course getCourseById(Integer courseId);

	boolean updateAdminById(Course course);

	PageBean<CourseVo> getCourseListByPageAndCondition(String condition, int page, int rows);

	boolean deleteCourse(Integer id);

	void deleteMultipleCourses(Integer[] ids);

	List<Course> getCourseList();

	boolean addMultipleCourses(List<Course> courseList);

	List<Course> getCourseListByTeacherId(int teacherId);

}
