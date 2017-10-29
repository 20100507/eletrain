package cn.edu.qqhru.train.controller;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.qqhru.train.pojo.Course;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.service.CourseService;
import cn.edu.qqhru.train.service.TeacherService;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.CourseVo;
import cn.edu.qqhru.train.vo.DeleteMultipleObjects;
import sun.misc.BASE64Encoder;

/**
 * <p>Title: CourseController</p>
 * <p>Description: 课程管理Controller</p>
 * <p>School: qiqihar university</p> 
 * @author	白鹏飞
 * @date	2017年9月1日下午6:27:29
 * @version 1.0
 */
@Controller
@RequestMapping("/course")
public class CourseController {
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private TeacherService teacherService;
	
	/**
	 * <p>Title: getAdminListByPage</p>
	 * <p>Description: 以分页的方式显示所有管理员信息</p>
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	@RequestMapping("/getCourseListByPage")
	public String getCourseListByPage(@RequestParam(value="page",defaultValue="1") int page, 
										@RequestParam(value="rows",defaultValue="8") int rows, Model model,
										HttpServletRequest request){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		PageBean<CourseVo> pageBean = courseService.getCourseListPageBean(page, rows);
		for(CourseVo course : pageBean.getList()){
			Teacher teacher = teacherService.getTeacherBYid(course.getTeacherId());
			course.setTeacher(teacher);
		}
		String path = "getCourseListByPage?menuId="+menuId;
		model.addAttribute("path", path);
		model.addAttribute("pageBean", pageBean);
		return "course-list";
	}
	
	/**
	 * <p>Title: getCourseListByPageAndCondition</p>
	 * <p>Description: 分页显示搜索出来的课程信息</p>
	 * @param page
	 * @param rows
	 * @param condition
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/getCourseListByPageAndCondition")
	public String getCourseListByPageAndCondition(@RequestParam(value="page",defaultValue="1") int page, 
													@RequestParam(value="rows",defaultValue="8") int rows, 
													@RequestParam String condition, HttpServletRequest request, Model model) throws Exception{
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		if(condition == null || condition == ""){
			return "redirect:/course/getCourseListByPage?menuId"+menuId+"&page="+page;
		}
		
		//解决get方式提交乱码
		String condition1 = new String(condition.getBytes("ISO-8859-1"),"utf-8"); //转码UTF8
		
		PageBean<CourseVo> pageBean = courseService.getCourseListByPageAndCondition(condition1, page, rows);
		for(CourseVo course : pageBean.getList()){
			Teacher teacher = teacherService.getTeacherBYid(course.getTeacherId());
			course.setTeacher(teacher);
		}
		String path = "getCourseListByPageAndCondition?menuId="+menuId+"&condition="+condition1;
		model.addAttribute("path", path);
		model.addAttribute("pageBean", pageBean);
		return "course-list";
	}
	
	/**
	 * <p>Title: toAddAdmin</p>
	 * <p>Description: 跳转到add-course.jsp页面</p>
	 * @return
	 */
	@RequestMapping("/toAddCourse")
	public String toAddAdmin(HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "add-course";
	}
	
	/**
	 * <p>Title: findTeacherList</p>
	 * <p>Description: 获取所有的teacher</p>
	 * @return
	 */
	@RequestMapping("/findTeacherList")
	@ResponseBody
	public List<Teacher> findTeacherList(){
		List<Teacher> teacherList = teacherService.getAllTeacher();
		return teacherList;
	}
	
	/**
	 * <p>Title: addCourse</p>
	 * <p>Description: 新增课程模块</p>
	 * @param course
	 * @param teacherId
	 * @return
	 */
	@RequestMapping("/addCourse")
	public String addCourse(Course course, HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		if(StringUtils.hasText(course.getCname()) && course.getCapacity() != null 
				&& StringUtils.hasText(course.getPlace()) && course.getTeacherId() != null){
			boolean flag = courseService.addCourse(course);
			if(flag){
				return "redirect:/course/getCourseListByPage?menuId="+menuId;
			}
		}
		return "add-course";
		
	}
	
	/**
	 * <p>Title: toUpdateCourse</p>
	 * <p>Description: 携带teacher跳转到更新界面</p>
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/toUpdateCourse")
	public String toUpdateCourse(@RequestParam Integer id, Model model, HttpServletRequest request){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		Course course = courseService.getCourseById(id);
		model.addAttribute("course", course);
		return "update-course";
	}
	
	/**
	 * <p>Title: updateCourse</p>
	 * <p>Description: 修改课程模块</p>
	 * @param course
	 * @return
	 */
	@RequestMapping("/updateCourse")
	public String updateCourse(Course course, HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		if(course.getCourseId() != null && StringUtils.hasText(course.getCname()) 
				&& course.getCapacity() != null && StringUtils.hasText(course.getPlace())
				&& course.getTeacherId() != null){	
			boolean flag = courseService.updateAdminById(course);
			if(flag){
				return "redirect:/course/getCourseListByPage?menuId="+menuId;
			}
		}
		return "update-course";
	}
	
	/**
	 * <p>Title: deleteCourse</p>
	 * <p>Description: 删除课程模块</p>
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteCourse")
	public String deleteCourse(@RequestParam Integer id, HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		boolean flag = courseService.deleteCourse(id);
		if(flag){
			
		}else {
			
		}
		return "redirect:/course/getCourseListByPage?menuId="+menuId;
	}
	
	/**
	 * <p>Title: deleteMultipleAdmins</p>
	 * <p>Description: 批量删除课程模块</p>
	 * @param deleteMultipleObjects
	 * @return
	 */
	@RequestMapping("/deleteMultipleCourses")
	public String deleteMultipleCourses(DeleteMultipleObjects deleteMultipleObjects, HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		if(deleteMultipleObjects.getIds() != null){
			courseService.deleteMultipleCourses(deleteMultipleObjects.getIds());
		}
		return "redirect:/course/getCourseListByPage?menuId="+menuId;
	}
	
	/**
	 * <p>Title: downloadTemplate</p>
	 * <p>Description: 下载模板模块</p>
	 * @param format
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadTemplate")
	public ResponseEntity<byte[]> downloadTemplate(String format, HttpServletRequest request, Model model) throws Exception{
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		String filename = "课程信息表"+format;
		String path = request.getSession().getServletContext().getRealPath("/download/");
		File file = new File(path+File.separator+filename);
		HttpHeaders headers = new HttpHeaders();
		String header = request.getHeader("user-agent");
		String downloadFileName = "";
		if (header.contains("Firefox")) {
			downloadFileName = "=?UTF-8?B?"+new BASE64Encoder().encode(filename.getBytes("utf-8"))+"?=";
		}else {
			downloadFileName = URLEncoder.encode(filename,"utf-8");
		}
		headers.setContentDispositionFormData("attachment", downloadFileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);  
	}
	
	/**
	 * <p>Title: importData</p>
	 * <p>Description: 导入文件模块</p>
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importData")
	public String importData(MultipartFile file, Model model, HttpServletRequest request) throws Exception{
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		String path = "";
		String suffix = "";
		if(file != null &&  file.getSize() !=0){
			path = file.getOriginalFilename();
			suffix = path.substring(path.lastIndexOf(".")+1);
			
			InputStream inputStream = file.getInputStream();
			
			List<Course> courseList = new ArrayList<Course>();
			if(suffix.equals("xlsx")){
				//得到Excel工作簿对象
				XSSFWorkbook wb = new XSSFWorkbook(inputStream);//创建Excel工作簿对象 
				//得到Excel工作表对象   
			    XSSFSheet sheet = wb.getSheetAt(0); 
			    for(Row row : sheet){
			    	int rowNum = row.getRowNum();
			    	if(rowNum < 2){
			    		continue;
			    	}
			    	List<String> field = new ArrayList<String>(); 
			    	for(Cell cell : row){
			    		switch (cell.getCellType()){
						case 0:
							field.add(String.valueOf((int) cell.getNumericCellValue()));
							break;
						case 1:
							field.add(cell.getStringCellValue());
							break;
						}
			    	}
			    	Course course = new Course();
			    	course.setCname(field.get(0));
			    	Teacher teacher = teacherService.getOneTeacherByCname(field.get(1));
			    	course.setTeacherId(teacher.getTeacherId());
			    	course.setPeriod(Integer.valueOf(field.get(2)));
			    	course.setCapacity(Integer.valueOf(field.get(3)));
			    	course.setPlace(field.get(4));
			    	if(field.size() >= 5){
			    		course.setInfo(field.get(5));
			    	}
			    	courseList.add(course);
			    	field.clear();
			    }
			}else if(suffix.equals("xls")){
				//得到Excel工作簿对象
				HSSFWorkbook wb = new HSSFWorkbook(inputStream);//创建Excel工作簿对象 
				//得到Excel工作表对象   
			    HSSFSheet sheet = wb.getSheetAt(0); 
			    for(Row row : sheet){
			    	int rowNum = row.getRowNum();
			    	if(rowNum < 2){
			    		continue;
			    	}
			    	List<String> field = new ArrayList<String>(); 
			    	for(Cell cell : row){
			    		switch (cell.getCellType()) {
						case 0:
							field.add(String.valueOf((int) cell.getNumericCellValue()));
							break;
						case 1:
							field.add(cell.getStringCellValue());
							break;
						}
			    	}
			    	Course course = new Course();
			    	course.setCname(field.get(0));
			    	Teacher teacher = teacherService.getOneTeacherByCname(field.get(1));
			    	course.setTeacherId(teacher.getTeacherId());;
			    	course.setPeriod(Integer.valueOf(field.get(2)));
			    	course.setCapacity(Integer.valueOf(field.get(3)));
			    	course.setPlace(field.get(4));
			    	if(field.size() >= 5){
			    		course.setInfo(field.get(5));
			    	}
			    	courseList.add(course);
			    	field.clear();
			    }
			}
			
		    courseService.addMultipleCourses(courseList);
		}
		
		
	    return "redirect:/course/getCourseListByPage?menuId="+menuId;
	}
	
	/**
	 * <p>Title: exportData</p>
	 * <p>Description: 导出文件模块</p>
	 * @param format
	 * @param page
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportData")
	public void exportData(String format, String page, HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(format.equals("xls")){
			HSSFWorkbook wb = new HSSFWorkbook();//创建Excel工作簿对象  
		    HSSFSheet sheet = wb.createSheet("课程");//创建Excel工作表对象   
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
		    cell.setCellValue("课程信息表");
		    cell.setCellStyle(cellStyle);
		    row.setHeight((short) (30*20));
		    
		    //第二行设置
		    String []name = {"课程名","任课老师","学分","课程容量","上课地点","课程详情"};
	    	row = sheet.createRow(1);
	    	row.setHeight((short) (30*20));
		    for(int i=0; i<6; i++){
		    	cell = row.createCell(i);
		    	cell.setCellValue(name[i]);
		    	cell.setCellStyle(cellStyle);
		    	sheet.setColumnWidth(i, 10 * 512);
		    } 
		    sheet.setColumnWidth(4, 20 * 512);
		    
		    //第三行及以后设置
		    List<Course> courseList = courseService.getCourseList();
		    int i = 2;
		    for(Course course : courseList){
		    	row = sheet.createRow(i); //创建Excel工作表的行  
		    	HSSFCell cell_0 = row.createCell(0);
		    	HSSFCell cell_1 = row.createCell(1);
		    	HSSFCell cell_2 = row.createCell(2);
		    	HSSFCell cell_3 = row.createCell(3);
		    	HSSFCell cell_4 = row.createCell(4);
		    	HSSFCell cell_5 = row.createCell(5);
		    	cell_0.setCellValue(course.getCname());
		    	cell_0.setCellStyle(cellStyle);
		    	
		    	Teacher teacher = teacherService.getTeacherBYid(course.getTeacherId());
		    	cell_1.setCellValue(teacher.getUsername());
		    	cell_1.setCellStyle(cellStyle);
		    	
		    	cell_2.setCellValue(course.getPeriod());
		    	cell_2.setCellStyle(cellStyle);
		    	
		    	cell_3.setCellValue(course.getCapacity());
		    	cell_3.setCellStyle(cellStyle);
		    	
		    	cell_4.setCellValue(course.getPlace());
		    	cell_4.setCellStyle(cellStyle);
		    	
		    	cell_5.setCellValue(course.getInfo());
		    	cell_5.setCellStyle(cellStyle);
		    	i++;
		    }
		    
		  //============选择下载路径===========
		    ServletOutputStream outputStream = response.getOutputStream();
		    try {
		    	String fileName = "课程信息."+format;  
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
		    XSSFSheet sheet = wb.createSheet("课程");//创建Excel工作表对象   
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
		    cell.setCellValue("课程信息表");
		    cell.setCellStyle(cellStyle);
		    row.setHeight((short) (30*20));
		    
		    //第二行设置
		    String []name = {"课程名","任课老师","学分","课程容量","上课地点","课程详情"};
	    	row = sheet.createRow(1);
	    	row.setHeight((short) (30*20));
		    for(int i=0; i<6; i++){
		    	cell = row.createCell(i);
		    	cell.setCellValue(name[i]);
		    	cell.setCellStyle(cellStyle);
		    	sheet.setColumnWidth(i, 10 * 512);
		    } 
		    sheet.setColumnWidth(4, 20 * 512);
		    
		  //第三行及以后设置
		    List<Course> courseList = courseService.getCourseList();
		    int i = 2;
		    for(Course course : courseList){
		    	row = sheet.createRow(i); //创建Excel工作表的行  
		    	XSSFCell cell_0 = row.createCell(0);
		    	XSSFCell cell_1 = row.createCell(1);
		    	XSSFCell cell_2 = row.createCell(2);
		    	XSSFCell cell_3 = row.createCell(3);
		    	XSSFCell cell_4 = row.createCell(4);
		    	XSSFCell cell_5 = row.createCell(5);
		    	
		    	cell_0.setCellValue(course.getCname());
		    	cell_0.setCellStyle(cellStyle);
		    	
		    	Teacher teacher = teacherService.getTeacherBYid(course.getTeacherId());
		    	cell_1.setCellValue(teacher.getUsername());
		    	cell_1.setCellStyle(cellStyle);
		    	
		    	cell_2.setCellValue(course.getPeriod());
		    	cell_2.setCellStyle(cellStyle);
		    	
		    	cell_3.setCellValue(course.getCapacity());
		    	cell_3.setCellStyle(cellStyle);
		    	
		    	cell_4.setCellValue(course.getPlace());
		    	cell_4.setCellStyle(cellStyle);
		    	
		    	cell_5.setCellValue(course.getInfo());
		    	cell_5.setCellStyle(cellStyle);
		    	i++;
		    }
		    
		    //============选择下载路径===========
		    ServletOutputStream outputStream = response.getOutputStream();
		    try {
		    	String fileName = "课程信息."+format;  
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
