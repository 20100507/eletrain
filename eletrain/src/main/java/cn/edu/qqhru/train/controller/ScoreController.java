package cn.edu.qqhru.train.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.ClassesTeacherKey;
import cn.edu.qqhru.train.pojo.Course;
import cn.edu.qqhru.train.pojo.Score;
import cn.edu.qqhru.train.pojo.Student;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.service.ClassesService;
import cn.edu.qqhru.train.service.CourseService;
import cn.edu.qqhru.train.service.ScoreService;
import cn.edu.qqhru.train.service.StuService;
import cn.edu.qqhru.train.service.TeacherService;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.ScoreVo;
import sun.misc.BASE64Encoder;

/**
 * <p>Title: ScoreController</p>
 * <p>Description: 分数模块的Controller</p>
 * <p>School: qiqihar university</p> 
 * @author	白鹏飞
 * @date	2017年9月6日下午3:19:37
 * @version 1.0.0
 */

@Controller
@RequestMapping("/score")
public class ScoreController {
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private StuService studentService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private ClassesService classesService;
	
	/**
	 * <p>Title: getScoreListByPage</p>
	 * <p>Description: 以分页的方式显示所有学生的分数信息</p>
	 * @return
	 */
	@RequestMapping("/getScoreListByPage")
	public String getScoreListByPage(@RequestParam(value="page",defaultValue="1") int page, 
										@RequestParam(value="rows",defaultValue="8") int rows, Model model,
										HttpServletRequest request){
		
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		int identity = 0;
		int teacherId = 0;
		if (user instanceof Admin){
			Admin admin = (Admin) user;
			identity = admin.getIdentify();
		}else if (user instanceof Teacher) {
			Teacher teacher = (Teacher) user;
			teacherId = teacher.getTeacherId();
			identity = teacher.getIdentify();
		}
		
		PageBean<ScoreVo> pageBean = null;
		if(identity == 1){
			pageBean = scoreService.getScoreListPageBean(page, rows, 1, null ,identity);
		}else if(identity == 6){
			pageBean = scoreService.getScoreListPageBean(page, rows, teacherId, null ,identity);
		}else{
			List<Classes> classesList = classesService.getClassesByLectureTeacherIdNoPage(teacherId);
			List<Integer> classesIdList = new ArrayList<Integer>();
			if(classesList != null && classesList.size() > 0){
				for (Classes classes : classesList) {
					classesIdList.add(classes.getClassesId());
				}
			}
			pageBean = scoreService.getScoreListPageBean(page, rows, teacherId, classesIdList ,identity);
		}
		for(ScoreVo scoreVo : pageBean.getList()){
			Teacher teacher = teacherService.getTeacherBYid(scoreVo.getTeacherId());
			scoreVo.setTeacher(teacher);
			
			Student student = studentService.getStuOne(scoreVo.getStudentId());
			scoreVo.setStudent(student);
			
			Classes clazz = classesService.getClassesBYid(scoreVo.getClassesId());
			scoreVo.setClazz(clazz);
		}
		model.addAttribute("pageBean", pageBean);
		String path = "getScoreListByPage?menuId="+menuId;
		model.addAttribute("path", path);
		if(identity == 1){
			return "manage-score";
		}
		return "score-list";
	}
	
	/**
	 * <p>Title: getScoreListByPageAndCondition</p>
	 * <p>Description: 分页显示搜索出来的学员成绩信息</p>
	 * @param identity
	 * @param page
	 * @param rows
	 * @param condition
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getScoreListByPageAndCondition")
	public String getScoreListByPageAndCondition(@RequestParam(value="page",defaultValue="1") int page, 
													@RequestParam(value="rows",defaultValue="8") int rows, 
													@RequestParam String condition1, 
													@RequestParam Integer condition2,
													@RequestParam(value="condition3", defaultValue="-100", required=false) Integer condition3, Model model,
													HttpServletRequest request) throws Exception{
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		int identity = 0;
		int teacherId = 0;
		if (user instanceof Admin) {
			if((condition1 == null || condition1 == "") && condition2 == -100 && condition3 == -100){
				return "redirect:/score/getScoreListByPage?page="+page;
			}
			Admin admin = (Admin) user;
			identity = admin.getIdentify();
			teacherId = condition3;
		}else if (user instanceof Teacher){
			if((condition1 == null || condition1 == "") && condition2 == -100){
				return "redirect:/score/getScoreListByPage?page="+page;
			}
			Teacher teacher = (Teacher) user;
			identity = teacher.getIdentify();
			teacherId = teacher.getTeacherId();
			condition3 = teacherId;
		}
		//解决get方式提交乱码
		String condition4 = new String(condition1.getBytes("ISO-8859-1"),"utf-8"); //转码UTF8
		Student student = studentService.getStuOneByIdcard(condition4);
		PageBean<ScoreVo> pageBean = null;
		if(student == null && condition2 == -100 && condition3 == -100){
			pageBean = scoreService.getScoreListByPageAndCondition(student, condition2 ,condition3, null ,identity, page, rows);
		}else{
			if(identity == 2){
				List<Classes> classesList = classesService.getClassesByLectureTeacherIdNoPage(teacherId);
				List<Integer> classesIdList = new ArrayList<Integer>();
				if(classesList != null && classesList.size() > 0){
					for (Classes classes : classesList) {
						classesIdList.add(classes.getClassesId());
					}
				}
				pageBean = scoreService.getScoreListByPageAndCondition(student, condition2 ,teacherId, classesIdList ,identity, page, rows);
			}else{
				pageBean = scoreService.getScoreListByPageAndCondition(student, condition2 ,teacherId, null ,identity, page, rows);
			}
		}
		if(pageBean != null && pageBean.getList().size() > 0){
			for(ScoreVo scoreVo : pageBean.getList()){
				Teacher teacher = teacherService.getTeacherBYid(scoreVo.getTeacherId());
				scoreVo.setTeacher(teacher);
				
				Student student1 = studentService.getStuOne(scoreVo.getStudentId());
				scoreVo.setStudent(student1);
				
				Classes clazz = classesService.getClassesBYid(scoreVo.getClassesId());
				scoreVo.setClazz(clazz);
			}
		}
		model.addAttribute("pageBean", pageBean);
		if(identity == 1){
			String path = "getScoreListByPageAndCondition?menuId="+menuId+"&condition1="+condition4+"&condition2="
																	+condition2+"&condition3="+condition3;
			model.addAttribute("path", path);
			return "manage-score";
		}
		String path = "getScoreListByPageAndCondition?menuId="+menuId+"&condition1="+condition4+"&condition2="+condition2;
		model.addAttribute("path", path);
		return "score-list";
	}
	
	@RequestMapping("/toAddScore")
	public String toAddScore(HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		if (user instanceof Admin) {
			return "manage-add-score";
		}
		return "add-score";
	}
	
	@RequestMapping("/addScore")
	public String addScore(Score score, HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		int identity = 0;
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		if(score.getTeacherId() == null){
			if (user instanceof Admin) {
				identity = ((Admin) user).getIdentify();
			}else if (user instanceof Teacher) {
				Teacher teacher = (Teacher) user;
				//score.setTeacherId(teacher.getTeacherId());
				identity = teacher.getIdentify();
			}
		}else{
			if(user instanceof Admin){
				identity = ((Admin) user).getIdentify();
			}
		}
		
		if(score.getClassesId() != null && score.getClassesId() != 0 && score.getStudentId() != null && score.getStudentId() != 0 && score.getTotal() != null){
			boolean flag = scoreService.addScore(score);
			if(flag){
				return "redirect:/score/getScoreListByPage?menuId="+menuId;
			}
		}
		if(identity == 1){
			return "manage-add-score";
		}else{
			return "add-score";
		}
	}
	
	@RequestMapping("/toUpdateScore")
	public String toUpdateScore(Integer id, HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		ScoreVo scoreVo = scoreService.getScoreListByScoreId(id);
		Teacher teacher = teacherService.getTeacherBYid(scoreVo.getTeacherId());
		scoreVo.setTeacher(teacher);
		
		Student student = studentService.getStuOne(scoreVo.getStudentId());
		scoreVo.setStudent(student);
		
		Classes clazz = classesService.getClassesBYid(scoreVo.getClassesId());
		scoreVo.setClazz(clazz);
		model.addAttribute("score", scoreVo);
		return "update-score";
	}
	
	@RequestMapping("/updateScore")
	public String updateScore(Score score, Model model, HttpServletRequest request){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		boolean flag = scoreService.updateScore(score);
		if(flag){
			return "redirect:/score/getScoreListByPage?menuId="+menuId;
		}
		return "update-score";
	}
	
	@RequestMapping("/findCourseList")
	@ResponseBody
	public List<Course> findCourseList(){
		List<Course> courseList = courseService.getCourseList();
		return courseList;
	}
	
	@RequestMapping("/findTeacherList")
	@ResponseBody
	public List<Teacher> findTeacherList(){
		List<Teacher> teacherList = teacherService.getAllTeacher();
		return teacherList;
	}
	
	@RequestMapping("findClassesList")
	@ResponseBody
	public List<Classes> findClassesList(){
		List<Classes> classessList = classesService.getAllClasses();
		return classessList;
	}
	
	@RequestMapping("/findClassesListByTeacherId")
	@ResponseBody
	public List<Classes> findClassesListByTeacherId(@RequestBody String teacherId, HttpServletRequest request){
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		teacherId = teacherId.replace("=","");
		Integer newTeacherId = Integer.valueOf(teacherId);
		if(user instanceof Admin){
			newTeacherId = -100;
		}else if (user instanceof Teacher) {
			Teacher teacher = (Teacher) user;
			newTeacherId = teacher.getTeacherId();
		}
		Teacher teacher = teacherService.getTeacherBYid(newTeacherId);
		List<Classes> classessList = new ArrayList<Classes>();
		if(newTeacherId == -100){
			classessList = classesService.getAllClasses();
		}else if(teacher != null && newTeacherId != -100){
			List<ClassesTeacherKey> classesTeacherList = new ArrayList<ClassesTeacherKey>();
			if(teacher.getIdentify() == 6){
				classesTeacherList = classesService.getClassesTeacherListByTeacherId(newTeacherId);
				if(classesTeacherList != null){
					for(ClassesTeacherKey classesTeacher : classesTeacherList){
						Classes classes = classesService.getClassesBYid(classesTeacher.getClassesId());
						classessList.add(classes);
					}
				}
			}else if(teacher.getIdentify() == 2){
				classessList = classesService.getClassesByLectureTeacherIdNoPage(newTeacherId);
			}
		}
		return classessList;
	}
	
	@RequestMapping("/findStudentListByClassesId")
	@ResponseBody
	public List<Student> findStudentListByClassesId(@RequestBody String classesId){
		List<Score> scoreList = scoreService.getScoreList();
		List<Student> list = studentService.getStuByClassId(Integer.valueOf(classesId.replace("=","")));
		List<Student> studentList = new ArrayList<Student>();
		for(Student student : list){
			boolean flag = false;
			if(scoreList != null){
				for (Score score : scoreList) {
					if(student.getStudentId() == score.getStudentId() || student.getStudentId().equals(score.getStudentId())){
						flag = false;
						break;
					}
					flag = true;
				}
			}else{
				flag = true;
			}
			
			if(flag){
				studentList.add(student);
			}
		}
		return studentList;
	}
	
	@RequestMapping("/exportData")
	public void exportData(String format, @RequestParam(value="classesId", required=false) Integer classesId, 
						HttpServletRequest request, HttpServletResponse response) throws Exception{
		Classes clazz = null;
		if(format.equals("xls")){
			HSSFWorkbook wb = new HSSFWorkbook();//创建Excel工作簿对象  
			HSSFSheet sheet = null;
			if(classesId == null){
				sheet = wb.createSheet("成绩");//创建Excel工作表对象   
			}else{
				clazz = classesService.getClassesBYid(classesId);
				sheet = wb.createSheet(clazz.getCname()+"成绩");//创建Excel工作表对象   
			}
		    HSSFRow row;
		    HSSFCell cell;
		    
		    //所有的样式
		    HSSFCellStyle cellStyle = wb.createCellStyle();
		    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
		    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
		    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
		    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中  
		    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中 
		    
		    //第一行设置
		    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5)); //合并单元格
		    //得到所有区域      
		    //sheet.getNumMergedRegions(); 
		    row = sheet.createRow(0);
		    cell = row.createCell(0);
		    if(classesId == null){
		    	cell.setCellValue("成绩单");
		    }else{
		    	cell.setCellValue(clazz.getCname()+"班成绩单");
		    }
		    
		    cell.setCellStyle(cellStyle);
		    row.setHeight((short) (30*20));
		    
		    //第二行设置
		    String []name = {"班级","学生姓名","理论成绩","实践成绩","总成绩","备注"};
	    	row = sheet.createRow(1);
	    	row.setHeight((short) (30*20));
		    for(int i=0; i<6; i++){
		    	cell = row.createCell(i);
		    	cell.setCellValue(name[i]);
		    	cell.setCellStyle(cellStyle);
		    	sheet.setColumnWidth(i, 10 * 512);
		    } 
		    //sheet.setColumnWidth(7, 20 * 512);
		    
		    //第三行及以后设置
		    List<Score> scoreList = null;
		    if(classesId == null){
		    	scoreList = scoreService.getScoreList();
		    }else{
		    	scoreList = scoreService.getScoreListByClassesId(classesId);
		    }
		    int i = 2;
		    if(scoreList != null){
		    	for(Score score : scoreList){
			    	row = sheet.createRow(i); //创建Excel工作表的行  
			    	HSSFCell cell_0 = row.createCell(0);
			    	HSSFCell cell_1 = row.createCell(1);
			    	HSSFCell cell_2 = row.createCell(2);
			    	HSSFCell cell_3 = row.createCell(3);
			    	HSSFCell cell_4 = row.createCell(4);
			    	HSSFCell cell_5 = row.createCell(5);
			    	
			    	if(classesId ==null){
			    		Classes classes = classesService.getClassesBYid(score.getClassesId());
				    	cell_0.setCellValue(classes.getCname());
				    	
			    	}else{
			    		cell_0.setCellValue(clazz.getCname());
			    	}
			    	cell_0.setCellStyle(cellStyle);
			    	
			    	Student student = studentService.getStuOne(score.getStudentId());
			    	cell_1.setCellValue(student.getUsername());
			    	cell_1.setCellStyle(cellStyle);
			    	
//			    	Teacher teacher = teacherService.getTeacherBYid(score.getTeacherId());
//			    	cell_2.setCellValue(teacher.getUsername());
//			    	cell_2.setCellStyle(cellStyle);
			    	
			    	if(score.getTheoryscore() == null){
			    		cell_2.setCellValue("");
			    	}else{
			    		cell_2.setCellValue(score.getTheoryscore());
			    	}
			    	cell_2.setCellStyle(cellStyle);
			    	
			    	if(score.getPracticescore() == null){
			    		cell_3.setCellValue("");
			    	}else{
			    		cell_3.setCellValue(score.getPracticescore());
			    		
			    	}
			    	cell_3.setCellStyle(cellStyle);
			    	
			    	cell_4.setCellValue(score.getTotal());
			    	cell_4.setCellStyle(cellStyle);
			    	
			    	if(score.getCommon() == null){
			    		cell_5.setCellValue("");
			    	}else{
			    		cell_5.setCellValue(score.getCommon());
			    		
			    	}
			    	cell_5.setCellStyle(cellStyle);
			    	i++;
			    }
		    }
		    
		    
		  //============选择下载路径===========
		    ServletOutputStream outputStream = response.getOutputStream();
		    try {
		    	String fileName = "";
		    	if(classesId == null){
		    		fileName = "成绩单."+format;
		    	}else{
		    		fileName = clazz.getCname()+"班成绩单."+format;
		    	}
		    	  
		    	response.reset();  //清除缓冲区中的任何数据以及状态代码和标头
		    	String header = request.getHeader("user-agent");
		    	if (header.contains("Firefox")) {
		    		fileName = "=?UTF-8?B?"+new BASE64Encoder().encode(fileName.getBytes("utf-8"))+"?=";
		    	}else {
		    		fileName = URLEncoder.encode(fileName, "UTF-8");  
		    	}
		    	response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
		    	response.setContentType("application/octet-stream;charset=UTF-8");  
		    	wb.write(outputStream);
		    }catch (Exception e) {
		    	outputStream.flush();
		    	outputStream.close();  
		    }  
		    outputStream.flush();
		    outputStream.close();  
		}else if(format.equals("xlsx")){
			XSSFWorkbook wb = new XSSFWorkbook();//创建Excel工作簿对象
			XSSFSheet sheet = null;
			if(classesId == null){
				sheet = wb.createSheet("成绩");//创建Excel工作表对象   
			}else{
				clazz = classesService.getClassesBYid(classesId);
				sheet = wb.createSheet(clazz.getCname()+"成绩");//创建Excel工作表对象   
			}
			  
		    XSSFRow row;
		    XSSFCell cell;
		    
		    //所有的样式
		    XSSFCellStyle cellStyle = wb.createCellStyle();
		    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
		    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
		    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
		    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框  
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中  
		    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中 
		    
		    //第一行设置
		    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5)); //合并单元格
		    //得到所有区域      
		    //sheet.getNumMergedRegions(); 
		    row = sheet.createRow(0);
		    cell = row.createCell(0);
		    if(classesId == null){
		    	cell.setCellValue("成绩单");
		    }else{
		    	cell.setCellValue(clazz.getCname()+"班成绩单");
		    }
		    cell.setCellStyle(cellStyle);
		    row.setHeight((short) (30*20));
		    
		    //第二行设置
		    String []name = {"班级","学生姓名","理论成绩","实践成绩","总成绩","备注"};
	    	row = sheet.createRow(1);
	    	row.setHeight((short) (30*20));
		    for(int i=0; i<6; i++){
		    	cell = row.createCell(i);
		    	cell.setCellValue(name[i]);
		    	cell.setCellStyle(cellStyle);
		    	sheet.setColumnWidth(i, 10 * 512);
		    } 
		    //sheet.setColumnWidth(7, 2s0 * 512);
		    
		  //第三行及以后设置
		    List<Score> scoreList = null;
		    if(classesId == null){
		    	scoreList = scoreService.getScoreList();
		    }else{
		    	scoreList = scoreService.getScoreListByClassesId(classesId);
		    }
		    int i = 2;
		    if(scoreList != null){
		    	for(Score score : scoreList){
			    	row = sheet.createRow(i); //创建Excel工作表的行  
			    	XSSFCell cell_0 = row.createCell(0);
			    	XSSFCell cell_1 = row.createCell(1);
			    	XSSFCell cell_2 = row.createCell(2);
			    	XSSFCell cell_3 = row.createCell(3);
			    	XSSFCell cell_4 = row.createCell(4);
			    	XSSFCell cell_5 = row.createCell(5);
			    	
			    	if(classesId ==null){
			    		Classes classes = classesService.getClassesBYid(score.getClassesId());
				    	cell_0.setCellValue(classes.getCname());
				    	
			    	}else{
			    		cell_0.setCellValue(clazz.getCname());
			    	}
			    	cell_0.setCellStyle(cellStyle);
			    	
			    	Student student = studentService.getStuOne(score.getStudentId());
			    	cell_1.setCellValue(student.getUsername());
			    	cell_1.setCellStyle(cellStyle);
			    	
//			    	Teacher teacher = teacherService.getTeacherBYid(score.getTeacherId());
//			    	cell_2.setCellValue(teacher.getUsername());
//			    	cell_2.setCellStyle(cellStyle);
			    	
			    	if(score.getTheoryscore() == null){
			    		cell_2.setCellValue("");
			    	}else{
			    		cell_2.setCellValue(score.getTheoryscore());
			    	}
			    	cell_2.setCellStyle(cellStyle);
			    	
			    	if(score.getPracticescore() == null){
			    		cell_3.setCellValue("");
			    	}else{
			    		cell_3.setCellValue(score.getPracticescore());
			    		
			    	}
			    	cell_3.setCellStyle(cellStyle);
			    	
			    	cell_4.setCellValue(score.getTotal());
			    	cell_4.setCellStyle(cellStyle);
			    	
			    	if(score.getCommon() == null){
			    		cell_5.setCellValue("");
			    	}else{
			    		cell_5.setCellValue(score.getCommon());
			    		
			    	}
			    	cell_5.setCellStyle(cellStyle);
			    	i++;
			    }
		    }
		    
		    //============选择下载路径===========
		    ServletOutputStream outputStream = response.getOutputStream();
		    try {
		    	String fileName = "";
		    	if(classesId == null){
		    		fileName = "成绩单."+format;
		    	}else{
		    		fileName = clazz.getCname()+"班成绩单."+format;
		    	}  
		    	response.reset();  //清除缓冲区中的任何数据以及状态代码和标头
		    	String header = request.getHeader("user-agent");
		    	if (header.contains("Firefox")) {
		    		fileName = "=?UTF-8?B?"+new BASE64Encoder().encode(fileName.getBytes("utf-8"))+"?=";
		    	}else {
		    		fileName = URLEncoder.encode(fileName, "UTF-8");  
		    	}
		    	response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
		    	response.setContentType("application/octet-stream;charset=UTF-8");  
		    	wb.write(outputStream);
		    }catch (Exception e) {
		    	outputStream.flush();
		    	outputStream.close();  
		    }  
		    outputStream.flush();
		    outputStream.close();  
		}
		HttpSession session = request.getSession();
		session.setAttribute("isSuccess", "true");
		session.setAttribute("successMessage", "导出成功！！！");
	}
}
