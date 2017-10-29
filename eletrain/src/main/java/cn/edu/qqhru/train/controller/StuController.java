package cn.edu.qqhru.train.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.Score;
import cn.edu.qqhru.train.pojo.Student;
import cn.edu.qqhru.train.service.ClassesService;
import cn.edu.qqhru.train.service.ScoreService;
import cn.edu.qqhru.train.service.StuService;
import cn.edu.qqhru.train.utils.DownUtils;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.StudentClassScoreVo;
import cn.edu.qqhru.train.vo.StudentExportVo;

@Controller
@RequestMapping("/student")
public class StuController {
	private String filename;
	@Autowired
	private StuService stuService;
	@Autowired
	private ClassesService classesService;
	@Autowired 
	private ScoreService scoreService;
	/**
	 * 查询所有学生
	 * @Description 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/allStu")
	public ModelAndView toAllStuPage(@RequestParam(value="page",defaultValue="1") int page, 
			@RequestParam(value="rows",defaultValue="10")int rows,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		PageBean<Student> pageBean = stuService.getAllStu(page, rows);
		modelAndView.addObject("url", "allStu?");
		modelAndView.addObject("pageBean", pageBean);
		modelAndView.setViewName("stu-list");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	/**
	 * 查询所有已毕业学生
	 * @Description 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/allFinishStu")
	public ModelAndView toAllFinishStuPage(@RequestParam(value="page",defaultValue="1") int page, 
			@RequestParam(value="rows",defaultValue="10")int rows,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		PageBean<Student> pageBean = stuService.getAllFinishStu(page, rows);
		modelAndView.addObject("url", "allFinishStu?");
		modelAndView.addObject("pageBean", pageBean);
		modelAndView.setViewName("stu-list");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	/**
	 * 查询所有未毕业学生
	 * @Description 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/allNotFinishStu")
	public ModelAndView toAllNotFinishStuPage(@RequestParam(value="page",defaultValue="1") int page, 
			@RequestParam(value="rows",defaultValue="10")int rows,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		PageBean<Student> pageBean = stuService.getAllNotFinishStu(page, rows);
		modelAndView.addObject("url", "allNotFinishStu?");
		modelAndView.addObject("pageBean", pageBean);
		modelAndView.setViewName("stu-list");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	/**
	 * 删除学生
	 * @Description 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delStu")
	public String delStu(@RequestParam(value="ids")int[] ids,Model model,HttpServletRequest request) {
		stuService.delStu(ids);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "stu-list";
	}
	/**
	 * 增加学生页面跳转
	 * @Description 
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(Model model,HttpServletRequest request) {
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "add-stu";
	}
	/**
	 * 增加学生
	 * @Description 
	 * @param stu
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/addStu")
	public String addStu(String headBase64Data,Student stu,Model model,HttpServletRequest request) throws IOException {
		byte[] decodeBuffer = new sun.misc.BASE64Decoder().decodeBuffer(headBase64Data);
		filename = UUID.randomUUID().toString().replace("-", "").toUpperCase() + ".jpg";
		FileOutputStream fileOutputStream = new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("/") + "images/" + filename));
		IOUtils.write(decodeBuffer, fileOutputStream);
		stu.setPicpath("images/" + filename);
		stu.setLoginname(UUID.randomUUID().toString().replace("-", "").toUpperCase());
		stuService.addStu(stu);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/student/allStu";
	}
	@RequestMapping("/checkNo")
	@ResponseBody
	public String check(String loginname) {
		boolean flag = stuService.checkNo(loginname);
		if (flag) {
			return "exist";
		}else {
			return "noexist";
		}
	}
	/**
	 * 编辑学生页面跳转
	 * @Description 
	 * @param id
	 * @return
	 */
	@RequestMapping("/editPage")
	public ModelAndView editPage(@RequestParam(value="id")int id,HttpServletRequest request) {
		Student stu = stuService.getStuOne(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("stuInfo", stu);
		modelAndView.setViewName("edit-stu");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	/**
	 * 编辑学生
	 * @Description 
	 * @param stu
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/editStu")
	public String editStu(Model model,HttpServletRequest request,Student stu,@RequestParam("file") CommonsMultipartFile file,HttpServletResponse response) throws Exception {
		String postfix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        // 判断没有文件上传
        if (file.getSize() == 0 && postfix == "") {
        	//stu.setPicpath("images/default.jpg");
        	stuService.editStu(stu,false);
        	response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>alert('修改成功');window.location.href='/student/allStu?menuId=0'</script>");
			out.flush();
			out.close();
			String menuId = request.getParameter("menuId");
			model.addAttribute("menuId", menuId);
			return "stu-list";
		}
		if (postfix.toLowerCase().equals("jpg") || postfix.toLowerCase().equals("jpeg")) {
			String filename = UUID.randomUUID().toString().replace("-", "").toUpperCase() + ".jpg";
			String path = request.getSession().getServletContext().getRealPath("/") + "images/" + filename ;  
			file.getFileItem().write(new File(path));
			stu.setPicpath("images/" + filename);
			String oldPath = stuService.editStu(stu,true);
			if (!oldPath.equals("images/default.jpg")) {
				String oldPathDisk = request.getSession().getServletContext().getRealPath("/") + oldPath ;  
				File fil = new File(oldPathDisk);
				fil.delete();
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>alert('修改成功');window.location.href='/student/allStu?menuId=0'</script>");
			out.flush();
			out.close();
			String menuId = request.getParameter("menuId");
			model.addAttribute("menuId", menuId);
			return "stu-list";
		}else {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>alert('上传的文件不是图片请重新上传');window.location.href='/student/editPage?menuId=0&id=" + stu.getStudentId().toString() + "'</script>");
			out.flush();
			out.close();
			String menuId = request.getParameter("menuId");
			model.addAttribute("menuId", menuId);
			return "edit-stu";
		}
	}
	/**
	 * 以学号搜索学生
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/searchByLoginname")
	public ModelAndView searchByLoginname(HttpServletRequest request,String loginname,@RequestParam(value="page",defaultValue="1") int page, @RequestParam(value="rows",defaultValue="10")int rows) throws UnsupportedEncodingException {
		String a = loginname;
		try {
			loginname = URLDecoder.decode(loginname,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView();
		PageBean<Student> pageBean = stuService.getStu(loginname, page, rows);
		modelAndView.addObject("pageBean", pageBean);
		modelAndView.addObject("url", "searchByLoginname?loginname=" + URLEncoder.encode(a,"iso8859-1"));
		modelAndView.addObject("loginname", loginname);
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		modelAndView.setViewName("stu-list");
		return modelAndView;
	}
	/**
	 * 初始化密码
	 */
	@RequestMapping("/initPassword")
	public String initPwd(int ids) {
		stuService.initPassword(ids);
		return "stu-list";
	}
	/**
	 * 查询学生详情
	 * @Description 
	 * @param id
	 * @return
	 */
	@RequestMapping("/stuInfo")
	public ModelAndView stuInfo(int id,HttpServletRequest request) {
		List<StudentClassScoreVo> studentClassScoreVo = new ArrayList<>();
		Student stu = stuService.getStuOne(id);
		List<Student> stuList = stuService.getStuByIdcard(stu.getIdcard());
		for (Student student : stuList) {
			StudentClassScoreVo stuVo = new StudentClassScoreVo();
			Classes classes = classesService.getClassesBYid(student.getClassesId());
			if (classes!=null) {
				stuVo.setClassName(classes.getCname());
				stuVo.setClassNo(classes.getClassesNo());
			}
			if (student.getIsfinish() == 0) {
				stuVo.setSign(0);
			}else {
				stuVo.setSign(1);
			}
			List<Score> scoreList = scoreService.getScoreListByStudentId(student.getStudentId());
			if (scoreList.size()>0 ) {
				if (scoreList.get(0).getTheoryscore()!=null) {
					stuVo.setTheoryscore(scoreList.get(0).getTheoryscore());
				}else {
					stuVo.setTheoryscore(0);
				}
				if (scoreList.get(0).getPracticescore()!=null) {
					stuVo.setPracticescore(scoreList.get(0).getPracticescore());
				}else {
					stuVo.setPracticescore(0);
				}
				if (scoreList.get(0).getTotal()!=null) {
					stuVo.setTotal(scoreList.get(0).getTotal());
				}else {
					stuVo.setTotal(0);
				}
				stuVo.setCertificateNo(scoreList.get(0).getCertificateno());
			}
			studentClassScoreVo.add(stuVo);
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("classScoreInfo", studentClassScoreVo);
		modelAndView.addObject("stuInfo", stu);
		modelAndView.setViewName("stu-info");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	/**
	 * 下载模板
	 */
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download1(int type,HttpServletRequest request) throws IOException {  
		if (type == 1) {
			filename = "学员信息录入表.xlsx";
		}else {
			filename = "学员信息录入表.xls";
		}
		String path = request.getSession().getServletContext().getRealPath("/") + "download/" + filename;  
		String header = request.getHeader("user-agent");
        return DownUtils.getResponseEntity(header,path, filename);
    }
	
	/**
	 * 导入
	 * @throws IOException 
	 */
	@RequestMapping("/upload")
	public String upload(Model model,HttpServletRequest request,@RequestParam("file") CommonsMultipartFile file,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		InputStream inputStream = file.getInputStream();
		List<String> fields = new ArrayList<>();
		List<Student> stuList = new ArrayList<>();
		String fileName = file.getOriginalFilename();
		String postfix=fileName.substring(fileName.lastIndexOf(".")+1);
		if (postfix.equals("xlsx")) {
			//1、读取工作簿
			XSSFWorkbook  workbook = new XSSFWorkbook (inputStream);
			try {
				//2、读取第一个工作表
				XSSFSheet  sheet = workbook.getSheetAt(0);
				for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++)  
				{  
					XSSFRow row = sheet.getRow(i);  
					for (int j = 0; j < sheet.getRow(1).getLastCellNum(); j++)  
					{  
						XSSFCell cell = row.getCell(j);  
						if (cell==null) {
							fields.add("");
							
						}else {
							cell.setCellType(XSSFCell.CELL_TYPE_STRING);
							String rawValue = cell.getStringCellValue();
								fields.add(rawValue);
							
						}
					}  
					if (fields.size()>0 && !fields.get(0).replace(" ", "").equals("") &&  fields.get(3).matches("^([0-9]){7,18}(x|X)?$") && fields.get(2).matches("^1[34578]\\d{9}$")) {
						Student stu = new Student();
						stu.setLoginname(UUID.randomUUID().toString().replace("-", "").toUpperCase());
						stu.setUsername(fields.get(0).replace(" ", ""));
						if (fields.get(1).replace(" ", "").equals("男") || fields.get(2).replace(" ", "").equals("女")) {
							stu.setEmail(fields.get(1).replace(" ", ""));
						}else {
							int c = 1/0;
						}
						stu.setTelephone(fields.get(2).replace(" ", ""));
						stu.setIdcard(fields.get(3).replace(" ", ""));
						if (fields.get(4).replace(" ", "").equals("")) {
							stu.setEducation("未填");
						}else {
							stu.setEducation(fields.get(4).replace(" ", ""));
						}
						if (fields.get(5).replace(" ", "").equals("")) {
							stu.setAddress("未填");
						}else {
							stu.setAddress(fields.get(5).replace(" ", ""));
						}
						stuList.add(stu);
						fields.clear();
					}else {
						int a = 1/0;
						break;
					}
				}  
				
			} catch (Exception e) {
				inputStream.close();
				out.print("<script>alert('导入的文件内容不符合格式，请按照模板写入');window.location.href='/student/allStu?menuId=0'</script>");
				out.flush();
				out.close();
				String menuId = request.getParameter("menuId");
				model.addAttribute("menuId", menuId);
				return "stu-list";
			}
			stuService.importStu(stuList);
			inputStream.close();
			out.print("<script>alert('导入成功');window.location.href='/student/allStu?menuId=0'</script>");
			out.flush();
			out.close();
			String menuId = request.getParameter("menuId");
			model.addAttribute("menuId", menuId);
			return "stu-list";
		}else if(postfix.equals("xls")){
			//1、读取工作簿
			HSSFWorkbook  workbook = new HSSFWorkbook (inputStream);
			try {
				//2、读取第一个工作表
				HSSFSheet  sheet = workbook.getSheetAt(0);
				for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++)  
				{  
					HSSFRow row = sheet.getRow(i);  
					for (int j = 0; j < sheet.getRow(1).getLastCellNum(); j++)  
					{  
						HSSFCell cell = row.getCell(j);  
						if (cell==null) {
							fields.add("");
							
						}else {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							String rawValue = cell.getStringCellValue();
							
								fields.add(rawValue);
							
						}
					}  
					if (fields.size()>0 && !fields.get(0).replace(" ", "").equals("") && fields.get(3).matches("^([0-9]){7,18}(x|X)?$") && fields.get(2).matches("^1[34578]\\d{9}$")) {
						Student stu = new Student();
						stu.setLoginname(UUID.randomUUID().toString().replace("-", "").toUpperCase());
						stu.setUsername(fields.get(0).replace(" ", ""));
						if (fields.get(1).replace(" ", "").equals("男") || fields.get(2).replace(" ", "").equals("女")) {
							stu.setEmail(fields.get(1).replace(" ", ""));
						}else {
							int c = 1/0;
						}
						stu.setTelephone(fields.get(2).replace(" ", ""));
						stu.setIdcard(fields.get(3).replace(" ", ""));
						if (fields.get(4).replace(" ", "").equals("")) {
							stu.setEducation("未填");
						}else {
							stu.setEducation(fields.get(4).replace(" ", ""));
						}
						if (fields.get(5).replace(" ", "").equals("")) {
							stu.setAddress("未填");
						}else {
							stu.setAddress(fields.get(5).replace(" ", ""));
						}
						stuList.add(stu);
						fields.clear();
					}else {
						int a = 1/0;
						break;
					}
				}  
				
			} catch (Exception e) {
				inputStream.close();
				out.print("<script>alert('导入的文件内容不符合格式，请按照模板写入');window.location.href='/student/allStu?menuId=0'</script>");
				out.flush();
				out.close();
				String menuId = request.getParameter("menuId");
				model.addAttribute("menuId", menuId);
				return "stu-list";
			}
			stuService.importStu(stuList);
			inputStream.close();
			out.print("<script>alert('导入成功');window.location.href='/student/allStu?menuId=0'</script>");
			out.flush();
			out.close();
			String menuId = request.getParameter("menuId");
			model.addAttribute("menuId", menuId);
			return "stu-list";
		}
		inputStream.close();
		out.print("<script>alert('文件格式不正确');window.location.href='/student/allStu?menuId=0'</script>");
		out.flush();
		out.close();
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "stu-list";
	}
	/**
	 * 导出Xls
	 */
	@RequestMapping("/exportFile")
	public ModelAndView exportFile(String filename,Integer type,Integer isFinish,Integer classesId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String a = filename;
		try {
			filename = URLDecoder.decode(filename,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<StudentExportVo> exportStu = stuService.getExportStu(classesId,isFinish);
		List<String> fields = new ArrayList<>();
		if (isFinish==0) {
			fields.add("姓名");
			fields.add("性别");
			fields.add("电话");
			fields.add("单位");
			fields.add("学历");
			fields.add("身份证");
			DownUtils.exportReport(type,request, response,null, filename,null,fields, null, null, null,null,null,exportStu,isFinish);
		}else if(isFinish==1) {
			fields.add("姓名");
			fields.add("性别");
			fields.add("电话");
			fields.add("单位");
			fields.add("学历");
			fields.add("身份证");
			fields.add("班级名称");
			fields.add("理论成绩");
			fields.add("实践成绩");
			fields.add("总分");
			fields.add("证书编号");
			DownUtils.exportReport(type,request, response,null, filename,null,fields, null, null, null,null,null,exportStu,isFinish);
		}
		return null;
	}
	/**
	 * 导出选择班级
	 */
	@RequestMapping("/allNoFinishClasses")
	@ResponseBody
	public List<Classes> getAllFinishClasses(){
		List<Classes> allNoFinishClass = stuService.getAllNoFinishClass();
		return allNoFinishClass;
	}
	/**
	 * 导出选择班级
	 */
	@RequestMapping("/allFinishClasses")
	@ResponseBody
	public List<Classes> getAllNoFinishClasses(){
		List<Classes> allFinishClass = stuService.getAllFinishClass();
		return allFinishClass;
	}
}
