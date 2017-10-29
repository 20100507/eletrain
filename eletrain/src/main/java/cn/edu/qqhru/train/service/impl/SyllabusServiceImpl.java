package cn.edu.qqhru.train.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.qqhru.train.mapper.ClassesMapper;
import cn.edu.qqhru.train.mapper.ClassesTeacherMapper;
import cn.edu.qqhru.train.mapper.CourseMapper;
import cn.edu.qqhru.train.mapper.SyllabusMapper;
import cn.edu.qqhru.train.mapper.TeacherMapper;
import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.ClassesExample;
import cn.edu.qqhru.train.pojo.Course;
import cn.edu.qqhru.train.pojo.CourseExample;
import cn.edu.qqhru.train.pojo.Syllabus;
import cn.edu.qqhru.train.pojo.SyllabusExample;
import cn.edu.qqhru.train.pojo.SyllabusExample.Criteria;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.pojo.TeacherExample;
import cn.edu.qqhru.train.service.SyllabusService;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.CourseDateVo;

@Service
public class SyllabusServiceImpl implements SyllabusService {
	
	@Autowired
	private SyllabusMapper syllabusMapper;
	@Autowired
	private ClassesMapper classesMapper;
	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	/**
	 * 通过id更新Syllabus
	 */
	@Override
	public void updateSyllabus(Syllabus syllabus) {
		syllabusMapper.updateByPrimaryKey(syllabus);
	}
	/**
	 * 分页查询所有的课表
	 */
	@Override
	public PageBean<CourseDateVo> getAllSyllabus(int page, int rows) {
		PageBean<CourseDateVo> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows,"week desc");
		SyllabusExample example = new SyllabusExample();
		Criteria criteria = example.createCriteria();
		criteria.andSyllabusIdIsNotNull();
		List<Syllabus> list = syllabusMapper.selectByExample(example);
		PageInfo<Syllabus> pageInfo = new PageInfo<>(list);
		ArrayList<CourseDateVo> cdvList = new ArrayList<>();
		
		if(list!=null&&list.size()>0){
			for(Syllabus syll:list){
				Course amCourse=null;
				Course pmCourse=null;
				Course niCourse=null;
				CourseDateVo courseDateVo = new CourseDateVo();
				//封装日期
				courseDateVo.setDate(syll.getWeek());
				//封装班级
				Classes classes = classesMapper.selectByPrimaryKey(syll.getClassId());
				courseDateVo.setClasses(classes);
				//封装上午课程
				if(syll.getAmfirst().trim().length()!=0){
					amCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getAmfirst()));
					courseDateVo.setAmCourse(amCourse);
				}
				//封装下午课程
				if(syll.getPmfirst().trim().length()!=0){
					pmCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getPmfirst()));
					courseDateVo.setPmCourse(pmCourse);
				}
				//封装晚上课程
				if(syll.getNight().trim().length()!=0){
					niCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getNight()));
					courseDateVo.setNiCourse(niCourse);
				}
				//封装上午教师
				if(amCourse!=null){
					Teacher amTeacher = teacherMapper.selectByPrimaryKey(amCourse.getTeacherId());
					courseDateVo.setAmTeacher(amTeacher);
				}
				//封装下午教师
				if(pmCourse!=null){
					Teacher pmTeacher = teacherMapper.selectByPrimaryKey(pmCourse.getTeacherId());
					courseDateVo.setPmTeacher(pmTeacher);			
				}
				//封装晚上教师
				if(niCourse!=null){
					Teacher niTeacher = teacherMapper.selectByPrimaryKey(niCourse.getTeacherId());
					courseDateVo.setNiTeacher(niTeacher);
				}
				//封装课表id
				courseDateVo.setSyllabusId(syll.getSyllabusId());;
				//添加到list集合中
				cdvList.add(courseDateVo);
			}
		}
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(cdvList);
		return pageBean;
	}
	/**
	 * 批量删除Syllabus
	 */
	@Override
	public void delSyllabusByIds(Integer[] ids) {
		SyllabusExample example = new SyllabusExample();
		Criteria criteria = example.createCriteria();
		//将数组转换成list集合
		ArrayList<Integer> idList =new ArrayList<Integer>(Arrays.asList(ids));
		criteria.andSyllabusIdIn(idList);
		syllabusMapper.deleteByExample(example);
	}
	/**
	 * 添加课表
	 */
	@Override
	public void addSyllabus(Syllabus syllabus) {
		//添加课表
		syllabusMapper.insert(syllabus);
	}
	/**
	 * 通过课表ID查询课表
	 */
	@Override
	public Syllabus getSyllabusIdBYid(Integer syllabusId) {
		Syllabus syllabus = syllabusMapper.selectByPrimaryKey(syllabusId);
		if(syllabus!=null){
			return syllabus;
		}
		else{
			return new Syllabus();
		}
	}
	/**
	 * 查询所有的课表
	 */
	@Override
	public List<CourseDateVo> getAllsyllabus() {
		SyllabusExample example = new SyllabusExample();
		example.setOrderByClause("week ASC");
		Criteria criteria = example.createCriteria();
		criteria.andSyllabusIdIsNotNull();
		List<Syllabus> list = syllabusMapper.selectByExample(example);
		List<CourseDateVo> cdvList = new ArrayList<>();
		if(list!=null&&list.size()>0){
			for(Syllabus syll:list){
				Course amCourse=null;
				Course pmCourse=null;
				Course niCourse=null;
				CourseDateVo courseDateVo = new CourseDateVo();
				//封装日期
				courseDateVo.setDate(syll.getWeek());
				//封装班级
				Classes classes = classesMapper.selectByPrimaryKey(syll.getClassId());
				courseDateVo.setClasses(classes);
				//封装上午课程
				if(syll.getAmfirst().trim().length()!=0){
					amCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getAmfirst()));
					courseDateVo.setAmCourse(amCourse);
				}
				//封装下午课程
				if(syll.getPmfirst().trim().length()!=0){
					pmCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getPmfirst()));
					courseDateVo.setPmCourse(pmCourse);
				}
				//封装晚上课程
				if(syll.getNight().trim().length()!=0){
					niCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getNight()));
					courseDateVo.setNiCourse(niCourse);
				}
				//封装上午教师
				if(amCourse!=null){
					Teacher amTeacher = teacherMapper.selectByPrimaryKey(amCourse.getTeacherId());
					courseDateVo.setAmTeacher(amTeacher);
				}
				//封装下午教师
				if(pmCourse!=null){
					Teacher pmTeacher = teacherMapper.selectByPrimaryKey(pmCourse.getTeacherId());
					courseDateVo.setPmTeacher(pmTeacher);			
				}
				//封装晚上教师
				if(niCourse!=null){
					Teacher niTeacher = teacherMapper.selectByPrimaryKey(niCourse.getTeacherId());
					courseDateVo.setNiTeacher(niTeacher);
				}
				//封装课表id
				courseDateVo.setSyllabusId(syll.getSyllabusId());;
				//添加到list集合中
				cdvList.add(courseDateVo);
			}
		}
		return cdvList;
	}
	/**
	 * 通过classesId查询课表
	 */
	@Override
	public List<Syllabus> getSyllabusByClassesId(Integer classesId) {
		SyllabusExample syllabusExample = new SyllabusExample();
		Criteria syllabusCriteria = syllabusExample.createCriteria();
		syllabusCriteria.andClassIdEqualTo(classesId);
		List<Syllabus> syllabusList = syllabusMapper.selectByExample(syllabusExample);
		if(syllabusList!=null&&syllabusList.size()>0){
			return syllabusList;
		}
		else{
			return Collections.emptyList();
		}
	}
	@Override
	public List<Syllabus> getSyllabusByDate(String week) {
		SyllabusExample syllabusExample = new SyllabusExample();
		Criteria syllabusCriteria = syllabusExample.createCriteria();
		syllabusCriteria.andWeekEqualTo(week);
		List<Syllabus> syllabusList = syllabusMapper.selectByExample(syllabusExample);
		if(syllabusList!=null&&syllabusList.size()>0){
			return syllabusList;
		}
		else{
			return Collections.emptyList();
		}
	}
	@SuppressWarnings("null")
	@Override
	public List<Course> getCourseByNotCourseIds(List<Integer> courseIds) {
		List<Course> courseList =new ArrayList<>();
		CourseExample courseExample = new CourseExample();
		cn.edu.qqhru.train.pojo.CourseExample.Criteria courseCriteria = courseExample.createCriteria();
		if(courseIds!=null&&courseIds.size()>0){
			courseCriteria.andCourseIdNotIn(courseIds);
		}else{
			courseCriteria.andCourseIdIsNotNull();
		}
		courseList = courseMapper.selectByExample(courseExample);
		if(courseList!=null&&courseList.size()>0){
			return courseList;
		}
		else{
			return Collections.emptyList();
		}
		
	}
	/**
	 * 查询老师的所有课表
	 */
	@Override
	public PageBean<CourseDateVo> getSyllabusByTeacher(Integer teacherId, int page, int rows) {
		CourseExample courseExample = new CourseExample();
		cn.edu.qqhru.train.pojo.CourseExample.Criteria courseCriteria = courseExample.createCriteria();
		courseCriteria.andTeacherIdEqualTo(teacherId);	
		List<Course> courseList = courseMapper.selectByExample(courseExample);
		
		List<String> courseIdList = new ArrayList<>();
		if(courseList!=null&&courseList.size()>0){
			for (Course course : courseList) {
				courseIdList.add(String.valueOf(course.getCourseId()));
			}
		}
		
		PageBean<CourseDateVo> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		
		List<Syllabus> syllabusList = new ArrayList<>();
		if(courseIdList!=null&&courseIdList.size()>0){
			SyllabusExample syllabusExample = new SyllabusExample();
			syllabusExample.setOrderByClause("week desc");
			Criteria amSyllabusCriteria = syllabusExample.createCriteria();
			amSyllabusCriteria.andAmfirstIn(courseIdList);
			
			Criteria pmSyllabusCriteria = syllabusExample.createCriteria();
			pmSyllabusCriteria.andPmfirstIn(courseIdList);
			
			Criteria niSyllabusCriteria = syllabusExample.createCriteria();
			niSyllabusCriteria.andNightIn(courseIdList);
			syllabusExample.or(pmSyllabusCriteria);
			syllabusExample.or(niSyllabusCriteria);
			
			syllabusList = syllabusMapper.selectByExample(syllabusExample);
			
		}
		
		List<CourseDateVo> cdvList = new ArrayList<>();
		if(syllabusList!=null&&syllabusList.size()>0){
			for(Syllabus syll:syllabusList){
				Course amCourse=null;
				Course pmCourse=null;
				Course niCourse=null;
				CourseDateVo courseDateVo = new CourseDateVo();
				//封装日期
				courseDateVo.setDate(syll.getWeek());
				//封装班级
				Classes classes = classesMapper.selectByPrimaryKey(syll.getClassId());
				courseDateVo.setClasses(classes);
				//封装上午课程
				if(syll.getAmfirst().trim().length()!=0){
					amCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getAmfirst()));
					courseDateVo.setAmCourse(amCourse);
				}
				//封装下午课程
				if(syll.getPmfirst().trim().length()!=0){
					pmCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getPmfirst()));
					courseDateVo.setPmCourse(pmCourse);
				}
				//封装晚上课程
				if(syll.getNight().trim().length()!=0){
					niCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getNight()));
					courseDateVo.setNiCourse(niCourse);
				}
				//封装上午教师
				if(amCourse!=null){
					Teacher amTeacher = teacherMapper.selectByPrimaryKey(amCourse.getTeacherId());
					courseDateVo.setAmTeacher(amTeacher);
				}
				//封装下午教师
				if(pmCourse!=null){
					Teacher pmTeacher = teacherMapper.selectByPrimaryKey(pmCourse.getTeacherId());
					courseDateVo.setPmTeacher(pmTeacher);			
				}
				//封装晚上教师
				if(niCourse!=null){
					Teacher niTeacher = teacherMapper.selectByPrimaryKey(niCourse.getTeacherId());
					courseDateVo.setNiTeacher(niTeacher);
				}
				//封装课表id
				courseDateVo.setSyllabusId(syll.getSyllabusId());;
				//添加到list集合中
				cdvList.add(courseDateVo);
			}
		}
		PageInfo<Syllabus> pageInfo = new PageInfo<>(syllabusList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(cdvList);
		return pageBean;
		
	}
	/**
	 * 获得老师的所有课表（不分页）
	 */
	@Override
	public List<CourseDateVo> getSyllabusByTeacher(Integer teacherId) {
		CourseExample courseExample = new CourseExample();
		cn.edu.qqhru.train.pojo.CourseExample.Criteria courseCriteria = courseExample.createCriteria();
		courseCriteria.andTeacherIdEqualTo(teacherId);	
		List<Course> courseList = courseMapper.selectByExample(courseExample);
		
		List<String> courseIdList = new ArrayList<>();
		for (Course course : courseList) {
			courseIdList.add(String.valueOf(course.getCourseId()));
		}
		List<Syllabus> syllabusList= new ArrayList<>();
		if(courseIdList!=null&&courseIdList.size()>0){
			SyllabusExample syllabusExample = new SyllabusExample();
			Criteria amSyllabusCriteria = syllabusExample.createCriteria();
			amSyllabusCriteria.andAmfirstIn(courseIdList);
			Criteria pmSyllabusCriteria = syllabusExample.createCriteria();
			pmSyllabusCriteria.andPmfirstIn(courseIdList);
			Criteria niSyllabusCriteria = syllabusExample.createCriteria();
			niSyllabusCriteria.andNightIn(courseIdList);
			syllabusExample.or(pmSyllabusCriteria);
			syllabusExample.or(niSyllabusCriteria);
			syllabusList = syllabusMapper.selectByExample(syllabusExample);
		}
		
		List<CourseDateVo> cdvList = new ArrayList<>();
		if(syllabusList!=null&&syllabusList.size()>0){
			for(Syllabus syll:syllabusList){
				Course amCourse=null;
				Course pmCourse=null;
				Course niCourse=null;
				CourseDateVo courseDateVo = new CourseDateVo();
				//封装日期
				courseDateVo.setDate(syll.getWeek());
				//封装班级
				Classes classes = classesMapper.selectByPrimaryKey(syll.getClassId());
				courseDateVo.setClasses(classes);
				//封装上午课程
				if(syll.getAmfirst().trim().length()!=0){
					amCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getAmfirst()));
					courseDateVo.setAmCourse(amCourse);
				}
				//封装下午课程
				if(syll.getPmfirst().trim().length()!=0){
					pmCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getPmfirst()));
					courseDateVo.setPmCourse(pmCourse);
				}
				//封装晚上课程
				if(syll.getNight().trim().length()!=0){
					niCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getNight()));
					courseDateVo.setNiCourse(niCourse);
				}
				//封装上午教师
				if(amCourse!=null){
					Teacher amTeacher = teacherMapper.selectByPrimaryKey(amCourse.getTeacherId());
					courseDateVo.setAmTeacher(amTeacher);
				}
				//封装下午教师
				if(pmCourse!=null){
					Teacher pmTeacher = teacherMapper.selectByPrimaryKey(pmCourse.getTeacherId());
					courseDateVo.setPmTeacher(pmTeacher);			
				}
				//封装晚上教师
				if(niCourse!=null){
					Teacher niTeacher = teacherMapper.selectByPrimaryKey(niCourse.getTeacherId());
					courseDateVo.setNiTeacher(niTeacher);
				}
				//封装课表id
				courseDateVo.setSyllabusId(syll.getSyllabusId());;
				//添加到list集合中
				cdvList.add(courseDateVo);
			}
		}
		
		return cdvList;
	}
	/**
	 * 包装课表
	 * @param syllList
	 * @return
	 */
	public List<CourseDateVo> PackagedSyllabusBySyllabusList(List<Syllabus> syllList) {
		List<CourseDateVo> cdvList = new ArrayList<>();
		if(syllList!=null&&syllList.size()>0){
			for(Syllabus syll:syllList){
				Course amCourse=null;
				Course pmCourse=null;
				Course niCourse=null;
				CourseDateVo courseDateVo = new CourseDateVo();
				//封装日期
				courseDateVo.setDate(syll.getWeek());
				//封装班级
				Classes classes = classesMapper.selectByPrimaryKey(syll.getClassId());
				courseDateVo.setClasses(classes);
				//封装上午课程
				if(syll.getAmfirst().trim().length()!=0){
					amCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getAmfirst()));
					courseDateVo.setAmCourse(amCourse);
				}
				//封装下午课程
				if(syll.getPmfirst().trim().length()!=0){
					pmCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getPmfirst()));
					courseDateVo.setPmCourse(pmCourse);
				}
				//封装晚上课程
				if(syll.getNight().trim().length()!=0){
					niCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getNight()));
					courseDateVo.setNiCourse(niCourse);
				}
				//封装上午教师
				if(amCourse!=null){
					Teacher amTeacher = teacherMapper.selectByPrimaryKey(amCourse.getTeacherId());
					courseDateVo.setAmTeacher(amTeacher);
				}
				//封装下午教师
				if(pmCourse!=null){
					Teacher pmTeacher = teacherMapper.selectByPrimaryKey(pmCourse.getTeacherId());
					courseDateVo.setPmTeacher(pmTeacher);			
				}
				//封装晚上教师
				if(niCourse!=null){
					Teacher niTeacher = teacherMapper.selectByPrimaryKey(niCourse.getTeacherId());
					courseDateVo.setNiTeacher(niTeacher);
				}
				//封装课表id
				courseDateVo.setSyllabusId(syll.getSyllabusId());;
				//添加到list集合中
				cdvList.add(courseDateVo);
			}
		}
		return cdvList;
	}
	@Override
	public PageBean<CourseDateVo> getPackagedSyllabusByDate(String index, int page, int rows) {
		PageBean<CourseDateVo> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		
		SyllabusExample syllabusExample = new SyllabusExample();
		Criteria syllabusCriteria = syllabusExample.createCriteria();
		syllabusCriteria.andWeekEqualTo(index);
		List<Syllabus> syllabusList = syllabusMapper.selectByExample(syllabusExample);
		List<CourseDateVo> packagedSyllabusBySyllabusList = PackagedSyllabusBySyllabusList(syllabusList);
		
		PageInfo<Syllabus> pageInfo = new PageInfo<>(syllabusList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(packagedSyllabusBySyllabusList);
		return pageBean;
	}
	/**
	 * 通过班级名称查询课表
	 */
	@Override
	public PageBean<CourseDateVo> getPackagedSyllabusByClasses(String index, int page, int rows) {
		ClassesExample classesExample = new ClassesExample();
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria classesCriteria = classesExample.createCriteria();
		classesCriteria.andCnameEqualTo(index);
		List<Classes> classesList = classesMapper.selectByExample(classesExample);
		List<Integer> classesIds = new ArrayList<>();
		if(classesList!=null&&classesList.size()>0){
			for (Classes classes : classesList) {
				classesIds.add(classes.getClassesId());
			}
		}
		
		PageBean<CourseDateVo> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		
		List<Syllabus> syllabusList = new ArrayList<>();
		if(classesIds!=null&&classesIds.size()>0){
			SyllabusExample syllabusExample = new SyllabusExample();
			syllabusExample.setOrderByClause("week desc");
			Criteria syllabusCriteria = syllabusExample.createCriteria();
			syllabusCriteria.andClassIdIn(classesIds);
			syllabusList = syllabusMapper.selectByExample(syllabusExample);
		}
		List<CourseDateVo> packagedSyllabusBySyllabusList = PackagedSyllabusBySyllabusList(syllabusList);
		
		PageInfo<Syllabus> pageInfo = new PageInfo<>(syllabusList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(packagedSyllabusBySyllabusList);
		return pageBean;
		
	}
	/**
	 * 通过教师姓名获得课表
	 */
	@Override
	public PageBean<CourseDateVo> getPackagedSyllabusByTeacherUsername(String index, int page, int rows) {
		//通过老师姓名获得老师id
		TeacherExample teacherExample = new TeacherExample();
		cn.edu.qqhru.train.pojo.TeacherExample.Criteria teahcerCriteria = teacherExample.createCriteria();
		teahcerCriteria.andUsernameEqualTo(index);
		List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
		List<Integer> teacherIds = new ArrayList<>();
		if(teacherList!=null&&teacherList.size()>0){
			for (Teacher teacher : teacherList) {
				teacherIds.add(teacher.getTeacherId());
			}
		}
		//通过老师id获得老师所交的课程
		List<Course> courseList = new ArrayList<>();
		if (teacherIds!=null&&teacherIds.size()>0) {
			CourseExample courseExample = new CourseExample();
			cn.edu.qqhru.train.pojo.CourseExample.Criteria courseCriteria = courseExample.createCriteria();
			courseCriteria.andTeacherIdIn(teacherIds);
			courseList = courseMapper.selectByExample(courseExample);
		}
		List<String> courseIdList = new ArrayList<>();
		for (Course course : courseList) {
			courseIdList.add(String.valueOf(course.getCourseId()));
		}
		PageBean<CourseDateVo> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		
		//通过课程id获得课表
		List<Syllabus> syllabusList = new ArrayList<>();
		if(courseIdList!=null&&courseIdList.size()>0){
			SyllabusExample syllabusExample = new SyllabusExample();
			syllabusExample.setOrderByClause("week desc");
			Criteria amSyllabusCriteria = syllabusExample.createCriteria();
			amSyllabusCriteria.andAmfirstIn(courseIdList);
			Criteria pmSyllabusCriteria = syllabusExample.createCriteria();
			pmSyllabusCriteria.andPmfirstIn(courseIdList);
			Criteria niSyllabusCriteria = syllabusExample.createCriteria();
			niSyllabusCriteria.andNightIn(courseIdList);
			syllabusExample.or(pmSyllabusCriteria);
			syllabusExample.or(niSyllabusCriteria);
			syllabusList = syllabusMapper.selectByExample(syllabusExample);
		}
		List<CourseDateVo> packagedSyllabusBySyllabusList = PackagedSyllabusBySyllabusList(syllabusList);
		
		PageInfo<Syllabus> pageInfo = new PageInfo<>(syllabusList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(packagedSyllabusBySyllabusList);
		return pageBean;
		
	}
	/**
	 * 通过班主任查询课表
	 */
	@Override
	public PageBean<CourseDateVo> getPackagedSyllabusByClassesTeacher(List<Integer> classesIds,String week, List<Integer> teacherIds, int page, int rows) {
		
		//通过老师id获得老师所交的课程
		List<Course> courseList = new ArrayList<>();
		if (teacherIds!=null&&teacherIds.size()>0) {
			CourseExample courseExample = new CourseExample();
			cn.edu.qqhru.train.pojo.CourseExample.Criteria courseCriteria = courseExample.createCriteria();
			courseCriteria.andTeacherIdIn(teacherIds);
			courseList = courseMapper.selectByExample(courseExample);
		}
		List<String> courseIdList = new ArrayList<>();
		for (Course course : courseList) {
			courseIdList.add(String.valueOf(course.getCourseId()));
		}
		PageBean<CourseDateVo> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);

		//通过课程id获得课表
		List<Syllabus> syllabusList = new ArrayList<>();
		if(courseIdList!=null&&courseIdList.size()>0){
			SyllabusExample syllabusExample = new SyllabusExample();
			syllabusExample.setOrderByClause("week desc");

			cn.edu.qqhru.train.pojo.SyllabusExample.Criteria amSyllabusCriteria = syllabusExample.createCriteria();
			amSyllabusCriteria.andAmfirstIn(courseIdList);
			if(classesIds!=null&&classesIds.size()>0){
				amSyllabusCriteria.andClassIdIn(classesIds);
			}
			else if(week!=null){
				amSyllabusCriteria.andWeekEqualTo(week);
			}

			cn.edu.qqhru.train.pojo.SyllabusExample.Criteria pmSyllabusCriteria = syllabusExample.createCriteria();
			pmSyllabusCriteria.andPmfirstIn(courseIdList);
			if(classesIds!=null&&classesIds.size()>0){
				pmSyllabusCriteria.andClassIdIn(classesIds);
			}
			else if(week!=null){
				pmSyllabusCriteria.andWeekEqualTo(week);
			}

			cn.edu.qqhru.train.pojo.SyllabusExample.Criteria niSyllabusCriteria = syllabusExample.createCriteria();
			niSyllabusCriteria.andNightIn(courseIdList);
			if(classesIds!=null&&classesIds.size()>0){
				niSyllabusCriteria.andClassIdIn(classesIds);
			}
			else if(week!=null){
				niSyllabusCriteria.andWeekEqualTo(week);
			}
			syllabusExample.or(pmSyllabusCriteria);
			syllabusExample.or(niSyllabusCriteria);
			syllabusList = syllabusMapper.selectByExample(syllabusExample);
		}
		
		List<CourseDateVo> packagedSyllabusBySyllabusList = PackagedSyllabusBySyllabusList(syllabusList);
		PageInfo<Syllabus> pageInfo = new PageInfo<>(syllabusList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(packagedSyllabusBySyllabusList);
		return pageBean;
	}

	/**
	 * 通过日期查询
	 */
	@Override
	public PageBean<CourseDateVo> getPackagedSyllabusByDate(List<Integer> classesIds, String index, int page, int rows) {
		PageBean<CourseDateVo> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);

		//通过课程id获得课表
		List<Syllabus> syllabusList = new ArrayList<>();
		if(classesIds!=null&&classesIds.size()>0&&index!=null&&index.length()>0){
			SyllabusExample syllabusExample = new SyllabusExample();
			Criteria syllabusCriteria = syllabusExample.createCriteria();
			syllabusCriteria.andClassIdIn(classesIds);
			syllabusCriteria.andWeekEqualTo(index);
			syllabusList = syllabusMapper.selectByExample(syllabusExample);
			
		}
		List<CourseDateVo> packagedSyllabusBySyllabusList = PackagedSyllabusBySyllabusList(syllabusList);
		PageInfo<Syllabus> pageInfo = new PageInfo<>(syllabusList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(packagedSyllabusBySyllabusList);
		return pageBean;
		
	}
	@Override
	public PageBean<CourseDateVo> getPackagedSyllabusByClassesName(List<Integer> classesIds, int page, int rows) {
		PageBean<CourseDateVo> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);

		//通过课程id获得课表
		List<Syllabus> syllabusList = new ArrayList<>();
		if(classesIds!=null&&classesIds.size()>0){
			SyllabusExample syllabusExample = new SyllabusExample();
			syllabusExample.setOrderByClause("week ASC");
			Criteria syllabusCriteria = syllabusExample.createCriteria();
			syllabusCriteria.andClassIdIn(classesIds);
			syllabusList = syllabusMapper.selectByExample(syllabusExample);
		}
		List<CourseDateVo> packagedSyllabusBySyllabusList = PackagedSyllabusBySyllabusList(syllabusList);
		PageInfo<Syllabus> pageInfo = new PageInfo<>(syllabusList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(packagedSyllabusBySyllabusList);
		return pageBean;
		
	}
	
	
}
