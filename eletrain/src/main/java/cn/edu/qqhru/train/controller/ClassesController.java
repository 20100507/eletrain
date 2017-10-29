package cn.edu.qqhru.train.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.Course;
import cn.edu.qqhru.train.pojo.Student;
import cn.edu.qqhru.train.pojo.Syllabus;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.service.ClassesService;
import cn.edu.qqhru.train.service.CourseService;
import cn.edu.qqhru.train.service.StuService;
import cn.edu.qqhru.train.service.SyllabusService;
import cn.edu.qqhru.train.service.TeacherService;
import cn.edu.qqhru.train.utils.ClassesStuDownUtils;
import cn.edu.qqhru.train.utils.DownUtils;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.ClassesVo;
import cn.edu.qqhru.train.vo.CourseDateVo;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import sun.misc.BASE64Encoder;

/**
 * 班级Controller
 * 
 * @author 张继富
 */
@Controller
@RequestMapping("/classes")
public class ClassesController {
	@Autowired
	private StuService stuService;
	@Autowired
	private CourseService courseServiceImpl;
	@Autowired
	private ClassesService classesServiceImpl;
	@Autowired
	private TeacherService teacherServiceImpl;
	@Autowired
	private StuService stuServiceImpl;
	@Autowired
	private SyllabusService syllabusServiceImpl;

	/**
	 * 分页查询教师列表(状态为正常)
	 * 
	 * @param model
	 * @param page
	 * @param rows
	 * @param type
	 *            0表示正常，1表示结课
	 * @return
	 */
	@RequestMapping("/list")
	public String toIndex0(
			Model model,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "type", defaultValue = "0") int type,
			HttpServletRequest request) {
		PageBean<Classes> pageBean = classesServiceImpl.getAllClasses(page, rows, type);
		model.addAttribute("pageBean", pageBean);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		model.addAttribute("path", "list?type="+type+"&menuId="+menuId);
		if (type == 0) {
			return "classes-list";
		} else {
			return "classes-list1";
		}
	}

	/**
	 * 班级编号验证
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/classesNoContentChecked")
	public String classesNoContentChecked(HttpServletResponse response, String classesNo, Integer type)
			throws IOException {
		String status = "allow";
		if (type == 0) {
			List<Classes> allClasses = classesServiceImpl.getAllClasses();
			if (allClasses != null && allClasses.size() > 0) {
				for (Classes classes : allClasses) {
					if (classes.getClassesNo().contentEquals(classesNo)) {
						status = "repeat";
						break;
					}
				}
			}
		} else {
			List<Classes> allClasses = classesServiceImpl.getAllClasses();
			if (allClasses != null && allClasses.size() > 0) {
				for (Classes classes : allClasses) {
					if (classes.getCname().contentEquals(classesNo)) {
						status = "repeat";
						break;
					}
				}
			}
		}
		PrintWriter writer = response.getWriter();
		writer.print(status);
		return null;
	}

	/**
	 * 跳转到添加班级页面
	 * 
	 * @return
	 */
	@RequestMapping("/addClassesUI")
	public String toAddClassesUI(Model model, HttpServletRequest request) {
		// 查询所有的教师作为授课教授的下拉列表内容
		/*
		 * List<Teacher> allTeacher = teacherServiceImpl.getAllTeacher();
		 * List<Teacher> teacherList = new ArrayList<>(); for (Teacher teacher :
		 * allTeacher) { if(2==teacher.getIdentify()){ teacherList.add(teacher);
		 * } } model.addAttribute("teacherList", teacherList);
		 */
		// 查询所有的班主任
		List<Teacher> classesTeacherList = teacherServiceImpl.getClassesTeacher();
		model.addAttribute("classesTeacherList", classesTeacherList);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "classes-publicUI";
	}

	/**
	 * 添加班级
	 * 
	 * @param teacher
	 * @return
	 */
	@RequestMapping("/addClasses")
	public String addClasses(HttpServletRequest request, Model model, Classes classes, Integer[] teacherIds,
			String startTime, String endTime) {
		classesServiceImpl.addClasses(classes, teacherIds, startTime, endTime);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/classes/list";

	}

	/**
	 * 跳转到通过班主任添加班级页面
	 * 
	 * @return
	 */
	// @RequestMapping("/addClassesUIByClassesTeacher")
	// public String toAddClassesUIByClassesTeacher(HttpServletRequest
	// request,Model model) {
	// //查询所有的教师作为授课教授的下拉列表内容
	// List<Teacher> allTeacher = teacherServiceImpl.getAllTeacher();
	// List<Teacher> teacherList = new ArrayList<>();
	// for (Teacher teacher : allTeacher) {
	// if(2==teacher.getIdentify()){
	// teacherList.add(teacher);
	// }
	// }
	// model.addAttribute("teacherList", teacherList);
	// //查询所有的班主任
	//// List<Teacher> classesTeacherList =
	// teacherServiceImpl.getClassesTeacher();
	// //获得登录用户的班主任身份
	// Teacher teacher = (Teacher)request.getSession().getAttribute("user");
	// ArrayList<Teacher> classesTeacherList = new ArrayList<>();
	// classesTeacherList.add(teacher);
	// model.addAttribute("classesTeacherList", classesTeacherList);
	// String menuId = request.getParameter("menuId");
	// model.addAttribute("menuId", menuId);
	// return "classesTeacher-publicUI";
	// }
	// /**
	// * 通过班主任添加班级
	// * @param teacher
	// * @return
	// */
	// @RequestMapping("/addClassesByClassesTeacher")
	// public String addClassesByClassesTeacher(HttpServletRequest request,Model
	// model,Classes classes, Integer[] teacherIds,String startTime,String
	// endTime) {
	// classesServiceImpl.addClasses(classes, teacherIds,startTime,endTime);
	// String menuId = request.getParameter("menuId");
	// model.addAttribute("menuId", menuId);
	// return "redirect:/classes/getClassesByClassesTeacherId";
	// }
	/**
	 * 通过id删除班级
	 * 
	 * @param classesId
	 * @return
	 */
	@RequestMapping("/deleteClasses")
	public String deleteClasses(HttpServletRequest request, Model model, Integer classesId) {
		//班级结课
		classesServiceImpl.deleteClasses(classesId);
		//学生毕业
		List<Integer> classesIds = new ArrayList<>();
		classesIds.add(classesId);
		classesServiceImpl.deleteStudent(classesIds);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/classes/list";
	}

	/**
	 * 通过id删除班级（班主任）
	 * 
	 * @param classesId
	 * @return
	 */
	// @RequestMapping("/deleteClassesByClassesTeacher")
	// public String deleteClassesByClassesTeacher(HttpServletRequest
	// request,Model model,Integer classesId) {
	// classesServiceImpl.deleteClasses(classesId);
	// String menuId = request.getParameter("menuId");
	// model.addAttribute("menuId", menuId);
	// return "redirect:/classes/getClassesByTeacherId";
	// }
	/**
	 * 通过id恢复班级
	 * 
	 * @param classesId
	 * @return
	 */
	@RequestMapping("/updClasses")
	public String updClasses(HttpServletRequest request, Model model, Integer classesId) {
		//恢复班级
		classesServiceImpl.updClasses(classesId);
		//恢复学生
		classesServiceImpl.updStudent(Arrays.asList(classesId));
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/classes/list?type=1";
	}

	/**
	 * 以身份证号搜索学生
	 * 
	 * @throws UnsupportedEncodingException
	 */
	// @RequestMapping("/searchByLoginnameByClassesTeacher")
	// public String searchByLoginnameByClassesTeacher(Model model,String
	// loginname,@RequestParam(value="page",defaultValue="1") int page,
	// @RequestParam(value="rows",defaultValue="10")int rows) throws
	// UnsupportedEncodingException {
	// try {
	// loginname = URLDecoder.decode(loginname,"UTF-8");
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// }
	// PageBean<Student> pageBean = stuService.getStu(loginname, page, rows);
	//
	// List<Student> list0 = new ArrayList<>();
	// for (Student student : pageBean.getList()) {
	// if(0==student.getIsfinish()){
	// list0.add(student);
	// }
	// }
	// pageBean.setList(list0);
	// model.addAttribute("pageBean", pageBean);
	// return "classesTeacher-addOrDelStuList";
	// }
	/**
	 * 以身份证号搜索学生
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/searchByLoginname")
	public String searchByLoginname(
			HttpServletRequest request, 
			Model model,
			String loginname,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows) throws UnsupportedEncodingException {
		try {
			loginname = URLDecoder.decode(loginname, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		PageBean<Student> pageBean = stuService.getStu(loginname, page, rows);

		List<Student> list0 = new ArrayList<>();
		for (Student student : pageBean.getList()) {
			if (0 == student.getIsfinish()) {
				list0.add(student);
			}
		}
		pageBean.setList(list0);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", loginname);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "classes-addOrDelStuList";
	}

	/**
	 * 跳转到编辑班级页面
	 * 
	 * @param model
	 * @param teacherId
	 * @return
	 */
	@RequestMapping("/updateClassesUI")
	public String updateClassesUI(HttpServletRequest request, Integer type, Model model, Integer classesId) {
		model.addAttribute("type", type);
		// 查询出班级信息，作为回显数据
		Classes classes = classesServiceImpl.getClassesBYid(classesId);
		model.addAttribute("classes", classes);
		String[] split = classes.getStarttime().split("~");
		model.addAttribute("startTime", split[0]);
		model.addAttribute("endTime", split[1]);
		// 查询出班级的教师信息作为回显数据
		List<Teacher> exitTeacherList = teacherServiceImpl.getTeacherByClassesId(classesId);
		/*
		 * ArrayList<Object> tnameList = new ArrayList<>(); for (Teacher teacher
		 * : exitTeacherList) { tnameList.add(teacher.getUsername()); }
		 * model.addAttribute("tnameList", tnameList);
		 */
		// 查询出所有的教师(除去班主任)
		/*
		 * List<Teacher> allTeacher = teacherServiceImpl.getAllTeacher();
		 * List<Teacher> teacherList = new ArrayList<>(); for (Teacher teacher :
		 * allTeacher) { if(2==teacher.getIdentify()){ teacherList.add(teacher);
		 * } } model.addAttribute("teacherList", teacherList);
		 */ // 查询出班级的班主任作为回显信息
		ArrayList<Object> ctnameList = new ArrayList<>();
		for (Teacher teacher : exitTeacherList) {
			if (teacher.getIdentify() == 6) {
				ctnameList.add(teacher.getUsername());
			}
		}
		model.addAttribute("ctnameList", ctnameList);
		// 查询出所有的班主任
		List<Teacher> classesTeacherList = teacherServiceImpl.getClassesTeacher();
		model.addAttribute("classesTeacherList", classesTeacherList);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "classes-publicUI";
	}

	/**
	 * 编辑班级
	 * 
	 * @param teacher
	 * @return
	 */
	@RequestMapping("/updateClasses")
	public String updateClasses(HttpServletRequest request, Integer type, Model model, Classes classes,
			Integer[] teacherIds, String startTime, String endTime) {
		classesServiceImpl.updateClasses(classes, teacherIds, startTime, endTime);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		if (0 == type) {
			return "redirect:/classes/list?type=0";
		} else {
			return "redirect:/classes/list?type=1";
		}
	}
	/**
	 * 跳转到通过班主任编辑班级的页面
	 * 
	 * @param model
	 * @param teacherId
	 * @return
	 */
	// @RequestMapping("/updateClassesUIByClassesTeacher")
	// public String updateClassesUIByClassesTeacher(HttpServletRequest
	// request,Model model, Integer classesId) {
	// // 查询出班级信息，作为回显数据
	// Classes classes = classesServiceImpl.getClassesBYid(classesId);
	// model.addAttribute("classes", classes);
	// String[] split = classes.getStarttime().split("~");
	// model.addAttribute("startTime", split[0]);
	// model.addAttribute("endTime", split[1]);
	// String menuId = request.getParameter("menuId");
	// model.addAttribute("menuId", menuId);
	// Teacher teacher = (Teacher)request.getSession().getAttribute("user");
	// ArrayList<Teacher> classesTeacherList = new ArrayList<>();
	// classesTeacherList.add(teacher);
	// model.addAttribute("classesTeacherList", classesTeacherList);
	// /*// 查询出班级的教师信息作为回显数据
	// List<Teacher> exitTeacherList =
	// teacherServiceImpl.getTeacherByClassesId(classesId);
	// ArrayList<Object> tnameList = new ArrayList<>();
	// for (Teacher teacher : exitTeacherList) {
	// tnameList.add(teacher.getUsername());
	// }
	// model.addAttribute("tnameList", tnameList);
	// // 查询出所有的教师(除去班主任)
	// List<Teacher> allTeacher = teacherServiceImpl.getAllTeacher();
	// List<Teacher> teacherList = new ArrayList<>();
	// for (Teacher teacher : allTeacher) {
	// if(2==teacher.getIdentify()){
	// teacherList.add(teacher);
	// }
	// }
	// model.addAttribute("teacherList", teacherList);
	// //查询出班级的班主任作为回显信息
	// ArrayList<Object> ctnameList = new ArrayList<>();
	// for (Teacher teacher : exitTeacherList) {
	// if(teacher.getIdentify()==6){
	// ctnameList.add(teacher.getUsername());
	// }
	// }
	// model.addAttribute("ctnameList", ctnameList);
	// //查询出所有的班主任
	// List<Teacher> classesTeacherList =
	// teacherServiceImpl.getClassesTeacher();
	// model.addAttribute("classesTeacherList", classesTeacherList);
	// */
	// return "classesTeacher-publicUI";
	// }

	/**
	 * 通过班主任编辑班级
	 * 
	 * @param teacher
	 * @return
	 */
	// @RequestMapping("/updateClassesByClassesTeacher")
	// public String updateTeacherByClassesTeacher(HttpServletRequest
	// request,Model model,Classes classes, Integer[] teacherIds,String
	// startTime,String endTime) {
	// classesServiceImpl.updateClasses(classes, teacherIds,startTime,endTime);
	// String menuId = request.getParameter("menuId");
	// model.addAttribute("menuId", menuId);
	// return "redirect:/classes/getClassesByClassesTeacherId";
	// }
	//
	/**
	 * 跳转到添加或删除学生的页面
	 * 
	 * @param classesId
	 * @return
	 */
	@RequestMapping("/insertOrDeleteUI")
	public String insertOrDeleteUI(HttpServletRequest request, Integer type, Integer adType, Model model,
			Integer classesId, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows) {
		model.addAttribute("type", type);
		model.addAttribute("adType", adType);
		model.addAttribute("classesId", classesId);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		// 添加学生
		if (adType == 0) {
			// 分页查询不属于本班级所有的学生信息
			PageBean<Student> pageBean = classesServiceImpl.getAllExecuteStu(classesId, page, rows);
			model.addAttribute("pageBean", pageBean);
		} else {
			// 查询班级已有的学生
			PageBean<Student> pageBean = classesServiceImpl.getAllStuByClassesId(classesId, page, rows);
			model.addAttribute("pageBean", pageBean);
		}
		return "classes-addOrDelStuList";
	}

	/**
	 * 添加给班级学生
	 * 
	 * @param classesId
	 *            班级的id
	 * @param ids
	 *            学生的id数组
	 * @return
	 */
	@RequestMapping("/insertOrDelete")
	public String insertOrDelete(HttpServletRequest request, Integer type, Integer adType, Model model,
			Integer classesId, Integer[] ids) {
		classesServiceImpl.addOrDelStudentToClasses(classesId, ids, adType);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/classes/insertOrDeleteUI?adType="+adType+"&type="+type+"&classesId="+classesId;
	
	}
	/**
	 * 跳转到通过班主任添加或删除学生的页面
	 * 
	 * @param classesId
	 * @return
	 */
	// @RequestMapping("/insertOrDeleteUIByClassesTeacher")
	// public String insertOrDeleteUIByClassesTeacher(HttpServletRequest
	// request,Model model,Integer type, Integer classesId,
	// @RequestParam(value = "page", defaultValue = "1") int page,
	// @RequestParam(value = "rows", defaultValue = "10") int rows) {
	// // 添加classesId
	// model.addAttribute("classesId", classesId);
	// model.addAttribute("type", type);
	// String menuId = request.getParameter("menuId");
	// model.addAttribute("menuId", menuId);
	// //添加学生
	// if(type==0){
	// // 分页查询不属于本班级所有的学生信息
	// PageBean<Student> pageBean =
	// classesServiceImpl.getAllExecuteStu(classesId,page, rows);
	// model.addAttribute("pageBean", pageBean);
	// }
	// else{
	// // 查询班级已有的学生
	// PageBean<Student> pageBean =
	// classesServiceImpl.getAllStuByClassesId(classesId,page, rows);
	// model.addAttribute("pageBean", pageBean);
	// }
	// return "classesTeacher-addOrDelStuList";
	// }

	/**
	 * 添加给班级学生
	 * 
	 * @param classesId
	 *            班级的id
	 * @param ids
	 *            学生的id数组
	 * @return
	 */
	// @RequestMapping("/insertOrDeleteByClassesTeacher")
	// public String insertOrDeleteByClassesTeacher(HttpServletRequest
	// request,Integer type,Model model,Integer classesId, Integer[] ids) {
	// classesServiceImpl.addOrDelStudentToClasses(classesId, ids,type);
	// String menuId = request.getParameter("menuId");
	// model.addAttribute("menuId", menuId);
	// return "redirect:/classes/getClassesByClassesTeacherId";
	// }
	/**
	 * 通过教师id查询相关的Classes
	 * 
	 * @param teacherId
	 * @param model
	 * @return
	 */
	@RequestMapping("/getClassesByTeacherId")
	public String getClassesByTeacherId(
			HttpServletRequest request, 
			Model model,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows) {
		Teacher teacher = (Teacher) request.getSession().getAttribute("user");
		Integer teacherId = teacher.getTeacherId();

		PageBean<Classes> pageBean = classesServiceImpl.getClassesByLectureTeacherId(teacherId,page,rows);
		model.addAttribute("pageBean", pageBean);

		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		model.addAttribute("path", "getClassesByTeacherId?menuId="+menuId);
		return "teacher-classes-list";
		/*if (2 == teacher.getIdentify()) {
			return "teacher-classes-list";
		} else {
			return "classesTeacher-classesList";
		}*/
	}

	/**
	 * 通过班主任id查询相关的Classes
	 * 
	 * @param teacherId
	 * @param model
	 * @return
	 */
	@RequestMapping("/getClassesByClassesTeacherId")
	public String getClassesByClassesTeacherId(
			Model model,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "type", defaultValue = "0") int type,
			HttpServletRequest request) {
		Teacher teacher = (Teacher) request.getSession().getAttribute("user");
		Integer teacherId = teacher.getTeacherId();

		PageBean<Classes> pageBean = classesServiceImpl.getClassesByClassesTeacherId(page,rows,null,null,teacherId,type);
		model.addAttribute("pageBean", pageBean);

		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		model.addAttribute("path", "getClassesByClassesTeacherId?type="+type);

		return "classesTeacher-classesList";
	}

	/**
	 * 查看班级详情信息
	 * 
	 * @param teacherId
	 * @return
	 */
	@RequestMapping("/lookInfo")
	public String lookInfo(HttpServletRequest request, Integer classesId, Integer type, Model model) {
		// 查询班级信息
		Classes classes = classesServiceImpl.getClassesBYid(classesId);
		model.addAttribute("classes", classes);
		// 查询出班级的授课教师信息
		/*
		 * List<Teacher> exitTeacherList =
		 * teacherServiceImpl.getTeacherByClassesId(classesId);
		 * ArrayList<Object> tnameList = new ArrayList<>(); for (Teacher teacher
		 * : exitTeacherList) { tnameList.add(teacher.getUsername()); }
		 */
		List<Integer> teacherIds = new ArrayList<>();
		List<Syllabus> syllabusList = syllabusServiceImpl.getSyllabusByClassesId(classesId);
		if(syllabusList!=null&&syllabusList.size()>0){
			for (Syllabus syllabus : syllabusList) {
				if(syllabus.getAmfirst().trim().length()!=0){
					Course amCourse = courseServiceImpl.getCourseById(Integer.valueOf(syllabus.getAmfirst()));
					teacherIds.add(amCourse.getTeacherId());
				}
				if(syllabus.getPmfirst().trim().length()!=0){
					Course pmCourse = courseServiceImpl.getCourseById(Integer.valueOf(syllabus.getPmfirst()));
					teacherIds.add(pmCourse.getTeacherId());
				}
				if(syllabus.getNight().trim().length()!=0){
					Course niCourse = courseServiceImpl.getCourseById(Integer.valueOf(syllabus.getNight()));
					teacherIds.add(niCourse.getTeacherId());
				}
			}
		}
		List<Teacher> teacherList = teacherServiceImpl.getTeacherByteacherIds(teacherIds);
		model.addAttribute("teacherList", teacherList);

		// 查询出班级的班主任作为回显信息
		List<Teacher> exitTeacherList = teacherServiceImpl.getTeacherByClassesId(classesId);
		String classesTeacher = null;
		for (Teacher teacher : exitTeacherList) {
			if (teacher.getIdentify() == 6) {
				classesTeacher = teacher.getUsername();
			}
		}
		model.addAttribute("classesTeacher", classesTeacher);
		// 查询班级实际人数
		Integer count = classesServiceImpl.getCountByCid(classesId);
		model.addAttribute("count", count);
		/*
		 * // 查询教师信息 List<Teacher> teacherList =
		 * classesServiceImpl.getTeacherByCid(classesId);
		 * model.addAttribute("teacherList", teacherList);
		 */
		// 查询出学生信息
		List<Student> studentList = classesServiceImpl.getStudentByCid(classesId);
		model.addAttribute("studentList", studentList);
		// 查询出课表信息
		List<Syllabus> syllList = classesServiceImpl.getSyllabusByClassesId(classesId);
		ArrayList<CourseDateVo> cdvList = new ArrayList<>();
		
		for (Syllabus syll : syllList) {
			Course amCourse=null; 
			Course pmCourse=null; 
			Course niCourse=null; 
			CourseDateVo courseDateVo = new CourseDateVo();
			// 封装日期
			courseDateVo.setDate(syll.getWeek());
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
			// 添加到list集合中
			cdvList.add(courseDateVo);
		}
		model.addAttribute("cdvList", cdvList);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		// 查询出培训信息
		if (0 == type) {
			return "classes-info";
		} else if (1 == type) {
			return "classesTeacher-classesInfo";
		} else {
			return "teacher-classes-info";
		}
	}

	/**
	 * 通过索引查询班级
	 * 
	 * @param index
	 * @param model
	 * @return
	 */
	@RequestMapping("/searchClasses")
	public String searchClasses(
			Model model,
			String index,
			Integer searchWay,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			HttpServletRequest request) {
		// 0表示班级编号
		// 1表示班级名称
		PageBean<Classes> pageBean;
		if (searchWay == 0) {
			// 按班级编号查
			pageBean = classesServiceImpl.getClassesByClassesNo(page, rows, index);
		} else {
			// 按班级名称查
			pageBean = classesServiceImpl.getClassesByClassesName(page, rows, index);
		}
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("path", "searchClasses?menuId="+menuId+"&index="+index+"&searchWay"+searchWay);
		return "classes-list";
	}
	
	/**
	 * 班主任通过索引查询班级
	 * 
	 * @param index
	 * @param model
	 * @return
	 */
	@RequestMapping("/searchClassesClassesTeacher")
	public String searchClsearchClassesClassesTeacherasses(
			Model model,
			String index,
			Integer searchWay,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			HttpServletRequest request) {
		Teacher teacher = (Teacher) request.getSession().getAttribute("user");
		Integer teacherId = teacher.getTeacherId();
		// 0表示班级编号
		// 1表示班级名称
		PageBean<Classes> pageBean;
		pageBean = classesServiceImpl.getClassesByClassesTeacherId(page, rows, index,searchWay,teacherId,null);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("path", "searchClassesClassesTeacher?searchWay="+searchWay+"&index="+index);
		return "classesTeacher-classesList";
	}

	/**
	 * 批量删除班级
	 * 
	 * @param classesIds
	 * @return
	 */
	@RequestMapping("/delClassesByIds")
	public String delClassesByIds(Integer[] classesIds) {
		if (classesIds != null) {
			classesServiceImpl.delClassesByIds(classesIds);
			List<Integer> classesIdsList = new ArrayList<>(Arrays.asList(classesIds));
			classesServiceImpl.deleteStudent(classesIdsList);
		} else {
			return "redirect:/classes/list";
		}
		return "redirect:/classes/list";
	}

	/**
	 * 批量删除班级(班主任)
	 * 
	 * @param classesIds
	 * @return
	 */
	// @RequestMapping("/delClassesByIdsByClassesTeacher")
	// public String delClassesByIdsByClassesTeacher(HttpServletRequest
	// request,Model model,Integer[] classesIds) {
	// classesServiceImpl.delClassesByIds(classesIds);
	// String menuId = request.getParameter("menuId");
	// model.addAttribute("menuId", menuId);
	// return "redirect:/classes/getClassesByTeacherId";
	// }
	/**
	 * 批量恢复班级
	 * 
	 * @param classesIds
	 * @return
	 */
	@RequestMapping("/updClassesByIds")
	public String updClassesByIds(HttpServletRequest request, Model model, Integer[] classesIds) {
		//恢复班级
		classesServiceImpl.updClassesByIds(classesIds);
		//恢复学生
		classesServiceImpl.updStudent(Arrays.asList(classesIds));
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/classes/list?tyep=1";
	}

	/**
	 * 导出教师信息
	 * 
	 * @param mould
	 * @param classesId
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/exportTeacher")
	public String exportTeacher(Integer mould, Integer classesId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 准备需要遍历的Teacher
		Classes classesBYid = classesServiceImpl.getClassesBYid(classesId);
		List<Integer> teacherIds = new ArrayList<>();
		List<Syllabus> syllabusList = syllabusServiceImpl.getSyllabusByClassesId(classesId);
		for (Syllabus syllabus : syllabusList) {
			if(syllabus.getAmfirst().trim().length()!=0){
				Course amCourse = courseServiceImpl.getCourseById(Integer.valueOf(syllabus.getAmfirst()));
				teacherIds.add(amCourse.getTeacherId());
			}
			if(syllabus.getPmfirst().trim().length()!=0){
				Course pmCourse = courseServiceImpl.getCourseById(Integer.valueOf(syllabus.getPmfirst()));
				teacherIds.add(pmCourse.getTeacherId());
			}
			if(syllabus.getNight().trim().length()!=0){
				Course niCourse = courseServiceImpl.getCourseById(Integer.valueOf(syllabus.getNight()));
				teacherIds.add(niCourse.getTeacherId());
			}
		}
		List<Teacher> teacherList = teacherServiceImpl.getTeacherByteacherIds(teacherIds);
		// 准备表格第一行显示的域
		List<String> fields = new ArrayList<>();
		fields.add("工号");
		fields.add("用户名");
		fields.add("职称");
		fields.add("性别");
		fields.add("电话");
		fields.add("地址");
		fields.add("学历");
		fields.add("身份证");
		fields.add("状态");
		fields.add("教师类别");
		fields.add("创建日期");
		fields.add("更新日期");
		DownUtils.exportReport(mould, request, response, null, "教师信息表_"+classesBYid.getCname(), null, fields, teacherList, null, null, null,
				null, null, null);
		return null;
	}

	/**
	 * 导出班级信息
	 */
	@RequestMapping("/exportClasses")
	public ModelAndView exportClasses(Integer mould, String type, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 查询出班级信息
		List<Classes> allClasses = classesServiceImpl.getAllClasses();
		List<ClassesVo> classesListByStatus = new ArrayList<>();
		// 准备表格第一行显示的域
		List<String> fields = new ArrayList<>();
		fields.add("班级编号");
		fields.add("班级名称");
		fields.add("班级状态");
		fields.add("报到时间");
		fields.add("授课时间段");
		fields.add("班主任");
		fields.add("班级人数");
		if (type.contentEquals("z")) {
			// 获得在课列表
			for (Classes classes : allClasses) {
				if (classes.getCapacity() == 0) {
					// 设置班级
					ClassesVo classesVo = new ClassesVo();
					classesVo.setClasses(classes);
					// 设置人数
					Integer classesCount = classesServiceImpl.getCountByCid(classes.getClassesId());
					classesVo.setClassesCount(classesCount);
					// 设置班主任
					List<Teacher> teacherByClassesId = teacherServiceImpl.getTeacherByClassesId(classes.getClassesId());
					if (teacherByClassesId != null && teacherByClassesId.size() > 0) {
						for (Teacher teacher : teacherByClassesId) {
							if (teacher.getIdentify() == 6) {
								classesVo.setClassesTeacher(teacher);
							} else {
								Teacher teacherExc = new Teacher();
								teacher.setUsername("找不到班主任");
								classesVo.setClassesTeacher(teacherExc);
							}
						}
					} else {
						Teacher teacher = new Teacher();
						teacher.setUsername("找不到班主任");
						classesVo.setClassesTeacher(teacher);
					}
					// 添加到集合中
					classesListByStatus.add(classesVo);
				}
			}
		} else if (type.contentEquals("l")) {
			// 获得结课列表
			for (Classes classes : allClasses) {
				if (classes.getCapacity() == 1) {
					// 设置班级
					ClassesVo classesVo = new ClassesVo();
					classesVo.setClasses(classes);
					// 设置人数
					Integer classesCount = classesServiceImpl.getCountByCid(classes.getClassesId());
					classesVo.setClassesCount(classesCount);
					// 设置班主任
					List<Teacher> teacherByClassesId = teacherServiceImpl.getTeacherByClassesId(classes.getClassesId());
					if (teacherByClassesId != null && teacherByClassesId.size() > 0) {
						for (Teacher teacher : teacherByClassesId) {
							if (teacher.getIdentify() == 6) {
								classesVo.setClassesTeacher(teacher);
							} else {
								Teacher teacherExc = new Teacher();
								teacher.setUsername("找不到班主任");
								classesVo.setClassesTeacher(teacherExc);
							}
						}
					} else {
						Teacher teacher = new Teacher();
						teacher.setUsername("找不到班主任");
						classesVo.setClassesTeacher(teacher);
					}
					// 添加到集合中
					classesListByStatus.add(classesVo);
				}
			}
		}
		ClassesStuDownUtils.exportClasses(mould, request, response, "班级信息表", fields, null, classesListByStatus);
		return null;
	}

	/**
	 * 导出学生信息
	 */
	@RequestMapping("/exportStudent")
	public ModelAndView exportStudent(Integer mould, Integer classesId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 查询出学生信息
		List<Student> studentList = classesServiceImpl.getStudentByCid(classesId);
		// 查询出班级信息
		Classes classes = classesServiceImpl.getClassesBYid(classesId);
//		String bdTime = classes.getCreatetime();
		// 准备表格第一行显示的域
		List<String> fields = new ArrayList<>();
		fields.add("序号");
		fields.add("姓名");
		fields.add("性别");
		fields.add("身份证");
		fields.add("工作单位");
		fields.add("报道时间");
		fields.add("电话");
		fields.add("员工号");
		fields.add("备注");
		ClassesStuDownUtils.exportReport(mould, request, response, classes, "培训人员信息表_"+classes.getCname(), fields, studentList);
		return null;
	}

	/**
	 * 导出考勤表
	 */
	@RequestMapping("/exportCheAttance")
	public ModelAndView exportCheAttance(
			Integer mould,
			Integer classesId,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//查询出班级信息
		Classes classesById = classesServiceImpl.getClassesBYid(classesId);
		Classes classes = new Classes();
		if(classesById!=null){
			classes=classesById;
		}
		//查询出班主任
		List<Teacher> classesTeacherById = teacherServiceImpl.getTeacherByClassesId(classes.getClassesId());
		Teacher classesTeacher = new Teacher();
		if(classesTeacherById!=null&&classesTeacherById.size()>0){
			classesTeacher = classesTeacherById.get(0);
		}
		// 查询出学生信息
		List<Student> studentList = classesServiceImpl.getStudentByCid(classesId);
		if(mould==1){
			DownUtils.exportCheAttendanceByxlsx(mould, response, request, studentList,classesTeacher,classes);
		}
		else{
			DownUtils.exportCheAttendanceByxls(mould, response, request, studentList,classesTeacher,classes);
		}
		return null;
	}

	/**
	 * 导出班级课表
	 * 
	 * @param classesId
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 * @throws MalformedTemplateNameException
	 * @throws TemplateNotFoundException
	 */
	@RequestMapping("/exportClassesSyllabus")
	public String exportClassesSyllabus(Integer classesId, HttpServletRequest request, HttpServletResponse response)
			throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		// ==========创建一个map集合==========
		Map<String, Object> map = new HashMap<>();
		// ==========查询出课表信息==========
		List<CourseDateVo> cdvList = classesServiceImpl.getPackageSyllabusByclassesId(classesId);
		// ==========查询出班级信息==========
		Classes classes = classesServiceImpl.getClassesBYid(classesId);
		String cname = classes.getCname();
		map.put("cname", cname);
		// ==========封装到map集合==========
		map.put("cdvList", cdvList);
		// ==========下载到客户端==========
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setDirectoryForTemplateLoading(
				new File(request.getSession().getServletContext().getRealPath("/download")));
		Template template = configuration.getTemplate("课程表.ftl");
		// ============选择下载路径===========
		ServletOutputStream outputStream = response.getOutputStream();
		Writer out = new BufferedWriter(new OutputStreamWriter(outputStream));
		try {
			String fileName1 = "课程表_"+cname+".doc";
			response.reset(); // 清除缓冲区中的任何数据以及状态代码和标头
			String header = request.getHeader("user-agent");
			if (header.contains("Firefox")) {
				fileName1 = "=?UTF-8?B?" + new BASE64Encoder().encode(fileName1.getBytes("utf-8")) + "?=";
			} else {
				fileName1 = URLEncoder.encode(fileName1, "UTF-8");
			}
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName1);
			response.setContentType("application/octet-stream;charset=UTF-8");
			template.process(map, out);
		} catch (Exception e) {
			out.flush();
			out.close();
			outputStream.flush();
			outputStream.close();
		}
		out.flush();
		out.close();
		outputStream.flush();
		outputStream.close();
		return null;
	}
	/**导出考勤表word形式
	 * 
	 * @param classesId
	 * @param request
	 * @param response
	 * @return
	 * @throws TemplateNotFoundException
	 * @throws MalformedTemplateNameException
	 * @throws ParseException
	 * @throws IOException
	 */
/*	@RequestMapping("/exportCheAttance")
	public String exportClassesAttendance(
			Integer classesId,
			HttpServletRequest request, 
			HttpServletResponse response)throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		// ==========创建一个map集合==========
		Map<String, Object> map = new HashMap<>();
		// ==========查询出班级信息==========
		//查询出班级信息
		Classes classesById = classesServiceImpl.getClassesBYid(classesId);
		Classes classes = new Classes();
		if(classesById!=null){
			classes=classesById;
			String stTime = classes.getStarttime().split("~")[0];
			String enTime = classes.getStarttime().split("~")[1];
			String  start=stTime.split("-")[0]+"年"+stTime.split("-")[1]+"月"+stTime.split("-")[2]+"日";
			String  end=enTime.split("-")[0]+"年"+enTime.split("-")[1]+"月"+enTime.split("-")[2]+"日";
			classes.setStarttime(start+"--"+end);
		}
		//查询出班主任
		List<Teacher> classesTeacherById = teacherServiceImpl.getTeacherByClassesId(classes.getClassesId());
		Teacher classesTeacher = new Teacher();
		if(classesTeacherById!=null&&classesTeacherById.size()>0){
			classesTeacher = classesTeacherById.get(0);
		}
		// 查询出学生信息
		List<Student> studentList = classesServiceImpl.getStudentByCid(classesId);
		
		// ==========封装到map集合==========
		map.put("classes", classes);
		map.put("classesTeacher", classesTeacher);
		map.put("studentList", studentList);
		
		// ==========下载到客户端==========
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setDirectoryForTemplateLoading(
				new File(request.getSession().getServletContext().getRealPath("/download")));
		Template template = configuration.getTemplate("考勤表.ftl");
		// ============选择下载路径===========
		ServletOutputStream outputStream = response.getOutputStream();
		Writer out = new BufferedWriter(new OutputStreamWriter(outputStream));
		try {
			String fileName1 = "考勤表_"+classes.getCname()+".doc";
			response.reset(); // 清除缓冲区中的任何数据以及状态代码和标头
			String header = request.getHeader("user-agent");
			if (header.contains("Firefox")) {
				fileName1 = "=?UTF-8?B?" + new BASE64Encoder().encode(fileName1.getBytes("utf-8")) + "?=";
			} else {
				fileName1 = URLEncoder.encode(fileName1, "UTF-8");
			}
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName1);
			response.setContentType("application/octet-stream;charset=UTF-8");
			template.process(map, out);
		} catch (Exception e) {
			out.flush();
			out.close();
			outputStream.flush();
			outputStream.close();
		}
		out.flush();
		out.close();
		outputStream.flush();
		outputStream.close();
		return null;
	}*/
}
