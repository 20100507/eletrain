package cn.edu.qqhru.train.controller;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.service.AdminService;
import cn.edu.qqhru.train.utils.MD5Utils;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.DeleteMultipleObjects;
import sun.misc.BASE64Encoder;

/**
 * 
 * <p>Title: AdminController</p>
 * <p>Description: 管理员模块的Controller</p>
 * <p>School: qiqihar university</p> 
 * @author	白鹏飞
 * @date	2017年9月1日下午2:52:07
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	/*@RequestMapping("/getAdminList")
	public String getAdminList(@RequestParam(value="page",defaultValue="1") int page, 
			@RequestParam(value="rows",defaultValue="8") int rows, Model model){
		List<Admin> adminList = adminService.getAdminList();
		return "admin-list";
	}*/
	
	/**
	 * <p>Title: getAdminListByPage</p>
	 * <p>Description: 以分页的方式显示所有管理员信息</p>
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	@RequestMapping("/getAdminListByPage")
	public String getAdminListByPage(@RequestParam(value="page",defaultValue="1") int page, 
										@RequestParam(value="rows",defaultValue="8") int rows,
										Model model,HttpServletRequest request){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		PageBean<Admin> pageBean = adminService.getAdminListPageBean(page, rows);
		String path = "getAdminListByPage?menuId="+menuId;
		model.addAttribute("path", path);
		model.addAttribute("pageBean", pageBean);
		return "admin-list";
	}
	
	/**
	 * <p>Title: getAdminListByPageAndCondition</p>
	 * <p>Description: 分页显示搜索出来的管理员信息</p>
	 * @param page
	 * @param rows
	 * @param condition
	 * @param model
	 * @return
	 */
	@RequestMapping("/getAdminListByPageAndCondition")
	public String getAdminListByPageAndCondition(@RequestParam(value="page",defaultValue="1") int page, 
													@RequestParam(value="rows",defaultValue="8") int rows, 
													@RequestParam String condition, Model model, HttpServletRequest request){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		if(condition == null || condition == ""){
			return "redirect:/admin/getAdminListByPage?page="+page;
		}
		
		PageBean<Admin> pageBean = adminService.getAdminListByPageAndCondition(condition, page, rows);
		String path = "getAdminListByPageAndCondition?menuId="+menuId+"&condition="+condition;
		model.addAttribute("path", path);
		model.addAttribute("pageBean", pageBean);
		return "admin-list";
	}
	
	/**
	 * <p>Title: toAddAdmin</p>
	 * <p>Description: 跳转到add-admin.jsp页面</p>
	 * @return
	 */
	@RequestMapping("/toAddAdmin")
	public String toAddAdmin(HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		return "add-admin";
	}
	
	/**
	 * <p>Title: addAdmin</p>
	 * <p>Description: 新增管理员模块</p>
	 * @param admin
	 * @return
	 */
	@RequestMapping("/addAdmin")
	public String addAdmin(Admin admin, HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		if(StringUtils.hasText(admin.getLoginname()) && StringUtils.hasText(admin.getUsername())){
			if(admin.getIdentify() == 1){
				admin.setEmail("管理员");
			}else if(admin.getIdentify() == 3){
				admin.setEmail("部门负责人");
			}else if(admin.getIdentify() == 4){
				admin.setEmail("培训管理部");
			}else if(admin.getIdentify() == 5){
				admin.setEmail("主管主任");
			}
			admin.setPassword("123456");
			boolean flag = adminService.addAdmin(admin);
			if(flag){
				return "redirect:/admin/getAdminListByPage?menuId"+menuId;
			}
		}
		return "add-admin";
	}
	
	/**
	 * <p>Title: toUpdateAdmin</p>
	 * <p>Description: 跳转到update-admin.jsp页面</p>
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/toUpdateAdmin")
	public String toUpdateAdmin(@RequestParam Integer id, Model model, HttpServletRequest request){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		Admin admin = adminService.getAdmin(id);
		model.addAttribute("admin", admin);
		return "update-admin";
	}
	
	/**
	 * <p>Title: updateAdmin</p>
	 * <p>Description: 修改管理员模块</p>
	 * @param admin
	 * @return
	 */
	@RequestMapping("/updateAdmin")
	public String updateAdmin(Admin admin, HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		if(StringUtils.hasText(admin.getLoginname()) && StringUtils.hasText(admin.getUsername())){
			boolean flag = adminService.updateAdmin(admin);
			if(flag){
				return "redirect:/admin/getAdminListByPage";
			}
		}
		return "update-admin";
	}
	
	/**
	 * <p>Title: deleteAdmin</p>
	 * <p>Description: 删除管理员模块</p>
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteAdmin")
	public String deleteAdmin(@RequestParam Integer id, HttpServletRequest request, Model model,
							@RequestParam(value="page",defaultValue="1") int page){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		boolean flag = adminService.deleteAdmin(id);
		if(flag){
			
		}else {
			
		}
		return "redirect:/admin/getAdminListByPage?page="+page;
	}
	
	/**
	 * <p>Title: deleteMultipleAdmins</p>
	 * <p>Description: 批量删除管理员模块</p>
	 * @param deleteMultipleObjects
	 * @return
	 */
	@RequestMapping("/deleteMultipleAdmins")
	public String deleteMultipleAdmins(DeleteMultipleObjects deleteMultipleObjects, HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		if(deleteMultipleObjects.getIds() != null){
			adminService.deleteMultipleAdmins(deleteMultipleObjects.getIds());
		}
		return "redirect:/admin/getAdminListByPage";
	}
	
	/**
	 * <p>Title: initializePassword</p>
	 * <p>Description: 初始化密码模块</p>
	 * @param id
	 * @return
	 */
	@RequestMapping("/initializePassword")
	@ResponseBody
	public boolean initializePassword(@RequestBody String id){
		boolean flag = adminService.initializePassword(id);
		return flag;
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
	public ResponseEntity<byte[]> downloadTemplate(String format, HttpServletRequest request) throws Exception{
		String filename = "管理员信息表"+format;
		String path = request.getSession().getServletContext().getRealPath("/download/");
		File file = new File(path+File.separator+filename);
		HttpHeaders headers = new HttpHeaders();
		String downloadFileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
		headers.setContentDispositionFormData("attachment", downloadFileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}
	
	/**
	 * <p>Title: importData</p>
	 * <p>Description: 导入文件模块</p>
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importData")
	public String importData(MultipartFile file) throws Exception{
		String path = "";
		String suffix = "";
		if(file.getSize() !=0 ){
			path = file.getOriginalFilename();
			suffix = path.substring(path.lastIndexOf(".")+1);
		}
		
		InputStream inputStream = file.getInputStream();
		
		List<Admin> adminList = new ArrayList<Admin>();
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
		    		switch (cell.getCellType()) {
					case 0:
						field.add(String.valueOf(cell.getNumericCellValue()));
						break;
					case 1:
						field.add(cell.getStringCellValue());
						break;

					}
		    	}
		    	Admin admin = new Admin();
		    	admin.setLoginname(field.get(0));
		    	admin.setUsername(field.get(1));
		    	admin.setPassword(MD5Utils.md5("123456"));
		    	admin.setEmail(field.get(2));
		    	admin.setIdentify(1);
		    	adminList.add(admin);
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
						field.add(String.valueOf(cell.getNumericCellValue()));
						break;
					case 1:
						field.add(cell.getStringCellValue());
						break;

					}
		    	}
		    	Admin admin = new Admin();
		    	admin.setLoginname(field.get(0));
		    	admin.setUsername(field.get(1));
		    	admin.setPassword(MD5Utils.md5("123456"));
		    	admin.setEmail(field.get(2));
		    	admin.setIdentify(1);
		    	adminList.add(admin);
		    	field.clear();
		    }
		}
		
	    adminService.addMultipleAdmins(adminList);
	    return "redirect:/admin/getAdminListByPage";
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
		    HSSFSheet sheet = wb.createSheet("管理员");//创建Excel工作表对象   
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
		    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2)); //合并单元格
		    //得到所有区域      
		    //sheet.getNumMergedRegions(); 
		    row = sheet.createRow(0);
		    cell = row.createCell(0);
		    cell.setCellValue("管理员信息表");
		    cell.setCellStyle(cellStyle);
		    row.setHeight((short) (30*20));
		    
		    //第二行设置
		    String []name = {"登录账号","姓名","邮箱"};
	    	row = sheet.createRow(1);
	    	row.setHeight((short) (30*20));
		    for(int i=0; i<3; i++){
		    	cell = row.createCell(i);
		    	cell.setCellValue(name[i]);
		    	cell.setCellStyle(cellStyle);
		    	sheet.setColumnWidth(i, 10 * 512);
		    } 
		    
		    //第三行及以后设置
		    List<Admin> adminList = adminService.getAdminList();
		    int i = 2;
		    for(Admin admin : adminList){
		    	row = sheet.createRow(i); //创建Excel工作表的行  
		    	HSSFCell cell_0 = row.createCell(0);
		    	HSSFCell cell_1 = row.createCell(1);
		    	HSSFCell cell_2 = row.createCell(2);
		    	
		    	cell_0.setCellValue(admin.getLoginname());
		    	cell_0.setCellStyle(cellStyle);
		    	
		    	cell_1.setCellValue(admin.getUsername());
		    	cell_1.setCellStyle(cellStyle);
		    	
		    	cell_2.setCellValue(admin.getEmail());
		    	cell_2.setCellStyle(cellStyle);
		    	i++;
		    }
		    
		  //============选择下载路径===========
		    ServletOutputStream outputStream = response.getOutputStream();
		    try {
		    	String fileName = "管理员信息."+format;  
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
		    XSSFSheet sheet = wb.createSheet("管理员");//创建Excel工作表对象   
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
		    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2)); //合并单元格
		    //得到所有区域      
		    //sheet.getNumMergedRegions(); 
		    row = sheet.createRow(0);
		    cell = row.createCell(0);
		    cell.setCellValue("管理员信息表");
		    cell.setCellStyle(cellStyle);
		    row.setHeight((short) (30*20));
		    
		    //第二行设置
		    String []name = {"登录账号","姓名","邮箱"};
	    	row = sheet.createRow(1);
	    	row.setHeight((short) (30*20));
		    for(int i=0; i<3; i++){
		    	cell = row.createCell(i);
		    	cell.setCellValue(name[i]);
		    	cell.setCellStyle(cellStyle);
		    	sheet.setColumnWidth(i, 10 * 512);
		    } 
		    
		    //第三行及以后设置
		    List<Admin> adminList = adminService.getAdminList();
		    int i = 2;
		    for(Admin admin : adminList){
		    	row = sheet.createRow(i); //创建Excel工作表的行  
		    	XSSFCell cell_0 = row.createCell(0);
		    	XSSFCell cell_1 = row.createCell(1);
		    	XSSFCell cell_2 = row.createCell(2);
		    	
		    	cell_0.setCellValue(admin.getLoginname());
		    	cell_0.setCellStyle(cellStyle);
		    	
		    	cell_1.setCellValue(admin.getUsername());
		    	cell_1.setCellStyle(cellStyle);
		    	
		    	cell_2.setCellValue(admin.getEmail());
		    	cell_2.setCellStyle(cellStyle);
		    	i++;
		    }
		    
		    //============选择下载路径===========
		    ServletOutputStream outputStream = response.getOutputStream();
		    try {
		    	String fileName = "管理员信息."+format;  
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
	}
}
