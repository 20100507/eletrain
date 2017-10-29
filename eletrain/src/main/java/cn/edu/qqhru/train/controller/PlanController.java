package cn.edu.qqhru.train.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.Book;
import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.Device;
import cn.edu.qqhru.train.pojo.Plan;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.service.AdminService;
import cn.edu.qqhru.train.service.BookService;
import cn.edu.qqhru.train.service.ClassesService;
import cn.edu.qqhru.train.service.DevicesService;
import cn.edu.qqhru.train.service.PlanService;
import cn.edu.qqhru.train.service.TeacherService;
import cn.edu.qqhru.train.utils.DownUtils;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.PlanVo;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Controller
@RequestMapping("/plan")
public class PlanController {
	private String filename;
	@Autowired
	private PlanService planService;
	@Autowired
	private DevicesService devicesService;
	@Autowired
	private BookService bookService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private ClassesService classesService;
	/**
	 * 获取所有方案
	 * @Description 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/getAllPlan")
	public ModelAndView getAllPlan(@RequestParam(value="page",defaultValue="1") int page, 
			@RequestParam(value="rows",defaultValue="10")int rows,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		PageBean<PlanVo> pageBean = planService.getAllPlan(page, rows);
		modelAndView.addObject("url", "getAllPlan?");
		modelAndView.addObject("pageBean", pageBean);
		modelAndView.setViewName("plan-list");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	/**
	 * 校验方案名称
	 */
	@RequestMapping("/checkPname")
	@ResponseBody
	public String check(String pname) {
		boolean flag = planService.checkPname(pname);
		if (flag) {
			return "exist";
		}else {
			return "noexist";
		}
	}
	/**
	 * 查询方案详情
	 * @Description 
	 * @param id
	 * @return
	 */
	@RequestMapping("/planInfo")
	public ModelAndView stuInfo(int id,HttpServletRequest request) {
		PlanVo planVo = planService.getPlanOne(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("planInfo", planVo);
		modelAndView.setViewName("plan-info");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	/**
	 * 删除方案
	 * @Description 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delPlan")
	public String delStu(@RequestParam(value="ids")int[] ids,Model model,HttpServletRequest request) {
		List<Plan> list = planService.getPlanByIds(ids);
		for (Plan plan : list) {
			String path = request.getSession().getServletContext().getRealPath("/") + "resource/" + plan.getPname();
			File file = new File(path);
			if(file.exists()){
				     deleteFile(file);
			}
		}
		planService.delPlan(ids);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "plan-list";
	}
	private void deleteFile(File file){
	      if(file.isDirectory()){
	           File[] files = file.listFiles();
	           for(int i=0; i<files.length; i++){
	                deleteFile(files[i]);
	           }
	      }
	      file.delete();
		 }
	/**
	 * 获取所有管理员
	 * @Description 
	 * @return
	 */
	@RequestMapping("/findAdminList")
	@ResponseBody
	public List<Admin> findAdminList(){
		List<Admin> adminList = planService.getAdminList();
		return adminList;
	}
	/**
	 * 获取所有班级
	 * @Description 
	 * @return
	 */
	@RequestMapping("/findClassesList")
	@ResponseBody
	public List<Classes> findClassesList(){
		List<Classes> list = planService.getClassesList();
		return list;
	}
	/**
	 * 获取所有设备
	 * @Description 
	 * @return
	 */
	@RequestMapping("/findDevicesList")
	@ResponseBody
	public List<Device> findDevicesList(){
		 List<Device> list = devicesService.getExportDev();
		return list;
	}
	/**
	 * 获取所有教材
	 * @Description 
	 * @return
	 */
	@RequestMapping("/findBookList")
	@ResponseBody
	public List<Book> findBookList(){
		List<Book> list = bookService.getExportBook();
		return list;
	}
	/**
	 * 获取所有教师
	 * @Description 
	 * @return
	 */
	@RequestMapping("/findTeaList")
	@ResponseBody
	public List<Teacher> findTeaList(){
		List<Teacher> list = planService.getTeacherList();
		return list;
	}
	/**
	 * 添加方案页面跳转
	 */
	@RequestMapping("/addPlanPage")
	public String addPlanPage(Model model,HttpServletRequest request) {
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "add-plan";
	}
	/**
	 * 添加方案
	 */
	@RequestMapping("/addPlan")
	public String addPlan(Model model,HttpServletRequest request,Plan plan,Integer[] classIds,Integer[] deviceIds,Integer[] bookIds,Integer[] teaIds) {
		planService.addPlan(plan, classIds, deviceIds, bookIds, teaIds);
		String path = request.getSession().getServletContext().getRealPath("/") + "resource/" + plan.getPname();
		File file = new File(path);
		file.mkdir();
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/plan/getAllPlan";
	}
	/**
	 * 编辑方案页面跳转
	 */
	@RequestMapping("/editPlanPage")
	public ModelAndView editPlanPage(@RequestParam(value="id")int id,HttpServletRequest request) {
		PlanVo plan = planService.getPlanOne(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("planInfo", plan);
		modelAndView.setViewName("edit-plan");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	/**
	 * 编辑方案
	 * @throws IOException 
	 */
	@RequestMapping("/editPlan")
	public String editPlan(Model model,HttpServletRequest request,HttpServletResponse response,Plan plan,Integer[] classIds,Integer[] deviceIds,Integer[] bookIds,Integer[] teaIds) throws IOException {
		PlanVo planVo = planService.getPlanOne(plan.getPlanId());
		String oldPath = request.getSession().getServletContext().getRealPath("/") + "resource/" + planVo.getPname();
		String newPath = request.getSession().getServletContext().getRealPath("/") + "resource/" + plan.getPname();
		File file = new File(oldPath);
		file.renameTo(new File(newPath));
		planService.editPlan(plan, classIds, deviceIds, bookIds, teaIds);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('修改成功');window.location.href='/plan/getAllPlan?menuId=7'</script>");
		out.flush();
		out.close();
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "plan-list"; 
	}
	/**
	 * 以方案名称搜索设备
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/searchByPlanName")
	public ModelAndView searchByPlanName(HttpServletRequest request,String pname,@RequestParam(value="page",defaultValue="1") int page, @RequestParam(value="rows",defaultValue="10")int rows) throws UnsupportedEncodingException {
		String a = pname;
		try {
			pname = URLDecoder.decode(pname,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView();
		PageBean<PlanVo> pageBean = planService.getPlan(pname, page, rows);
		modelAndView.addObject("pageBean", pageBean);
		modelAndView.addObject("url", "searchByPlanName?menuId=7&pname=" + URLEncoder.encode(a,"iso8859-1"));
		modelAndView.addObject("pname", pname);
		modelAndView.setViewName("plan-list");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	/**
	 * 导出
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws MalformedTemplateNameException 
	 * @throws TemplateNotFoundException 
	 * @throws TemplateException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/exportFile")
	@ResponseBody
	public List<String> exportFile(int planId,HttpServletRequest request,HttpServletResponse response) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		Map map = new HashMap<>();
		List<Teacher> teachers = new ArrayList<>();
		List<Device> devices = new ArrayList<>();
		List<Book> books = new ArrayList<>();
		List<Classes> classes = new ArrayList<>();
		PlanVo planVo = planService.getPlanOne(planId);
		List<Integer> teacherIds = planVo.getTeacherIds();
		for (Integer integer : teacherIds) {
			teachers.add(teacherService.getTeacherBYid(integer));
		}
		List<Integer> devIds = planVo.getDevIds();
		for (Integer integer : devIds) {
			devices.add(devicesService.getDevOne(integer));
		}
		List<Integer> bookIds = planVo.getBookIds();
		for (Integer integer : bookIds) {
			books.add(bookService.getBookOne(integer));
		}
		List<Integer> classesIds = planVo.getClassesIds();
		for (Integer integer : classesIds) {
			classes.add(classesService.getClassesBYid(integer));
		}
		map.put("planName", planVo.getPname());
		map.put("adminName", planVo.getAdminName());
		
		if (planVo.getSign() == 0) {
			map.put("name1","");
			map.put("name2","");
			map.put("name3","");
			map.put("sign", "未审批");
		}else if (planVo.getSign() == 1) {
			map.put("name1","");
			map.put("name2","");
			map.put("name3","");
			map.put("sign", "审批中");
		}else if (planVo.getSign() == 2) {
			map.put("name1","");
			map.put("name2","");
			map.put("name3","");
			map.put("sign", "未通过");
		}else {
			map.put("name1",planService.getUsernameByIdentify(3));
			map.put("name2",planService.getUsernameByIdentify(4));
			map.put("name3",planService.getUsernameByIdentify(5));
			map.put("sign", "通过");
		}
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
	    String dateString = formatter.format(planVo.getCreatetime());
		map.put("time", dateString);
		map.put("planObject", planVo.getClassesNames());
		map.put("planAim", planVo.getPlanAim());
		map.put("ability", planVo.getAbility());
		map.put("scale", planVo.getScale());
		map.put("classInfo", classes);
		map.put("planPattern", planVo.getPlanPattern());
		map.put("examPattern", planVo.getExamPattern());
		map.put("teacherInfo", teachers);
		map.put("deviceInfo", devices);
		map.put("bookInfo", books);
		map.put("planContent", planVo.getPlanContent());
		map.put("planRequirement", planVo.getPlanRequirement());
		map.put("cdvList", classesService.getPackageSyllabusByclassesId(planVo.getClassesIds().get(0)));
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setDirectoryForTemplateLoading(new File(request.getSession().getServletContext().getRealPath("/") + "download/"));
		Template template = configuration.getTemplate("培训方案.ftl");
		String fileName1 = "培训方案.doc";
		File outFile = new File(request.getSession().getServletContext().getRealPath("/") + "resource/" + planVo.getPname() + "/" +fileName1);
		 Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
		 template.process(map, out);
		 out.flush();
         out.close();
         String path = request.getSession().getServletContext().getRealPath("/") + "resource/" + planVo.getPname() + "/";
         File f = new File(path);
         File[] fa = f.listFiles();
         List<String> filenames = new ArrayList<>();
         for (File file : fa) {
        	 filenames.add(file.getAbsolutePath());
		}
         
		return filenames;
	}
	/**
	 * 下载方案相关文档（空白）
	 */
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download1(int type,HttpServletRequest request) throws IOException {  
		if (type == 1) {
			filename = "培训项目需求调查方案.doc";
		}else if(type == 2){
			filename = "培训需求说明书.doc";
		}else if (type == 3) {
			filename = "培训需求调研报告.doc";
		}else {
			filename = "培训课程大纲.doc";
		}
		String path = request.getSession().getServletContext().getRealPath("/") + "download/" + filename;  
		String header = request.getHeader("user-agent");
        return DownUtils.getResponseEntity(header,path, filename);
    }
	/**
	 * 培训方案相关文档的上传
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/upload")
	public String upload(Model model,HttpServletRequest request,HttpServletResponse response,@RequestParam("files")MultipartFile[] files,String planName) throws IllegalStateException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		for (MultipartFile multipartFile : files) {
			if (multipartFile.getSize() != 0) {
				String fileName = multipartFile.getOriginalFilename();
				String postfix=fileName.substring(fileName.lastIndexOf(".")+1);
				if (!(postfix.equals("doc") || postfix.equals("docx"))) {
					out.print("<script>alert('有文件格式不正确，或未上传文件');window.location.href='/plan/getAllPlan?menuId=7'</script>");
					out.flush();
					out.close();
					String menuId = request.getParameter("menuId");
					model.addAttribute("menuId", menuId);
					return "plan-list";
				}
			}
		}
		String path = request.getSession().getServletContext().getRealPath("/") + "resource/" + planName + "/";
		for (MultipartFile multipartFile : files) {
			if (multipartFile.getSize() != 0) {
				File file1 = new File(path+multipartFile.getOriginalFilename());
				multipartFile.transferTo(file1);
			}
		}
		out.print("<script>alert('上传成功');window.location.href='/plan/getAllPlan?menuId=7'</script>");
		out.flush();
		out.close();
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "plan-list";
	}
	/**
	 * 下载方案相关文档
	 * @throws IOException 
	 */
	@RequestMapping("/downloadAllFile")
	public ResponseEntity<byte[]> download2(HttpServletRequest request,String path) throws IOException{
		String pathNew = URLDecoder.decode(path,"UTF-8");
		filename = pathNew.substring(pathNew.lastIndexOf("\\")+1);
		String header = request.getHeader("user-agent");
        return DownUtils.getResponseEntity(header,pathNew, filename);
	}
	
	//=====================================添加=========================================
	@RequestMapping("/submitApp")
	@ResponseBody
	public String submitApp(Integer id,HttpServletRequest request){
		Object user = request.getSession().getAttribute("user");
		try{
			Admin admin;
			if(user instanceof Admin){
				admin = (Admin) user;
				planService.submitApp(admin, id);
			}
			return "success"; 
		}catch(Exception e){
			return "error";
		}
	}
}
