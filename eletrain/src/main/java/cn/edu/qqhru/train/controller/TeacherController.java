package cn.edu.qqhru.train.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.service.AdminService;
import cn.edu.qqhru.train.service.TeacherService;
import cn.edu.qqhru.train.utils.DownUtils;
import cn.edu.qqhru.train.utils.PageBean;
/**
 * 教师管理
 * @ClassName IndexController
 * @Description TODO
 * @author 张继富
 * @Date 2017年9月9日 下午5:09:43
 * @version 1.0.0
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {
	//模板文件名
	private String filename;
	@Autowired
	private TeacherService teacherServiceImpl;
	@Autowired
	private AdminService adminServiceImpl;
	/**
	 * 分页查询教师列表
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	public String toIndex(Model model,
			@RequestParam(value="page",defaultValue="1") int page,
			@RequestParam(value="rows",defaultValue="10")int rows,
			@RequestParam(value = "type", defaultValue = "0") int type,
			HttpServletRequest request) {
		PageBean<Teacher> pageBean = teacherServiceImpl.getAllTeacher(page,rows,type);
		model.addAttribute("pageBean", pageBean);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		model.addAttribute("path", "list?menuId="+menuId+"&type="+type);
		if(type==0){
			return "teacher-list";
		}
		else{
			return "teacher-list1";
		}
	}
	/**
	 * 登录名验证
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/LoginNameContentChecked")
	public String LoginNameContentChecked(HttpServletResponse response,String loginname) throws IOException {
		String status="allow";
		String index;
		List<Teacher> allTeacher = teacherServiceImpl.getAllTeacher();
		if(allTeacher!=null&&allTeacher.size()>0){
			for (Teacher teacher : allTeacher) {
				if(teacher.getIdcard().contentEquals(loginname)){
					status="repeat";
					index=teacher.getIdcard();
					break;
				}
			}
		}
		
		PrintWriter writer = response.getWriter();
		writer.print(status);
		return null;
	}
	/**
	 * 跳转到添加教师页面
	 * @return
	 */
	@RequestMapping("/addTeacherUI")
	public String toAddTeacherUI(HttpServletRequest request,Model model,Integer mould) {
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		model.addAttribute("mould", mould);
		return "teacher-publicUI";
	}
	/**
	 * 通过教师ids使教师和就职
	 * @return
	 */
	@RequestMapping("/updTeacherByIds")
	public String updTeacherByIds(Integer[] teacherIds) {
		if(teacherIds!=null){
			teacherServiceImpl.updTeacherByIds(teacherIds);
		}
		else{
			return "redirect:/teacher/list";
		}
		return "redirect:/teacher/list";
	}
	/**
	 * 添加教师
	 * @param teacher
	 * @return
	 */
	@RequestMapping("/addTeacher")
	public String addTeacher(HttpServletRequest request,Model model,Teacher teacher) {
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		teacher.setIsfinish(0);
		teacherServiceImpl.addTeacher(teacher);
		return "redirect:/teacher/list";
	}
	/**
	 * 教师离职，通过id
	 * @param teacherId
	 * @return
	 */
	@RequestMapping("/deleteTeacher")
	public String deleteTeacher(HttpServletRequest request,Model model,Integer teacherId) {
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		teacherServiceImpl.deleteTeacher(teacherId);
		return "redirect:/teacher/list";
	}
	/**
	 * 跳转到编辑页面
	 * @param model
	 * @param teacherId
	 * @return
	 */
	@RequestMapping("/updateTeacherUI")
	public String updateTeacherUI(HttpServletRequest request,Integer type,Model model,Integer teacherId) {
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		model.addAttribute("type", type);
		
		Teacher teacher = teacherServiceImpl.getTeacherBYid(teacherId);
		model.addAttribute("teacher", teacher);
		return "teacher-publicUI";
	}
	/**
	 * 编辑教师
	 * @param teacher
	 * @return
	 */
	@RequestMapping("/updateTeacher")
	public String updateTeacher(HttpServletRequest request,Integer type,Model model,Teacher teacher) {
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		teacherServiceImpl.updateTeacher(teacher);
		if(0==type){
			return "redirect:/teacher/list";
		}else{
			return "redirect:/teacher/list?type=1";
		}
	}
	/**
	 * 初始化密码
	 * @param teacher
	 * @return
	 */
	@RequestMapping("/init")
	public String init(HttpServletRequest request,Model model,Integer teacherId) {
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		teacherServiceImpl.init(teacherId);
		return "redirect:/teacher/list";
	}
	
	/**
	 * 查看Teacher详情信息
	 * @param teacherId
	 * @return
	 */
	@RequestMapping("/lookInfo")
	public String lookInfo(HttpServletRequest request,Integer index,Model model) {
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		Teacher teacher = teacherServiceImpl.lookInfo(index);
		model.addAttribute("teacher", teacher);
		return "teacher-info";
	}
	/**
	 * 通过索引查询Teacher
	 * @param index
	 * @param searchWay
	 * @param state
	 * @param model
	 * @return
	 */
	@RequestMapping("/searchTeacher")
	public String searchTeacher(
			@RequestParam(value="page",defaultValue="1") int page,
			@RequestParam(value="rows",defaultValue="10")int rows,
			HttpServletRequest request,
			String index,
			Integer searchWay,
			Model model) {
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		PageBean<Teacher> pageBean = new PageBean<>();
		if (searchWay == 0) {
			// 按工号查
			pageBean = teacherServiceImpl.getTeacherByLoginname(index,page,rows);
		}
		else if(searchWay == 1) {
			// 按姓名查
			pageBean = teacherServiceImpl.getTeacherByUsername(index,page,rows);
		}
		else{
			//按身份证号查
			pageBean = teacherServiceImpl.getTeacherByCardId(index,page,rows);
		}
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("path", "searchTeacher?index="+index+"&searchWay="+searchWay+"&menuId="+menuId);
		return "teacher-list";
	}
	/**
	 * 批量删除
	 * @param teacherIds
	 * @return
	 */
	@RequestMapping("/delTeacherByIds")
	public String delTeacherByIds(Integer[] teacherIds) {
		if(teacherIds!=null){
			teacherServiceImpl.delTeacherByIds(teacherIds);
		}
		else{
			return "redirect:/teacher/list";
		}
		return "redirect:/teacher/list";
	}
	/**
	 * 下载模板
	 */
	@RequestMapping("/downLoad")
	public ResponseEntity<byte[]> downLoad(Integer mould,HttpServletRequest request,HttpServletResponse response) throws IOException {  
		if(mould==1){
			//给定模板的文件名
			filename = "教师信息录入表.xlsx";
		}
		else{
			//给定模板的文件名
			filename = "教师信息录入表.xls";
		}
		//确定模板在服务端的真实路径
        String path = request.getSession().getServletContext().getRealPath("/") + "download/" + filename;  
        String header = request.getHeader("user-agent");
        return DownUtils.getResponseEntity(header,path, filename);
        
      /*  String agent = request.getHeader("user-agent");
        return DownUtils.getResponseEntity(agent,path, filename);
        String agent = request.getHeader("user-agent");
        return DownUtils.getResponseEntity(agent,path, filename);*/
    }
	/**
	 * 导入
	 * @throws IOException 
	 */
	@RequestMapping("/upload")
	public String upload(@RequestParam("file") CommonsMultipartFile file,HttpServletResponse response) throws IOException {
		String succ="s";
		String repeatLine="";
		String nullValueLine="";
		//设置响应类型，字符编码
		response.setContentType("text/html;charset=utf-8");
		//获得字符响应流
		PrintWriter out = response.getWriter();
		//从文件对象获得一个字节输入流
		InputStream inputStream = file.getInputStream();
		//创建一个List集合，存储读取到的一行数据
		List<String> fields = new ArrayList<>();
		//创建一个List集合，存储需要倒入的对象
		List<Teacher> teacherList = new ArrayList<>();
		//获取原始文件名
		String fileName = file.getOriginalFilename();
		//获取文件扩展名
		String postfix=fileName.substring(fileName.lastIndexOf(".")+1);
		//如果后缀为xlsx，按这种方式读取工作薄
		if (postfix.equals("xlsx")) {
			//读取工作簿
			XSSFWorkbook  workbook = new XSSFWorkbook (inputStream);
			try {
				//读取第一个工作表
				XSSFSheet  sheet = workbook.getSheetAt(0);
				//从第三行开始读取
				for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++)  
				{   //读取第i行 
					XSSFRow row = sheet.getRow(i);  
					for (int j = 0; j < row.getPhysicalNumberOfCells(); j++)  
					{  	//读取第i列
						XSSFCell cell = row.getCell(j);  
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);//？？？
						String rawValue = cell.getStringCellValue();
						fields.add(rawValue);
					} 
					if(fields.size()==0){
						continue;
					}
					
					/*for (int d=0; d<fields.size(); d++) {
						String trim = fields.get(d).trim();
						if(trim.length()==0){
							succ="f";
							nullValueLine+=i+1+",";
							break;
						}
					}*/
					String trim = fields.get(7).trim();
					if(trim.length()==0){
						succ="f";
						nullValueLine+=i+1+",";
					}
					if(teacherServiceImpl.checkLoginName(fields.get(7).replace(" ", ""))){
						succ="f";
						repeatLine+=i+1+",";
					}
					if(succ.contains("f")){
						fields.clear();
						continue;
					}
					
					Teacher teacher = new Teacher();
//					teacher.setLoginname(fields.get(0).replace(" ", ""));
					teacher.setUsername(fields.get(1).replace(" ", ""));
					teacher.setPosition(fields.get(2).replace(" ", ""));
					String sex=null;
					if(fields.get(3).contains("男")){
						sex="1";
					}
					else if(fields.get(3).contains("女")){
						sex="0";
					}
					teacher.setEmail(sex);
					teacher.setTelephone(fields.get(4).replace(" ", ""));
					teacher.setAddress(fields.get(5).replace(" ", ""));
					teacher.setEducation(fields.get(6).replace(" ", ""));
					teacher.setIdcard(fields.get(7).replace(" ", ""));
					if(fields.get(8).replace(" ", "").contentEquals("班主任")){
						teacher.setIdentify(6);
					}
					else{
						teacher.setIdentify(2);
					}
					teacherList.add(teacher);
					fields.clear();
				}  
				
			} catch (Exception e) {
				inputStream.close();
				out.print("<script>alert('导入的文件内容不符合格式，请按照模板写入');window.location.href='/teacher/list'</script>");
				out.flush();
				out.close();
				return "teacher-list";
			}
			teacherServiceImpl.importWorkTeacher(teacherList);
			inputStream.close();
			if(succ.contains("f")){
				if(repeatLine.trim().length()!=0){
					repeatLine+="行身份证号重复。";
				}
				if(nullValueLine.trim().length()!=0){
					nullValueLine+="行身份证号为空。";
				}
				out.print("<script>alert('部分数据导入失败,原因如下："+nullValueLine+repeatLine+"你可以在修改后重新导入这些记录');window.location.href='/teacher/list'</script>");
			}
			else{
				out.print("<script>alert('数据导入成功');window.location.href='/teacher/list'</script>");
			}
			out.flush();
			out.close();
			return "teacher-list";
		}
		//如果后缀名为xls,按照这种方式读取工作薄
		else if(postfix.equals("xls")){
			HSSFWorkbook  workbook = new HSSFWorkbook (inputStream);
			try {
				HSSFSheet  sheet = workbook.getSheetAt(0);
				for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++)  
				{  
					HSSFRow row = sheet.getRow(i);  
					for (int j = 0; j < row.getPhysicalNumberOfCells(); j++)  
					{  
						HSSFCell cell = row.getCell(j);  
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						String rawValue = cell.getStringCellValue();
						fields.add(rawValue);
					}  
					if(fields.size()==0){
						continue;
					}
					/*for (int d=0; d<fields.size(); d++) {
						String trim = fields.get(d).trim();
						if(trim.length()==0){
							succ="f";
							nullValueLine+=i+1+",";
							break;
						}
					}*/
					String trim = fields.get(7).trim();
					if(trim.length()==0){
						succ="f";
						nullValueLine+=i+1+",";
					}
					if(teacherServiceImpl.checkLoginName(fields.get(7).replace(" ", ""))){
						succ="f";
						repeatLine+=i+1+",";
					}
					if(succ.contains("f")){
						fields.clear();
						continue;
					}
					
					Teacher teacher = new Teacher();
//					teacher.setLoginname(fields.get(0).replace(" ", ""));
					teacher.setUsername(fields.get(1).replace(" ", ""));
					teacher.setPosition(fields.get(2).replace(" ", ""));
					String sex1=null;
					if(fields.get(3).contains("男")){
						sex1="1";
					}
					else if(fields.get(3).contains("女")){
						sex1="0";
					}
					teacher.setEmail(sex1);
					teacher.setTelephone(fields.get(4).replace(" ", ""));
					teacher.setAddress(fields.get(5).replace(" ", ""));
					teacher.setEducation(fields.get(6).replace(" ", ""));
					teacher.setIdcard(fields.get(7).replace(" ", ""));
					if(fields.get(8).replace(" ", "").contentEquals("班主任")){
						teacher.setIdentify(6);
					}
					else{
						teacher.setIdentify(2);
					}
					teacherList.add(teacher);
					fields.clear();
				}  
				
			} catch (Exception e) {
				inputStream.close();
				out.print("<script>alert('导入的文件内容不符合格式，请按照模板写入');window.location.href='/teacher/list'</script>");
				out.flush();
				out.close();
				return "teacher-list";
			}
			teacherServiceImpl.importWorkTeacher(teacherList);
			inputStream.close();
			if(succ.contains("f")){
				if(repeatLine.trim().length()!=0){
					repeatLine+="行身份证号重复。";
				}
				if(nullValueLine.trim().length()!=0){
					nullValueLine+="行身份证号为空。";
				}
				out.print("<script>alert('部分数据导入失败,原因如下："+nullValueLine+repeatLine+"你可以在修改后重新导入这些记录');window.location.href='/teacher/list'</script>");
			}
			else{
				out.print("<script>alert('数据导入成功');window.location.href='/teacher/list'</script>");
			}
			out.flush();
			out.close();
			return "teacher-list";
		}
		//如果没有符合的格式，则输出文件格式不正确
		inputStream.close();
		out.print("<script>alert('文件格式不正确');window.location.href='/teacher/list'</script>");
		out.flush();
		out.close();
		return "teacher-list";
	}
	/**
	 * 导出
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/export")
	public String export(Integer mould,String type,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		//准备需要遍历的Teacher
		List<Teacher> teacherList = teacherServiceImpl.getAllWorkTeacher(type);
		//准备表格第一行显示的域
		List<String> fields = new ArrayList<>();
		fields.add("登录名");
		fields.add("用户名");
		fields.add("职称");
		fields.add("性别");
		fields.add("电话");
		fields.add("地址");
		fields.add("学历");
		fields.add("身份证");
		fields.add("状态");
		fields.add("教师类别");
		fields.add("创建时间");
		fields.add("更新时间");
		DownUtils.exportReport(mould,request,response,null, "教师信息表", null, fields, teacherList, null, null, null, null, null, null);
		return null;
	}
}
