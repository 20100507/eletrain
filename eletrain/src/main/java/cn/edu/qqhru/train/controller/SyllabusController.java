package cn.edu.qqhru.train.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.Course;
import cn.edu.qqhru.train.pojo.Syllabus;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.service.ClassesService;
import cn.edu.qqhru.train.service.CourseService;
import cn.edu.qqhru.train.service.SyllabusService;
import cn.edu.qqhru.train.service.TeacherService;
import cn.edu.qqhru.train.utils.DownUtils;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.CourseDateVo;

/**
 * 课表管理
 * 
 * @ClassName SyllabusController
 * @Description TODO
 * @author 张继富
 * @Date 2017年9月9日 下午12:27:32
 * @version 1.0.0
 */
@Controller
@RequestMapping("/syllabus")
public class SyllabusController {
	@Autowired
	private SyllabusService syllabusServiceImpl;
	@Autowired
	private ClassesService classesServiceImpl;
	@Autowired
	private CourseService courseServiceImpl;
	@Autowired
	private TeacherService teacherServiceImpl;

	/**
	 * 分页查询课表信息
	 * 
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	public String toIndex(
			Model model,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			HttpServletRequest request) {
		PageBean<CourseDateVo> pageBean = syllabusServiceImpl.getAllSyllabus(page, rows);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("path", "list?");
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "syllabus-list";
	}
	/**
	 * 通过索引查询
	 * @param 课表
	 * @param index 0表示班级，1表示教师
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/searchSyllabus")
	public String searchSyllabus(
			HttpServletRequest request,
			Model model, 
			@RequestParam(value = "searchWay", defaultValue = "0") Integer searchWay,
			String index, 
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows) throws UnsupportedEncodingException{
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		PageBean<CourseDateVo> pageBean = new PageBean<>();
		if(1==searchWay){
			//根据教师姓名查询
			String teacherName;
			if(request.getMethod().equalsIgnoreCase("post")){
				teacherName=index;
			}
			else{
				teacherName = new String(request.getParameter("index").getBytes("ISO-8859-1"), "utf-8");  
			}
			pageBean = syllabusServiceImpl.getPackagedSyllabusByTeacherUsername(teacherName,page,rows);
			model.addAttribute("path", "searchSyllabus?searchWay="+searchWay+"&index="+teacherName);
		}
		else if(0==searchWay){
			//根据班级查询
			String classesName;
			if(request.getMethod().equalsIgnoreCase("post")){
				classesName=index;
			}
			else{
				classesName = new String(request.getParameter("index").getBytes("ISO-8859-1"), "utf-8");  
			}
			pageBean = syllabusServiceImpl.getPackagedSyllabusByClasses(classesName,page,rows);
			model.addAttribute("path", "searchSyllabus?searchWay="+searchWay+"&index="+classesName);
		}
		else if(2==searchWay){
			//根据日期查询
			pageBean= syllabusServiceImpl.getPackagedSyllabusByDate(index,page,rows);
			model.addAttribute("path", "searchSyllabus?searchWay="+searchWay+"&index="+index);
		}
		
		model.addAttribute("pageBean", pageBean);
		return "syllabus-list";
	}
	/**
	 * 课表时间  地点 校验
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/checkDatePlace")
	public String checkDatePlace(
			HttpServletRequest request,
			HttpServletResponse response,
			Syllabus syllabus,
			String oldWeek) throws IOException, ParseException {
		List<Syllabus> syllabusByClassesId = syllabusServiceImpl.getSyllabusByClassesId(syllabus.getClassId());
		Classes classesBYid = classesServiceImpl.getClassesBYid(syllabus.getClassId());
		String returnValue="";
		String amValue="";
		String pmValue="";
		String niValue="";
		//======判断课表日期是否在授课区间内，是否为班级创建过课表======
		//如果不在，返回授课区间
		if(!isInterval(syllabus.getWeek(),classesBYid)){
			returnValue="授课区间为： "+classesBYid.getStarttime()+" 请重新选择";
		}
		//如果在，判断在当前日期下是否为该班级创建过课表
		else{
			//如果是，则返回提示信息
			if(!isCreatedSyllabus(syllabus.getWeek(),syllabusByClassesId)){
				returnValue="该班级已在"+syllabus.getWeek()+"创建过课表，您可以选择去修改或者重新选择日期";
			}
		}
		//============获得所有的可选课程============
		Course amCourse = null;
		Course pmCourse = null;
		Course niCourse = null;
		if(oldWeek.contentEquals(syllabus.getWeek())){
			Syllabus exitSyllabus = syllabusServiceImpl.getSyllabusIdBYid(syllabus.getSyllabusId());
			if(exitSyllabus.getAmfirst().trim().length()!=0){
				amCourse = classesServiceImpl.getCourseByCourseId(Integer.valueOf(exitSyllabus.getAmfirst()));
			}
			if(exitSyllabus.getPmfirst().trim().length()!=0){
				pmCourse = classesServiceImpl.getCourseByCourseId(Integer.valueOf(exitSyllabus.getPmfirst()));
			}
			if(exitSyllabus.getNight().trim().length()!=0){
				niCourse = classesServiceImpl.getCourseByCourseId(Integer.valueOf(exitSyllabus.getNight()));
			}
		}
	
		List<Integer> amCourseIds = new ArrayList<>();
		List<Integer> pmCourseIds = new ArrayList<>();
		List<Integer> niCourseIds = new ArrayList<>();
		List<Syllabus> allsyllabus = syllabusServiceImpl.getSyllabusByDate(syllabus.getWeek());
		for (Syllabus syll : allsyllabus) {
			if(syll.getAmfirst().trim().length()!=0){
				amCourseIds.add(Integer.valueOf(syll.getAmfirst()));
			}
			if(syll.getPmfirst().trim().length()!=0){
				pmCourseIds.add(Integer.valueOf(syll.getPmfirst()));
			}
			if(syll.getNight().trim().length()!=0){
				niCourseIds.add(Integer.valueOf(syll.getNight()));
			}
		}
		
		List<Course> amCourses = syllabusServiceImpl.getCourseByNotCourseIds(amCourseIds);
		if(amCourse!=null){
			amCourses.add(amCourse);
		}
		for (Course course : amCourses) {
			Teacher teacher = teacherServiceImpl.getTeacherBYid(course.getTeacherId());
			amValue+="<option value='"+course.getCourseId()+"'>"+course.getCname()+"("+course.getPlace()+"--"+teacher.getUsername()+")</option>";
		}
		List<Course> pmCourses = syllabusServiceImpl.getCourseByNotCourseIds(pmCourseIds);
		if(pmCourse!=null){
			pmCourses.add(pmCourse);
		}
		for (Course course : pmCourses) {
			Teacher teacher = teacherServiceImpl.getTeacherBYid(course.getTeacherId());
			pmValue+="<option value='"+course.getCourseId()+"'>"+course.getCname()+"("+course.getPlace()+"--"+teacher.getUsername()+")</option>";
		}
		List<Course> niCourses = syllabusServiceImpl.getCourseByNotCourseIds(niCourseIds);
		if(niCourse!=null){
			niCourses.add(niCourse);
		}
		for (Course course : niCourses) {
			Teacher teacher = teacherServiceImpl.getTeacherBYid(course.getTeacherId());
			niValue+="<option value='"+course.getCourseId()+"'>"+course.getCname()+"("+course.getPlace()+"--"+teacher.getUsername()+")</option>";
		}
	
		JSONObject json = new JSONObject();
		json.put("returnValue", returnValue);
		json.put("amValue", amValue);
		json.put("pmValue", pmValue);
		json.put("niValue", niValue);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json);
		return null;
	}
	/**
	 * 判断在当前日期下是否为该班级创建过课表
	 * @param week
	 * @param syllabusByClassesId
	 * @return
	 */
	private boolean isCreatedSyllabus(String week, List<Syllabus> syllabusByClassesId) {
		if(syllabusByClassesId!=null&&syllabusByClassesId.size()>0){
			for (Syllabus syllabus : syllabusByClassesId) {
				if(syllabus.getWeek().contentEquals(week)){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 区间检查
	 * @param syllabusTime
	 * @param classesBYid
	 * @return
	 * @throws ParseException 
	 */
	public boolean isInterval(String syllabusTime,Classes classesBYid) throws ParseException{
		String startAndEndTime = classesBYid.getStarttime();
		String[] split = startAndEndTime.split("~");
		String startTime = split[0];
		String endTime = split[1];
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date stime = sdf.parse(startTime);
		Date etime = sdf.parse(endTime);
		Date ktime = sdf.parse(syllabusTime);
		if(stime.getTime()<=ktime.getTime()&&ktime.getTime()<=etime.getTime()){
			return true;
		}
		else{
			return false;
		}
		
	}
	/**
	 * 跳转到添加课表页面
	 * @return
	 */
	@RequestMapping("/addSyllabusUI")
	public String toAddSyllabusUI(HttpServletRequest request,Model model) {
		// 查询所有的课程
		List<Course> courseList = courseServiceImpl.getCourseList();
		model.addAttribute("courseList", courseList);
		// 查询所有的班级
		List<Classes> allList = classesServiceImpl.getAllClasses();
		ArrayList<Object> classesList = new ArrayList<>();
		for (Classes classes : allList) {
			if(classes.getCapacity()==0){
				classesList.add(classes);
			}
		}
		model.addAttribute("classesList", classesList);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "syllabus-publicUI";
	}
	/**
	 * 跳转到添加课表页面(通过班主任)
	 * 
	 * @return
	 */
	@RequestMapping("/addSyllabusUIByClassesTeacher")
	public String toAddSyllabusUIByClassesTeacher(HttpServletRequest request,Model model) {
		// 查询所有的课程
		List<Course> courseList = courseServiceImpl.getCourseList();
		model.addAttribute("courseList", courseList);
		//获得班主任id
		Teacher teacher = (Teacher)request.getSession().getAttribute("user");
		Integer teacherId = teacher.getTeacherId();
		// 查询班级
		List<Classes> classesList = classesServiceImpl.getAllClassesByClassesTeacher(teacherId);
		model.addAttribute("classesList", classesList);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "classesTeacherSyllabus-publicUI";
	}
	/**
	 * 通过班主任获得班级课表
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/getClassesSyllabusByTeacher")
	public String getClassesSyllabusByTeacher(
			Model model,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			HttpServletRequest request,
			Integer searchWay,
			String index) throws UnsupportedEncodingException{
		Teacher teacher = (Teacher)request.getSession().getAttribute("user");
		Integer teacherId = teacher.getTeacherId();
		List<Classes> classesList = classesServiceImpl.getClassesByTeacherId(teacherId);
		
		List<Integer> classesIds = new ArrayList<>();
		for (Classes classes2 : classesList) {
			classesIds.add(classes2.getClassesId());
		}
				
		if(searchWay!=null&&index!=null){
			PageBean<CourseDateVo> pageBean = new PageBean<CourseDateVo>();
			if(1==searchWay){
				//根据教师姓名查询
				/*if(list!=null&&list.size()>0){
					for (CourseDateVo courseDateVo : list) {
						if(index.contentEquals(courseDateVo.getAmTeacher()==null?"":courseDateVo.getAmTeacher().getUsername())){
							cdvListBySearch.add(courseDateVo);
						}
						else if(index.contentEquals(courseDateVo.getPmTeacher()==null?"":courseDateVo.getPmTeacher().getUsername())){
							cdvListBySearch.add(courseDateVo);
						}
						else if(index.contentEquals(courseDateVo.getNiTeacher()==null?"":courseDateVo.getNiTeacher().getUsername())){
							cdvListBySearch.add(courseDateVo);
						}
					}
				}*/
				String teacherName;
				if(request.getMethod().equalsIgnoreCase("post")){
					teacherName=index;
				}
				else{
					teacherName = new String(request.getParameter("index").getBytes("ISO-8859-1"), "utf-8");  
				}
				List<Teacher> teacherList = teacherServiceImpl.getTeacherByUsername(teacherName);
				List<Integer> teacherIds = new ArrayList<>();
				for (Teacher teacher2 : teacherList) {
					teacherIds.add(teacher2.getTeacherId());
				}
				pageBean = syllabusServiceImpl.getPackagedSyllabusByClassesTeacher(classesIds, null, teacherIds, page, rows);
				model.addAttribute("path", "getClassesSyllabusByTeacher?searchWay="+searchWay+"&index="+teacherName);
			}
			else if(0==searchWay){
				//根据班级查询
				/*for (CourseDateVo courseDateVo : list) {
					if(index.contentEquals(courseDateVo.getClasses().getCname())){
						cdvListBySearch.add(courseDateVo);
					}
				}*/
				String classesName;
				if(request.getMethod().equalsIgnoreCase("post")){
					classesName=index;
				}
				else{
					classesName = new String(request.getParameter("index").getBytes("ISO-8859-1"), "utf-8");  
				}
				List<Classes> classesListByCname = classesServiceImpl.getClassesByClassesName(classesName);
				List<Integer> classesIds2 = new ArrayList<>();
				for (Classes classes : classesListByCname) {
					if(classesIds.contains(classes.getClassesId())){
						classesIds2.add(classes.getClassesId());
					}
				}
				pageBean = syllabusServiceImpl.getPackagedSyllabusByClassesName(classesIds2, page, rows);
				model.addAttribute("path", "getClassesSyllabusByTeacher?searchWay="+searchWay+"&index="+classesName);

			}
			else if(2==searchWay){
				//根据日期查询
				/*for (CourseDateVo courseDateVo : list) {
					if(index.contentEquals(courseDateVo.getDate())){
						cdvListBySearch.add(courseDateVo);
					}
				}*/
				pageBean = syllabusServiceImpl.getPackagedSyllabusByDate(classesIds, index, page, rows);
				model.addAttribute("path", "getClassesSyllabusByTeacher?searchWay="+searchWay+"&index="+index);
			}
			model.addAttribute("pageBean", pageBean);

		}
		else{
			// 查询出课表信息
			ArrayList<CourseDateVo> cdvList = new ArrayList<>();
			PageBean<Syllabus> pageBean2 = classesServiceImpl.getSyllabusByClassesIds(classesIds,page,rows);
			for (Syllabus syll : pageBean2.getList()) {
				Course amCourse=null; 
				Course pmCourse=null; 
				Course niCourse=null; 
				CourseDateVo courseDateVo = new CourseDateVo();
				// 封装日期
				courseDateVo.setDate(syll.getWeek());
				// 封装课表id
				courseDateVo.setSyllabusId(syll.getSyllabusId());
				// 封装上午课程
				if(syll.getAmfirst().trim().length()!=0){
					amCourse = classesServiceImpl.getCourseByCourseId(Integer.parseInt(syll.getAmfirst()));
					courseDateVo.setAmCourse(amCourse);
				}
				// 封装下午课程
				if(syll.getPmfirst().trim().length()!=0){
					pmCourse = classesServiceImpl.getCourseByCourseId(Integer.parseInt(syll.getPmfirst()));
					courseDateVo.setPmCourse(pmCourse);
				}
				// 封装晚上课程
				if(syll.getNight().trim().length()!=0){
					niCourse = classesServiceImpl.getCourseByCourseId(Integer.parseInt(syll.getNight()));
					courseDateVo.setNiCourse(niCourse);
				}
				// 封装上午老师
				if(amCourse!=null){
					Teacher amTeacher = teacherServiceImpl.getTeacherBYid(amCourse.getTeacherId());
					courseDateVo.setAmTeacher(amTeacher);
				}
				// 封装下午老师
				if(pmCourse!=null){
					Teacher pmTeacher = teacherServiceImpl.getTeacherBYid(pmCourse.getTeacherId());
					courseDateVo.setPmTeacher(pmTeacher);
				}
				// 封装晚上老师
				if(niCourse!=null){
					Teacher niTeacher = teacherServiceImpl.getTeacherBYid(niCourse.getTeacherId());
					courseDateVo.setNiTeacher(niTeacher);
				}

				//封装班级
				Classes classes = classesServiceImpl.getClassesBYid(syll.getClassId());
				courseDateVo.setClasses(classes);
				
				// 添加到list集合中
				cdvList.add(courseDateVo);
			}
			
			PageBean<CourseDateVo> pageBean = new PageBean<CourseDateVo>();
			pageBean.setList(cdvList);
			pageBean.setPage(pageBean2.getPage());
			pageBean.setTotalPage(pageBean2.getTotalPage());
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("path", "getClassesSyllabusByTeacher?");
		}
		
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "classesTeacherSyllabus-list";
	}
	/**
	 * 通过老师查询课表
	 * 
	 * @param syllabus
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/getSyllabusByTeacher")
	public String getSyllabusByTeacher(
			Model model,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			Integer searchWay,
			String index,
			HttpServletRequest request) throws UnsupportedEncodingException {
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		Teacher teacher = (Teacher)request.getSession().getAttribute("user");
		Integer teacherId = teacher.getTeacherId();
		
		PageBean<CourseDateVo> pageBean = new PageBean<>();
		if(searchWay!=null&&index!=null){
			//获得老师的所有课表
			if(0==searchWay){
				//根据班级查询
				String classesName;
				if(request.getMethod().equalsIgnoreCase("post")){
					classesName=index;
				}
				else{
					classesName = new String(request.getParameter("index").getBytes("ISO-8859-1"), "utf-8");  
				}
				List<Classes> classesList = classesServiceImpl.getClassesByClassesName(classesName);
				List<Integer> classesIds = new ArrayList<>();
				for (Classes classes : classesList) {
					classesIds.add(classes.getClassesId());
				}
				if(classesIds.size()==0){
					classesIds.add(Integer.MIN_VALUE);
				}
				pageBean = syllabusServiceImpl.getPackagedSyllabusByClassesTeacher(classesIds, null, Arrays.asList(teacherId), page, rows);
				model.addAttribute("path", "getSyllabusByTeacher?searchWay="+searchWay+"&index="+classesName+"&menuId="+menuId);
			}
			else if(2==searchWay){
				//根据日期查询
				pageBean = syllabusServiceImpl.getPackagedSyllabusByClassesTeacher(new ArrayList<Integer>(), index, Arrays.asList(teacherId), page, rows);
				model.addAttribute("path", "getSyllabusByTeacher?searchWay="+searchWay+"&index="+index+"&menuId="+menuId);
			}
		}
		else{
			pageBean = syllabusServiceImpl.getSyllabusByTeacher(teacherId,page,rows);
			model.addAttribute("path", "getSyllabusByTeacher?&menuId="+menuId);
		}
		
		model.addAttribute("pageBean", pageBean);
		return "teacherSyllabus-list";
	}
	/**
	 * 添加课表
	 * 
	 * @param syllabus
	 * @return
	 */
	@RequestMapping("/addSyllabus")
	public String addSyllabus(HttpServletRequest request,Model model,Syllabus syllabus) {
		syllabusServiceImpl.addSyllabus(syllabus);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/syllabus/list";
	}
	/**
	 * 添加课表(通过班主任)
	 * 
	 * @param syllabus
	 * @return
	 */
	@RequestMapping("/addSyllabusByClassesTeacher")
	public String addSyllabusByClassesTeacher(HttpServletRequest request,Model model,Syllabus syllabus) {
		syllabusServiceImpl.addSyllabus(syllabus);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/syllabus/getClassesSyllabusByTeacher";
	}
	/**
	 * 跳转到编辑页面
	 * 
	 * @param model
	 * @param syllabusId
	 * @return
	 */
	@RequestMapping("/updateSyllabusUI")
	public String updateSyllabusUI(HttpServletRequest request,Model model, Integer syllabusId) {
		// 查询所有的班级
		List<Classes> allList = classesServiceImpl.getAllClasses();
		ArrayList<Object> classesList = new ArrayList<>();
		for (Classes classes : allList) {
			if(classes.getCapacity()==0){
				classesList.add(classes);
			}
		}
		model.addAttribute("classesList", classesList);
		// 查询所有的可选课程
		Syllabus syllabus = syllabusServiceImpl.getSyllabusIdBYid(syllabusId);
		model.addAttribute("syllabus", syllabus);
		List<Integer> amCourseIds = new ArrayList<>();
		List<Integer> pmCourseIds = new ArrayList<>();
		List<Integer> niCourseIds = new ArrayList<>();
		List<Syllabus> allsyllabus = syllabusServiceImpl.getSyllabusByDate(syllabus.getWeek());
		for (Syllabus syll : allsyllabus) {
			if(syll.getAmfirst().trim().length()!=0){
				amCourseIds.add(Integer.valueOf(syll.getAmfirst()));
			}
			if(syll.getPmfirst().trim().length()!=0){
				pmCourseIds.add(Integer.valueOf(syll.getPmfirst()));
			}
			if(syll.getNight().trim().length()!=0){
				niCourseIds.add(Integer.valueOf(syll.getNight()));
			}
		}
		List<Course> amCourses = syllabusServiceImpl.getCourseByNotCourseIds(amCourseIds);
		List<CourseDateVo> amCDVList = new ArrayList<>();
		for (Course course : amCourses) {
			CourseDateVo cdv = new CourseDateVo();
			Teacher teacher = teacherServiceImpl.getTeacherBYid(course.getTeacherId());
			cdv.setAmTeacher(teacher);
			cdv.setAmCourse(course);
			amCDVList.add(cdv);
		}
		
		List<Course> pmCourses = syllabusServiceImpl.getCourseByNotCourseIds(pmCourseIds);
		List<CourseDateVo> pmCDVList = new ArrayList<>();
		for (Course course : pmCourses) {
			CourseDateVo cdv = new CourseDateVo();
			Teacher teacher = teacherServiceImpl.getTeacherBYid(course.getTeacherId());
			cdv.setPmTeacher(teacher);
			cdv.setPmCourse(course);
			pmCDVList.add(cdv);
		}
		
		List<Course> niCourses = syllabusServiceImpl.getCourseByNotCourseIds(niCourseIds);
		List<CourseDateVo> niCDVList = new ArrayList<>();
		for (Course course : niCourses) {
			CourseDateVo cdv = new CourseDateVo();
			Teacher teacher = teacherServiceImpl.getTeacherBYid(course.getTeacherId());
			cdv.setNiTeacher(teacher);
			cdv.setNiCourse(course);
			niCDVList.add(cdv);
		}
		
		if(syllabus.getAmfirst().trim().length()!=0){
			CourseDateVo amCDV = new CourseDateVo();
			Course amCourse = classesServiceImpl.getCourseByCourseId(Integer.valueOf(syllabus.getAmfirst()));
			Teacher teacher = teacherServiceImpl.getTeacherBYid(amCourse.getTeacherId());
			amCDV.setAmCourse(amCourse);
			amCDV.setAmTeacher(teacher);
			amCDVList.add(amCDV);
		}
		if(syllabus.getPmfirst().trim().length()!=0){
			CourseDateVo pmCDV = new CourseDateVo();
			Course pmCourse = classesServiceImpl.getCourseByCourseId(Integer.valueOf(syllabus.getPmfirst()));
			Teacher teacher = teacherServiceImpl.getTeacherBYid(pmCourse.getTeacherId());
			pmCDV.setPmCourse(pmCourse);
			pmCDV.setPmTeacher(teacher);
			pmCDVList.add(pmCDV);
		}
		if(syllabus.getNight().trim().length()!=0){
			CourseDateVo niCDV = new CourseDateVo();
			Course niCourse = classesServiceImpl.getCourseByCourseId(Integer.valueOf(syllabus.getNight()));
			Teacher teacher = teacherServiceImpl.getTeacherBYid(niCourse.getTeacherId());
			niCDV.setNiCourse(niCourse);
			niCDV.setNiTeacher(teacher);
			niCDVList.add(niCDV);
		}
		/*model.addAttribute("amCDV", amCDV);
		model.addAttribute("pmCDV", pmCDV);
		model.addAttribute("niCDV", niCDV);*/
		
		model.addAttribute("amCDVList", amCDVList);
		model.addAttribute("pmCDVList", pmCDVList);
		model.addAttribute("niCDVList", niCDVList);
		
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "syllabus-publicUI";
	}
	/**
	 * 跳转到编辑页面（通过班主任）
	 * 
	 * @param model
	 * @param syllabusId
	 * @return
	 */
	@RequestMapping("/updateSyllabusUIByClassesTeacher")
	public String updateSyllabusUIByClassesTeacher(HttpServletRequest request,Model model, Integer syllabusId) {
		// 查询所有的课程
		List<Course> courseList = courseServiceImpl.getCourseList();
		model.addAttribute("courseList", courseList);
		// 查询所有的班级
/*		List<Classes> classesList = classesServiceImpl.getAllClasses();
		model.addAttribute("classesList", classesList);
*/		
		//获得班主任id
		Teacher teacher = (Teacher)request.getSession().getAttribute("user");
		Integer teacherId = teacher.getTeacherId();
		// 查询班级
		List<Classes> classesList = classesServiceImpl.getAllClassesByClassesTeacher(teacherId);
		model.addAttribute("classesList", classesList);
		
		Syllabus syllabus = syllabusServiceImpl.getSyllabusIdBYid(syllabusId);
		model.addAttribute("syllabus", syllabus);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "classesTeacherSyllabus-publicUI";
	}
	/**
	 * 编辑课表
	 * 
	 * @param syllabus
	 * @return
	 */
	@RequestMapping("/updateSyllabus")
	public String updateSyllabus(HttpServletRequest request,Model model,Syllabus syllabus) {
		syllabusServiceImpl.updateSyllabus(syllabus);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/syllabus/list";
	}
	/**
	 * 编辑课表(通过班主任)
	 * 
	 * @param syllabus
	 * @return
	 */
	@RequestMapping("/updateSyllabusByClassesTeacher")
	public String updateSyllabusByClassesTeacher(HttpServletRequest request,Model model,Syllabus syllabus) {
		syllabusServiceImpl.updateSyllabus(syllabus);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/syllabus/getClassesSyllabusByTeacher";
	}
	/**
	 * 批量删除
	 * 
	 * @param syllabusIds
	 * @return
	 */
	@RequestMapping("/deleteSyllabusByIds")
	public String delTeacherByIds(HttpServletRequest request,Model model,Integer[] syllabusIds) {
		syllabusServiceImpl.delSyllabusByIds(syllabusIds);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/syllabus/list";
	}
	/**
	 * 批量删除（通过班主任）
	 * 
	 * @param syllabusIds
	 * @return
	 */
	@RequestMapping("/deleteSyllabusByIdsByClassesTeacher")
	public String delTeacherByIdsByClassesTeacher(HttpServletRequest request,Model model,Integer[] syllabusIds) {
		syllabusServiceImpl.delSyllabusByIds(syllabusIds);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/syllabus/getClassesSyllabusByTeacher";
	}
	 /**
	 * 导出
	 * @return
	 * @throws IOException
	 */
	 @RequestMapping("/exportSyllabus")
	 public String export(Integer mould,HttpServletRequest
	 request,HttpServletResponse response) throws IOException {
	
	 //准备需要遍历的课表
	 List<CourseDateVo> syllabusList = syllabusServiceImpl.getAllsyllabus();
	 //准备表格第一行显示的域
	 List<String> fields = new ArrayList<>();
	 fields.add("日期");
	 fields.add("班级");
	 fields.add("08.30-11.30");
	 fields.add("13.30-16.30");
	 fields.add("17.30-20.30");
	 DownUtils.exportReport(mould, request, response, null, "课表安排", syllabusList, fields, null, null, null, null, null, null, null);
	 return null;
	 }
	 /**
	  * 导出班主任所带班级的所有课表
	  * @return
	  * @throws IOException
	  */
	 @RequestMapping("/exportSyllabusByClassesTeacher")
	 public String exportSyllabusByClassesTeacher(Integer mould,HttpServletRequest
			 request,HttpServletResponse response) throws IOException {
		 //准备需要遍历的课表
		 Teacher teacher = (Teacher)request.getSession().getAttribute("user");
			Integer teacherId = teacher.getTeacherId();
			List<Classes> classesList = classesServiceImpl.getClassesByTeacherId(teacherId);
			
			ArrayList<CourseDateVo> syllabusList = new ArrayList<>();
			for (Classes classes : classesList) {
				// 查询出课表信息
				List<Syllabus> syllList = classesServiceImpl.getSyllabusByClassesId(classes.getClassesId());
				for (Syllabus syll : syllList) {
					Course amCourse=null; 
					Course pmCourse=null; 
					Course niCourse=null; 
					CourseDateVo courseDateVo = new CourseDateVo();
					// 封装日期
					courseDateVo.setDate(syll.getWeek());
					// 封装课表id
					courseDateVo.setSyllabusId(syll.getSyllabusId());
					// 封装上午课程
					if(syll.getAmfirst().trim().length()!=0){
						amCourse = classesServiceImpl.getCourseByCourseId(Integer.parseInt(syll.getAmfirst()));
						courseDateVo.setAmCourse(amCourse);
					}
					// 封装下午课程
					if(syll.getPmfirst().trim().length()!=0){
						pmCourse = classesServiceImpl.getCourseByCourseId(Integer.parseInt(syll.getPmfirst()));
						courseDateVo.setPmCourse(pmCourse);
					}
					// 封装晚上课程
					if(syll.getNight().trim().length()!=0){
						niCourse = classesServiceImpl.getCourseByCourseId(Integer.parseInt(syll.getNight()));
						courseDateVo.setNiCourse(niCourse);
					}
					// 封装上午老师
					if(amCourse!=null){
						Teacher amTeacher = teacherServiceImpl.getTeacherBYid(amCourse.getTeacherId());
						courseDateVo.setAmTeacher(amTeacher);
					}
					// 封装下午老师
					if(pmCourse!=null){
						Teacher pmTeacher = teacherServiceImpl.getTeacherBYid(pmCourse.getTeacherId());
						courseDateVo.setPmTeacher(pmTeacher);
					}
					// 封装晚上老师
					if(niCourse!=null){
						Teacher niTeacher = teacherServiceImpl.getTeacherBYid(niCourse.getTeacherId());
						courseDateVo.setNiTeacher(niTeacher);
					}
					//封装班级
					courseDateVo.setClasses(classes);
					
					// 添加到list集合中
					syllabusList.add(courseDateVo);
				}
			}
		 //准备表格第一行显示的域
		 List<String> fields = new ArrayList<>();
		 fields.add("日期");
		 fields.add("班级");
		 fields.add("08.30-11.30");
		 fields.add("13.30-16.30");
		 fields.add("17.30-20.30");
		 DownUtils.exportReport(mould, request, response, null, "课表安排", syllabusList, fields, null, null, null, null, null, null, null);
		 return null;
	 }
}
