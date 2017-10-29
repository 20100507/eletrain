package cn.edu.qqhru.train.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.ModelAndView;

import cn.edu.qqhru.train.pojo.Device;
import cn.edu.qqhru.train.service.DevicesService;
import cn.edu.qqhru.train.utils.DownUtils;
import cn.edu.qqhru.train.utils.PageBean;

@Controller
@RequestMapping("/devices")
public class DevicesController {
	private String filename;
	@Autowired
	private DevicesService devicesService;
	/**
	 * 查询所有设备
	 * @Description 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/getAllDevices")
	public ModelAndView getAllDevices(@RequestParam(value="page",defaultValue="1") int page,
			@RequestParam(value="rows",defaultValue="10")int rows,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		PageBean<Device> pageBean = devicesService.getAllDev(page, rows);
		modelAndView.addObject("url", "getAllDevices?");
		modelAndView.addObject("pageBean", pageBean);
		modelAndView.setViewName("dev-list");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	/**
	 * 删除设备
	 * @Description 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delDev")
	public String delStu(@RequestParam(value="ids")int[] ids,Model model,HttpServletRequest request) {
		devicesService.delDev(ids);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "dev-list";
	}
	/**
	 * 增加设备页面跳转
	 * @Description 
	 * @return
	 */
	@RequestMapping("/addDevPage")
	public String addPage(Model model,HttpServletRequest request) {
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "add-dev";
	}
	/**
	 * 增加设备
	 * @Description 
	 * @param stu
	 * @return
	 */
	@RequestMapping("/addDev")
	public String addDev(Device dev,Model model,HttpServletRequest request) {
		devicesService.addDev(dev);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/devices/getAllDevices";
	}
	/**
	 * 编辑设备页面跳转
	 * @Description 
	 * @param id
	 * @return
	 */
	@RequestMapping("/editDevPage")
	public ModelAndView editPage(@RequestParam(value="id")int id,HttpServletRequest request) {
		Device dev = devicesService.getDevOne(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("devInfo", dev);
		modelAndView.setViewName("edit-dev");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	/**
	 * 编辑设备
	 * @Description 
	 * @param stu
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/editDev")
	public String editStu(Model model,HttpServletRequest request,Device dev,HttpServletResponse response) throws Exception {
		devicesService.editDev(dev);
    	response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('修改成功');window.location.href='/devices/getAllDevices?menuId=7'</script>");
		out.flush();
		out.close();
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "dev-list";
	}
	/**
	 * 以设备号搜索设备
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/searchByDeviceName")
	public ModelAndView searchByLoginname(HttpServletRequest request,String dname,@RequestParam(value="page",defaultValue="1") int page, @RequestParam(value="rows",defaultValue="10")int rows) throws UnsupportedEncodingException {
		String a = dname;
		try {
			dname = URLDecoder.decode(dname,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView();
		PageBean<Device> pageBean = devicesService.getDev(dname, page, rows);
		modelAndView.addObject("pageBean", pageBean);
		modelAndView.addObject("url", "searchByDeviceName?dname=" + URLEncoder.encode(a,"iso8859-1"));
		modelAndView.addObject("dname", dname);
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		modelAndView.setViewName("dev-list");
		return modelAndView;
	}
	/**
	 * 查询设备详情
	 * @Description 
	 * @param id
	 * @return
	 */
	@RequestMapping("/devInfo")
	public ModelAndView stuInfo(int id,HttpServletRequest request) {
		Device device = devicesService.getDevOne(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("devInfo", device);
		modelAndView.setViewName("dev-info");
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
			filename = "设备信息录入表.xlsx";
		}else {
			filename = "设备信息录入表.xls";
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
		List<Device> devList = new ArrayList<>();
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
					for (int j = 0; j < row.getPhysicalNumberOfCells(); j++)  
					{  
						XSSFCell cell = row.getCell(j);  
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						String rawValue = cell.getStringCellValue();
						if (!rawValue.equals("")) {
							fields.add(rawValue);
						}
					}  
					if (fields.size()==5) {
					Device dev = new Device();
					dev.setDname(fields.get(0).replace(" ", ""));
					dev.setDcount(Integer.parseInt(fields.get(1).replace(" ", "")));
					dev.setInfo(fields.get(2).replace(" ", ""));
					dev.setTool(fields.get(3).replace(" ", ""));
					dev.setTcount(Integer.parseInt(fields.get(4).replace(" ", "")));
					devList.add(dev);
					fields.clear();
					}
					if (fields.size()==2) {
					Device dev = new Device();
					dev.setDname(fields.get(0).replace(" ", ""));
					dev.setDcount(Integer.parseInt(fields.get(1).replace(" ", "")));
					devList.add(dev);
					fields.clear();
					}
					if (fields.size()==3) {
					Device dev = new Device();
					dev.setDname(fields.get(0).replace(" ", ""));
					dev.setDcount(Integer.parseInt(fields.get(1).replace(" ", "")));
					dev.setInfo(fields.get(2).replace(" ", ""));
					devList.add(dev);
					fields.clear();
					}
				}  
				
			} catch (Exception e) {
				inputStream.close();
				out.print("<script>alert('导入的文件内容不符合格式，请按照模板写入');window.location.href='/devices/getAllDevices?menuId=7'</script>");
				out.flush();
				out.close();
				String menuId = request.getParameter("menuId");
				model.addAttribute("menuId", menuId);
				return "dev-list";
			}
			devicesService.importDev(devList);
			inputStream.close();
			out.print("<script>alert('导入成功');window.location.href='/devices/getAllDevices?menuId=7'</script>");
			out.flush();
			out.close();
			String menuId = request.getParameter("menuId");
			model.addAttribute("menuId", menuId);
			return "dev-list";
		}else if(postfix.equals("xls")){
			//1、读取工作簿
			HSSFWorkbook  workbook = new HSSFWorkbook (inputStream);
			try {
				//2、读取第一个工作表
				HSSFSheet  sheet = workbook.getSheetAt(0);
				for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++)  
				{  
					HSSFRow row = sheet.getRow(i);  
					for (int j = 0; j < row.getPhysicalNumberOfCells(); j++)  
					{  
						HSSFCell cell = row.getCell(j);  
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						String rawValue = cell.getStringCellValue();
						if (!rawValue.equals("")) {
							fields.add(rawValue);
						}
					}  
					if (fields.size()==5) {
					Device dev = new Device();
					dev.setDname(fields.get(0).replace(" ", ""));
					dev.setDcount(Integer.parseInt(fields.get(1).replace(" ", "")));
					dev.setInfo(fields.get(2).replace(" ", ""));
					dev.setTool(fields.get(3).replace(" ", ""));
					dev.setTcount(Integer.parseInt(fields.get(4).replace(" ", "")));
					devList.add(dev);
					fields.clear();
					}
					if (fields.size()==2) {
					Device dev = new Device();
					dev.setDname(fields.get(0).replace(" ", ""));
					dev.setDcount(Integer.parseInt(fields.get(1).replace(" ", "")));
					devList.add(dev);
					fields.clear();
					}
					if (fields.size()==3) {
					Device dev = new Device();
					dev.setDname(fields.get(0).replace(" ", ""));
					dev.setDcount(Integer.parseInt(fields.get(1).replace(" ", "")));
					dev.setInfo(fields.get(2).replace(" ", ""));
					devList.add(dev);
					fields.clear();
					}
				}  
				
			} catch (Exception e) {
				inputStream.close();
				out.print("<script>alert('导入的文件内容不符合格式，请按照模板写入');window.location.href='/devices/getAllDevices?menuId=7'</script>");
				out.flush();
				out.close();
				String menuId = request.getParameter("menuId");
				model.addAttribute("menuId", menuId);
				return "dev-list";
			}
			devicesService.importDev(devList);
			inputStream.close();
			out.print("<script>alert('导入成功');window.location.href='/devices/getAllDevices?menuId=7'</script>");
			out.flush();
			out.close();
			String menuId = request.getParameter("menuId");
			model.addAttribute("menuId", menuId);
			return "dev-list";
		}
		inputStream.close();
		out.print("<script>alert('文件格式不正确');window.location.href='/devices/getAllDevices?menuId=7'</script>");
		out.flush();
		out.close();
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "dev-list";
	}
	/**
	 * 导出Xls
	 */
	@RequestMapping("/exportFile")
	public ModelAndView exportFile(int type,HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Device> exportDev = devicesService.getExportDev();
		List<String> fields = new ArrayList<>();
		fields.add("设备名称");
		fields.add("设备数量");
		fields.add("备注");
		fields.add("工具及辅助设备");
		fields.add("数量");
		DownUtils.exportReport(type,request, response,null, "设备信息",null,fields, null, null, null,exportDev,null,null,null);
		return null;
	}
}
