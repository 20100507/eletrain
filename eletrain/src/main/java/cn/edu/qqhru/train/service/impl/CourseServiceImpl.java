package cn.edu.qqhru.train.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.qqhru.train.mapper.CourseMapper;
import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.AdminExample;
import cn.edu.qqhru.train.pojo.Course;
import cn.edu.qqhru.train.pojo.CourseExample;
import cn.edu.qqhru.train.service.CourseService;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.CourseVo;

/**
 * 
 * <p>Title: CourseServiceImpl</p>
 * <p>Description: 课程模块service层的实现类</p>
 * <p>School: qiqihar university</p> 
 * @author	白鹏飞
 * @date	2017年9月1日下午6:32:27
 * @version 1.0
 */
@Service("courseService")
public class CourseServiceImpl implements CourseService {
	@Autowired
	private CourseMapper courseMapper;
	
	private CourseExample courseExample;
	
	/**
	 * <p>Title: getCourseListPageBean</p>
	 * <p>Description: 以分页的方式显示所有课程信息service层上的实现类</p>
	 * @param page
	 * @param rows
	 * @return
	 * @see cn.edu.qqhru.train.service.CourseService#getCourseListPageBean(int, int)
	 */
	@Override
	public PageBean<CourseVo> getCourseListPageBean(int page, int rows) {
		PageBean<CourseVo> courseListPageBean = new PageBean<CourseVo>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		courseExample = new CourseExample();
		courseExample.createCriteria().andCourseIdIsNotNull();
		List<CourseVo> courseVoList = new ArrayList<CourseVo>();
		List<Course> courseList = courseMapper.selectByExample(courseExample);
		for(Course course : courseList){
			CourseVo courseVo = new CourseVo();
			courseVo.setCourseId(course.getCourseId());
			courseVo.setCname(course.getCname());
			courseVo.setCapacity(course.getCapacity());
			courseVo.setPlace(course.getPlace());
			courseVo.setInfo(course.getInfo());
			courseVo.setTeacherId(course.getTeacherId());
			courseVo.setPeriod(course.getPeriod());
			courseVoList.add(courseVo);
		}
		PageInfo<Course> pageInfo = new PageInfo<Course>(courseList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		courseListPageBean.setPage(pageNum);
		courseListPageBean.setTotalPage(totalPage);
		courseListPageBean.setList(courseVoList);
		return courseListPageBean;
	}

	@Override
	public boolean addCourse(Course course) {
		int num = courseMapper.insert(course);
		if(num == 1){
			return true;
		}
		return false;
	}

	@Override
	public Course getCourseById(Integer courseId) {
		Course course = courseMapper.selectByPrimaryKey(courseId);
		return course;
	}

	@Override
	public boolean updateAdminById(Course course) {
		int num = courseMapper.updateByPrimaryKeySelective(course);
		if(num == 1){
			return true;
		}
		return false;
	}

	@Override
	public PageBean<CourseVo> getCourseListByPageAndCondition(String condition, int page, int rows) {
		PageBean<CourseVo> adminListPageBean = new PageBean<CourseVo>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		courseExample = new CourseExample();
		courseExample.createCriteria().andCourseIdIsNotNull().andCnameEqualTo(condition);
		List<CourseVo> courseVoList = new ArrayList<CourseVo>();
		List<Course> courseList = courseMapper.selectByExample(courseExample);
		for(Course course : courseList){
			CourseVo courseVo = new CourseVo();
			courseVo.setCourseId(course.getCourseId());
			courseVo.setCname(course.getCname());
			courseVo.setCapacity(course.getCapacity());
			courseVo.setPlace(course.getPlace());
			courseVo.setInfo(course.getInfo());
			courseVo.setTeacherId(course.getTeacherId());
			courseVo.setPeriod(course.getPeriod());
			courseVoList.add(courseVo);
		}
		PageInfo<Course> pageInfo = new PageInfo<Course>(courseList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		adminListPageBean.setPage(pageNum);
		adminListPageBean.setTotalPage(totalPage);
		adminListPageBean.setList(courseVoList);
		return adminListPageBean;
	}

	@Override
	public boolean deleteCourse(Integer id) {
		int num = courseMapper.deleteByPrimaryKey(id);
		if(num == 1){
			return true;
		}
		return false;
	}

	@Override
	public void deleteMultipleCourses(Integer[] ids) {
		for(int i=0; i<ids.length; i++){
			courseMapper.deleteByPrimaryKey(ids[i]);
		}
	}

	@Override
	public List<Course> getCourseList() {
		courseExample = new CourseExample();
		courseExample.createCriteria().andCourseIdIsNotNull();
		List<Course> courseList = courseMapper.selectByExample(courseExample);
		return courseList;
	}

	@Override
	public boolean addMultipleCourses(List<Course> courseList) {
		boolean flag = true;
		for(Course course : courseList){
			int num = courseMapper.insert(course);
			if(num != 1){
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public List<Course> getCourseListByTeacherId(int teacherId) {
		courseExample = new CourseExample();
		courseExample.createCriteria().andCourseIdIsNotNull().andTeacherIdEqualTo(teacherId);
		List<Course> courseList = courseMapper.selectByExample(courseExample);
		return courseList;
	}

}
