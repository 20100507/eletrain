package cn.edu.qqhru.train.utils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.Book;
import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.Device;
import cn.edu.qqhru.train.pojo.Student;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.vo.CourseDateVo;
import cn.edu.qqhru.train.vo.StudentExportVo;
import sun.misc.BASE64Encoder;

public class DownUtils {
	/**
	 * 抽取下载方法
	 * @Description 
	 * @param path
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static ResponseEntity<byte[]> getResponseEntity(String agent,String path,String filename) throws IOException{
		String fileName= "";
		File file=new File(path);  
        HttpHeaders headers = new HttpHeaders();    
        if (agent.contains("Firefox")) {
        	fileName = "=?UTF-8?B?"+new BASE64Encoder().encode(filename.getBytes("utf-8"))+"?=";
		}else {
			fileName = URLEncoder.encode(filename,"utf-8");
		}
        headers.setContentDispositionFormData("attachment", fileName);   
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
                                          headers, HttpStatus.OK);
	}
	/**
	 * 报表的导出,格式为xlsx
	 * @param response 指定HttpServletResponse
	 * @param fileName 指定导出文件的名字
	 * @param fields 指定表格中的域，按照------登录名，用户名，邮箱，电话，地址，学历，身份证----的添加到域中
	 * @param teacherList 指定要导出的教师集合，如果没有则设置为null
	 * @param studentList 指定要导出的学员集合，如果没有则设置为null
	 * @param adminList 指定要导出的管理员集合，如果没有则设置为null
	 * @throws IOException
	 */
	public static void exportReportByxlsx(
			HttpServletResponse response,
			HttpServletRequest request,
			String bdTime,
			String fileName, 
			List<String> fields, 
			List<CourseDateVo> syllabusList, 
			List<Teacher> teacherList,
			List<Admin> adminList,
			List<Student> studentList,
			List<Device> devList,
			List<Book> bookList,List<StudentExportVo> studentVoList,Integer isFinish) throws IOException{
		String xuhao=fields.get(0);
			//==========准备工作薄============
			//创建工作薄
			XSSFWorkbook workbook = new XSSFWorkbook();
			//创建sheet页，指定格式
			XSSFSheet sheet = workbook.createSheet(fileName);
			sheet.setDefaultColumnWidth(20);
			//合并单元格，用于显示表头
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, fields.size()-1);
			sheet.addMergedRegion(cellRangeAddress);
			//添加样式
			XSSFCellStyle style = workbook.createCellStyle();
			XSSFDataFormat format = workbook.createDataFormat();
			style.setDataFormat(format.getFormat("@"));
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			XSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short) 11);
			style.setFont(font);
			//============ 创建第一行============
			XSSFRow row0 = sheet.createRow(0);
			XSSFCell cell_0 = row0.createCell(0);
			cell_0.setCellStyle(style);
			cell_0.setCellValue(fileName);
			//============创建第二行============
			XSSFRow row1 = sheet.createRow(1);
			for (int i = 0; i < fields.size(); i++) {
				row1.setHeight((short)(30*20));
				XSSFCell cell_1 = row1.createCell(i);
				cell_1.setCellStyle(style);
				cell_1.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell_1.setCellValue(fields.get(i));
			}
			fields.clear();
			//============创建其它的行============
			//遍历教师
			if(teacherList!=null){
				for(int i=0;i<teacherList.size();i++){
					XSSFRow row_n = sheet.createRow(i+2);
					Teacher teacher = teacherList.get(i);
					fields.add(teacher.getLoginname());
					fields.add(teacher.getUsername());
					fields.add(teacher.getPosition());
					if(teacher.getEmail().contentEquals("1")){
						fields.add("男");
					}
					else if(teacher.getEmail().contentEquals("0")){
						fields.add("女");
					}
					else{
						fields.add("其它");
					}
					fields.add(teacher.getTelephone());
					fields.add(teacher.getAddress());
					fields.add(teacher.getEducation());
					fields.add(teacher.getIdcard());
					if(teacher.getIsfinish()==0){
						fields.add("在职");
					}
					else{
						fields.add("离职");
					}
					if(teacher.getIdentify()==6){
						fields.add("班主任");
					}
					else if(teacher.getIdentify()==2){
						fields.add("授课教师");
					}
					else{
						fields.add("其它");
					}
					fields.add(teacher.getCreatetime().toLocaleString());
					if(teacher.getUpdatetime()!=null){
						fields.add(teacher.getUpdatetime().toLocaleString());
					}
					else{
						fields.add("未更新过教师");
					}
					sheet.setColumnWidth(0, 8*512);
					sheet.setColumnWidth(1, 5*512);
					sheet.setColumnWidth(2, 5*512);
					sheet.setColumnWidth(3, 3*512);
					sheet.setColumnWidth(4, 8*512);
					sheet.setColumnWidth(5, 20*512);
					sheet.setColumnWidth(6, 5*512);
					sheet.setColumnWidth(7, 13*512);
					sheet.setColumnWidth(8, 3*512);
					sheet.setColumnWidth(8, 5*512);
					for(int j=0;j<fields.size();j++){
						XSSFCell cell_n = row_n.createCell(j);
						cell_n.setCellStyle(style);
						cell_n.setCellValue(fields.get(j));
					}
					fields.clear();
				}
			}
			//遍历课表
			if(syllabusList!=null){
				for(int i=0;i<syllabusList.size();i++){
					XSSFRow row_n = sheet.createRow(i+2);
					CourseDateVo cdv = syllabusList.get(i);
					if(cdv.getDate()!=null){
						fields.add(cdv.getDate());
					}
					else{
						fields.add("*无课*");
					}
					if(cdv.getClasses()!=null){
						fields.add(cdv.getClasses().getCname());
					}
					else{
						fields.add("*无课*");
					}
					if(cdv.getAmCourse()!=null){
						fields.add(cdv.getAmCourse().getCname()+"("+cdv.getAmTeacher().getUsername()+")");
					}
					else{
						fields.add("*无课*");
					}
					if(cdv.getPmCourse()!=null){
						fields.add(cdv.getPmCourse().getCname()+"("+cdv.getPmTeacher().getUsername()+")");
					}
					else{
						fields.add("*无课*");
					}
					if(cdv.getNiCourse()!=null){
						fields.add(cdv.getNiCourse().getCname()+"("+cdv.getNiTeacher().getUsername()+")");
					}
					else{
						fields.add("*无课*");
					}
					for(int j=0;j<fields.size();j++){
						XSSFCell cell_n = row_n.createCell(j);
						cell_n.setCellStyle(style);
						cell_n.setCellValue(fields.get(j));
					}
					fields.clear();
				}
			}
			//便利学员
			if(studentList!=null){
				for(int i=0;i<studentList.size();i++){
					XSSFRow row_n = sheet.createRow(i+2);
					Student student = studentList.get(i);
					if(xuhao.contains("序号")){
						int index=i+1;
						fields.add(String.valueOf(index));
					}
					if(student.getLoginname()!=null){
						fields.add(student.getLoginname());
					}
					if(student.getUsername()!=null){
						fields.add(student.getUsername());
					}
					if(student.getEmail()!=null){
						fields.add(student.getEmail());
					}
					if(student.getTelephone()!=null){
						fields.add(student.getTelephone());
					}
					if(student.getAddress()!=null){
						fields.add(student.getAddress());
					}
					if(student.getEducation()!=null){
						fields.add(student.getEducation());
					}
					if(student.getIdcard()!=null){
						fields.add(student.getIdcard());
					}
					if(bdTime!=null){
						fields.add(bdTime);
					}
					
					for(int j=0;j<fields.size();j++){
						XSSFCell cell_n = row_n.createCell(j);
						cell_n.setCellStyle(style);
						cell_n.setCellValue(fields.get(j));
					}
					fields.clear();
				}
			}
			//便利学员2(屈海龙)
			if(studentVoList!=null){
				for(int i=0;i<studentVoList.size();i++){
					XSSFRow row_n = sheet.createRow(i+2);
					StudentExportVo student = studentVoList.get(i);
					if (isFinish==0) {
						if(student.getUsername()!=null){
							fields.add(student.getUsername());
						}
						if(student.getEmail()!=null){
							fields.add(student.getEmail());
						}
						if(student.getTelephone()!=null){
							fields.add(student.getTelephone());
						}
						if(student.getAddress()!=null){
							fields.add(student.getAddress());
						}
						if(student.getEducation()!=null){
							fields.add(student.getEducation());
						}
						if(student.getIdcard()!=null){
							fields.add(student.getIdcard());
						}
					}else {
						if(student.getUsername()!=null){
							fields.add(student.getUsername());
						}
						if(student.getEmail()!=null){
							fields.add(student.getEmail());
						}
						if(student.getTelephone()!=null){
							fields.add(student.getTelephone());
						}
						if(student.getAddress()!=null){
							fields.add(student.getAddress());
						}
						if(student.getEducation()!=null){
							fields.add(student.getEducation());
						}
						if(student.getIdcard()!=null){
							fields.add(student.getIdcard());
						}
						if(student.getClassName()!=null){
							fields.add(student.getClassName());
						}
						if(student.getTheoryscore()!=null){
							fields.add(student.getTheoryscore()+"");
						}else {
							fields.add(0+"");
						}
						if(student.getPracticescore()!=null){
							fields.add(student.getPracticescore()+"");
						}else {
							fields.add(0+"");
						}
						if(student.getTotal()!=null){
							fields.add(student.getTotal()+"");
						}else {
							fields.add(0+"");
						}
						if(student.getCertificateNo()!=null){
							fields.add(student.getCertificateNo());
						}else {
							fields.add("");
						}
					}
					for(int j=0;j<fields.size();j++){
						XSSFCell cell_n = row_n.createCell(j);
						cell_n.setCellStyle(style);
						cell_n.setCellValue(fields.get(j));
					}
					fields.clear();
				}
			}
			//遍历设备
			if(devList!=null){
				for(int i=0;i<devList.size();i++){
					XSSFRow row_n = sheet.createRow(i+2);
					Device device = devList.get(i);
					if(device.getDname()!=null){
						fields.add(device.getDname());
					}
					if(device.getDcount().toString()!=null){
						fields.add(device.getDcount().toString());
					}
					if(device.getInfo()!=null){
						fields.add(device.getInfo());
					}
					if(device.getTool()!=null){
						fields.add(device.getTool());
					}
					if(device.getTcount().toString()!=null){
						fields.add(device.getTcount().toString());
					}
					
					for(int j=0;j<fields.size();j++){
						XSSFCell cell_n = row_n.createCell(j);
						cell_n.setCellStyle(style);
						cell_n.setCellValue(fields.get(j));
					}
					fields.clear();
				}
			}
			//遍历教材
			if(bookList!=null){
				for(int i=0;i<bookList.size();i++){
					XSSFRow row_n = sheet.createRow(i+2);
					Book book = bookList.get(i);
					if(book.getBname()!=null){
						fields.add(book.getBname());
					}
					if(book.getAuthor()!=null){
						fields.add(book.getAuthor());
					}
					if(book.getPress()!=null){
						fields.add(book.getPress());
					}
					if(book.getCount().toString()!=null){
						fields.add(book.getCount().toString());
					}
					for(int j=0;j<fields.size();j++){
						XSSFCell cell_n = row_n.createCell(j);
						cell_n.setCellStyle(style);
						cell_n.setCellValue(fields.get(j));
					}
					fields.clear();
				}
			}
			//遍历管理员
			if(adminList!=null){
				for(int i=0;i<adminList.size();i++){
					XSSFRow row_n = sheet.createRow(i+2);
					Admin admin = adminList.get(i);
					if(admin.getLoginname()!=null){
						fields.add(admin.getLoginname());
					}
					if(admin.getUsername()!=null){
						fields.add(admin.getUsername());
					}
					if(admin.getEmail()!=null){
						fields.add(admin.getEmail());
					}
					/*if(admin.getTelephone()!=null){
						fields.add(admin.getTelephone());
					}
					if(admin.getAddress()!=null){
						fields.add(admin.getAddress());
					}
					if(admin.getEducation()!=null){
						fields.add(admin.getEducation());
					}
					if(admin.getIdcard()!=null){
						fields.add(admin.getIdcard());
					}*/
					
					for(int j=0;j<fields.size();j++){
						XSSFCell cell_n = row_n.createCell(j);
						cell_n.setCellStyle(style);
						cell_n.setCellValue(fields.get(j));
					}
					fields.clear();
				}
			}
			//============选择下载路径===========
			ServletOutputStream outputStream = response.getOutputStream();
			try {
			    String fileName1 = ""+fileName+".xlsx";  
			    response.reset();  //清除缓冲区中的任何数据以及状态代码和标头
			    String header = request.getHeader("user-agent");
				if (header.contains("Firefox")) {
					fileName1 = "=?UTF-8?B?"+new BASE64Encoder().encode(fileName1.getBytes("utf-8"))+"?=";
				}else {
					fileName1 = URLEncoder.encode(fileName1, "UTF-8");  
				}
			    response.setHeader("Content-Disposition", "attachment; filename=" + fileName1 + "");  
			    response.setContentType("application/octet-stream;charset=UTF-8");  
				workbook.write(outputStream);
			} catch (Exception e) {
				outputStream.flush();
				outputStream.close();  
			}  
			outputStream.flush();
			outputStream.close();  
		}
	/**
	 * 报表的导出,格式为xls
	 * @param mould 指定导出的类型：1表示xlsx,2表示xls
	 * @param response 指定HttpServletResponse
	 * @param fileName 指定导出文件的名字
	 * @param fields 指定表格中的域，按照------登录名，用户名，邮箱，电话，地址，学历，身份证----的添加到域中
	 * @param teacherList 指定要导出的教师集合，如果没有则设置为null
	 * @param studentList 指定要导出的学员集合，如果没有则设置为null
	 * @param adminList 指定要导出的管理员集合，如果没有则设置为null
	 * @throws IOException
	 */
	public static void exportReport(Integer mould,HttpServletRequest request,HttpServletResponse response,String bdTime,String fileName, List<CourseDateVo> syllabusList,List<String> fields,List<Teacher> teacherList,List<Admin> adminList,List<Student> studentList,List<Device> devList,List<Book> bookList,List<StudentExportVo> studentVoList,Integer isFinish) throws IOException{
		if(mould==1){
			exportReportByxlsx(response,request,bdTime, fileName, fields, syllabusList, teacherList, adminList, studentList,devList,bookList,studentVoList,isFinish);
		}
		else if(mould==2){
			exportReportByxls(response,request, bdTime, fileName, fields, syllabusList,teacherList, adminList, studentList,devList,bookList,studentVoList,isFinish);
		}
		else{
			System.out.println("发出脚本提示");
		}
	}
	
	public static void exportReportByxls(HttpServletResponse response,HttpServletRequest request,String bdTime, String fileName,List<String> fields, List<CourseDateVo> syllabusList,List<Teacher> teacherList,List<Admin> adminList,List<Student> studentList,List<Device> devList,List<Book> bookList,List<StudentExportVo> studentVoList,Integer isFinish) throws IOException{
		String xuhao=fields.get(0);
		//==========准备工作薄============
		//创建工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建sheet页，指定格式
		HSSFSheet sheet = workbook.createSheet(fileName);
		sheet.setDefaultColumnWidth(20);
		//合并单元格，用于显示表头
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, fields.size()-1);
		sheet.addMergedRegion(cellRangeAddress);
		//添加样式
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		style.setFont(font);
		//============ 创建第一行============
		HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell_0 = row0.createCell(0);
		cell_0.setCellStyle(style);
		cell_0.setCellValue(fileName);
		//============创建第二行============
		HSSFRow row1 = sheet.createRow(1);
		for (int i = 0; i < fields.size(); i++) {
			row1.setHeight((short)(30*20));
			HSSFCell cell_1 = row1.createCell(i);
			cell_1.setCellStyle(style);
			cell_1.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell_1.setCellValue(fields.get(i));
		}
		fields.clear();
		//============创建其它的行============
		//遍历教师
		if(teacherList!=null){
			for(int i=0;i<teacherList.size();i++){
				HSSFRow row_n = sheet.createRow(i+2);
				Teacher teacher = teacherList.get(i);
				fields.add(teacher.getLoginname());
				fields.add(teacher.getUsername());
				fields.add(teacher.getPosition());
				if(teacher.getEmail().contentEquals("1")){
					fields.add("男");
				}
				else if(teacher.getEmail().contentEquals("0")){
					fields.add("女");
				}
				else{
					fields.add("其它");
				}
				fields.add(teacher.getTelephone());
				fields.add(teacher.getAddress());
				fields.add(teacher.getEducation());
				fields.add(teacher.getIdcard());
				if(teacher.getIsfinish()==0){
					fields.add("在职");
				}
				else{
					fields.add("离职");
				}
				if(teacher.getIdentify()==6){
					fields.add("班主任");
				}
				else if(teacher.getIdentify()==2){
					fields.add("授课教师");
				}
				else{
					fields.add("其它");
				}
				fields.add(teacher.getCreatetime().toLocaleString());
				if(teacher.getUpdatetime()!=null){
					fields.add(teacher.getUpdatetime().toLocaleString());
				}
				else{
					fields.add("未更新过教师");
				}
				sheet.setColumnWidth(0, 8*512);
				sheet.setColumnWidth(1, 5*512);
				sheet.setColumnWidth(2, 5*512);
				sheet.setColumnWidth(3, 3*512);
				sheet.setColumnWidth(4, 8*512);
				sheet.setColumnWidth(5, 20*512);
				sheet.setColumnWidth(6, 5*512);
				sheet.setColumnWidth(7, 13*512);
				sheet.setColumnWidth(8, 3*512);
				sheet.setColumnWidth(9, 5*512);
				for(int j=0;j<fields.size();j++){
					HSSFCell cell_n = row_n.createCell(j);
					cell_n.setCellStyle(style);
					cell_n.setCellValue(fields.get(j));
				}
				fields.clear();
			}
		}
		//遍历课表
		if(syllabusList!=null){
			for(int i=0;i<syllabusList.size();i++){
				HSSFRow row_n = sheet.createRow(i+2);
				CourseDateVo cdv = syllabusList.get(i);
				if(cdv.getDate()!=null){
					fields.add(cdv.getDate());
				}
				else{
					fields.add("*无课*");
				}
				if(cdv.getClasses()!=null){
					fields.add(cdv.getClasses().getCname());
				}
				else{
					fields.add("*无课*");
				}
				if(cdv.getAmCourse()!=null){
					fields.add(cdv.getAmCourse().getCname()+"("+cdv.getAmTeacher().getUsername()+")");
				}
				else{
					fields.add("*无课*");
				}
				if(cdv.getPmCourse()!=null){
					fields.add(cdv.getPmCourse().getCname()+"("+cdv.getPmTeacher().getUsername()+")");
				}
				else{
					fields.add("*无课*");
				}
				if(cdv.getNiCourse()!=null){
					fields.add(cdv.getNiCourse().getCname()+"("+cdv.getNiTeacher().getUsername()+")");
				}
				else{
					fields.add("*无课*");
				}
				
				for(int j=0;j<fields.size();j++){
					HSSFCell cell_n = row_n.createCell(j);
					cell_n.setCellStyle(style);
					cell_n.setCellValue(fields.get(j));
				}
				fields.clear();
			}
		}
		//便利学员
		if(studentList!=null){
			for(int i=0;i<studentList.size();i++){
				HSSFRow row_n = sheet.createRow(i+2);
				Student student = studentList.get(i);
				if(xuhao.contains("序号")){
					int index=i+1;
					fields.add(String.valueOf(index));
				}
				if(student.getLoginname()!=null){
					fields.add(student.getLoginname());
				}
				if(student.getUsername()!=null){
					fields.add(student.getUsername());
				}
				if(student.getEmail()!=null){
					fields.add(student.getEmail());
				}
				if(student.getTelephone()!=null){
					fields.add(student.getTelephone());
				}
				if(student.getEducation()!=null){
					fields.add(student.getEducation());
				}
				if(student.getIdcard()!=null){
					fields.add(student.getIdcard());
				}
				if(student.getAddress()!=null){
					fields.add(student.getAddress());
				}
				if(bdTime!=null){
					fields.add(bdTime);
				}
				
				for(int j=0;j<fields.size();j++){
					HSSFCell cell_n = row_n.createCell(j);
					cell_n.setCellStyle(style);
					cell_n.setCellValue(fields.get(j));
				}
				fields.clear();
			}
		}
		//便利学员2(屈海龙)
		if(studentVoList!=null){
			for(int i=0;i<studentVoList.size();i++){
				HSSFRow row_n = sheet.createRow(i+2);
				StudentExportVo student = studentVoList.get(i);
				if (isFinish==0) {
					if(student.getUsername()!=null){
						fields.add(student.getUsername());
					}
					if(student.getEmail()!=null){
						fields.add(student.getEmail());
					}
					if(student.getTelephone()!=null){
						fields.add(student.getTelephone());
					}
					if(student.getAddress()!=null){
						fields.add(student.getAddress());
					}
					if(student.getEducation()!=null){
						fields.add(student.getEducation());
					}
					if(student.getIdcard()!=null){
						fields.add(student.getIdcard());
					}
				}else {
					if(student.getUsername()!=null){
						fields.add(student.getUsername());
					}
					if(student.getEmail()!=null){
						fields.add(student.getEmail());
					}
					if(student.getTelephone()!=null){
						fields.add(student.getTelephone());
					}
					if(student.getAddress()!=null){
						fields.add(student.getAddress());
					}
					if(student.getEducation()!=null){
						fields.add(student.getEducation());
					}
					if(student.getIdcard()!=null){
						fields.add(student.getIdcard());
					}
					if(student.getClassName()!=null){
						fields.add(student.getClassName());
					}
					if(student.getTheoryscore()!=null){
						fields.add(student.getTheoryscore()+"");
					}else {
						fields.add(0+"");
					}
					if(student.getPracticescore()!=null){
						fields.add(student.getPracticescore()+"");
					}else {
						fields.add(0+"");
					}
					if(student.getTotal()!=null){
						fields.add(student.getTotal()+"");
					}else {
						fields.add(0+"");
					}
					if(student.getCertificateNo()!=null){
						fields.add(student.getCertificateNo());
					}else {
						fields.add("");
					}
				}
				for(int j=0;j<fields.size();j++){
					HSSFCell cell_n = row_n.createCell(j);
					cell_n.setCellStyle(style);
					cell_n.setCellValue(fields.get(j));
				}
				fields.clear();
			}
		}
		//遍历设备
		if(devList!=null){
			for(int i=0;i<devList.size();i++){
				HSSFRow row_n = sheet.createRow(i+2);
				Device device = devList.get(i);
				if(device.getDname()!=null){
					fields.add(device.getDname());
				}
				if(device.getDcount().toString()!=null){
					fields.add(device.getDcount().toString());
				}
				if(device.getInfo()!=null){
					fields.add(device.getInfo());
				}
				if(device.getTool()!=null){
					fields.add(device.getTool());
				}
				if(device.getTcount().toString()!=null){
					fields.add(device.getTcount().toString());
				}
				
				for(int j=0;j<fields.size();j++){
					HSSFCell cell_n = row_n.createCell(j);
					cell_n.setCellStyle(style);
					cell_n.setCellValue(fields.get(j));
				}
				fields.clear();
			}
		}
		//遍历教材
		if(bookList!=null){
			for(int i=0;i<bookList.size();i++){
				HSSFRow row_n = sheet.createRow(i+2);
				Book book = bookList.get(i);
				if(book.getBname()!=null){
					fields.add(book.getBname());
				}
				if(book.getAuthor()!=null){
					fields.add(book.getAuthor());
				}
				if(book.getPress()!=null){
					fields.add(book.getPress());
				}
				if(book.getCount().toString()!=null){
					fields.add(book.getCount().toString());
				}
				for(int j=0;j<fields.size();j++){
					HSSFCell cell_n = row_n.createCell(j);
					cell_n.setCellStyle(style);
					cell_n.setCellValue(fields.get(j));
				}
				fields.clear();
			}
		}
		//遍历管理员
		if(adminList!=null){
			for(int i=0;i<adminList.size();i++){
				HSSFRow row_n = sheet.createRow(i+2);
				Admin admin = adminList.get(i);
				if(admin.getLoginname()!=null){
					fields.add(admin.getLoginname());
				}
				if(admin.getUsername()!=null){
					fields.add(admin.getUsername());
				}
				if(admin.getEmail()!=null){
					fields.add(admin.getEmail());
				}
				/*if(admin.getTelephone()!=null){
						fields.add(admin.getTelephone());
					}
					if(admin.getAddress()!=null){
						fields.add(admin.getAddress());
					}
					if(admin.getEducation()!=null){
						fields.add(admin.getEducation());
					}
					if(admin.getIdcard()!=null){
						fields.add(admin.getIdcard());
					}*/
				
				for(int j=0;j<fields.size();j++){
					HSSFCell cell_n = row_n.createCell(j);
					cell_n.setCellStyle(style);
					cell_n.setCellValue(fields.get(j));
				}
				fields.clear();
			}
		}
		//============选择下载路径===========
		ServletOutputStream outputStream = response.getOutputStream();
		try {
			String fileName1 = ""+fileName+".xls";  
			response.reset();  //清除缓冲区中的任何数据以及状态代码和标头
			String header = request.getHeader("user-agent");
			if (header.contains("Firefox")) {
				fileName1 = "=?UTF-8?B?"+new BASE64Encoder().encode(fileName1.getBytes("utf-8"))+"?=";
			}else {
				fileName1 = URLEncoder.encode(fileName1, "UTF-8");  
			}
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName1);  
			response.setContentType("application/octet-stream;charset=UTF-8");  
			workbook.write(outputStream);
		} catch (Exception e) {
			outputStream.flush();
			outputStream.close();  
		}  
		outputStream.flush();
		outputStream.close();  
	}
	/**
	 * 导出考勤表
	 * @param mould
	 * @param response
	 * @param request
	 * @param studentList
	 * @param classes 
	 * @param classesTeacher 
	 * @throws IOException
	 */
	public static void exportCheAttendanceByxls(Integer mould,HttpServletResponse response,HttpServletRequest request,List<Student> studentList, Teacher classesTeacher, Classes classes) throws IOException{
		//==========准备工作薄============
		//创建工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建sheet页，指定格式
		HSSFSheet sheet = workbook.createSheet("考勤表");
		sheet.setDefaultColumnWidth(5);
		sheet.setColumnWidth(1, 255*10);
		//合并单元格，用于显示表头
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 17);
		sheet.addMergedRegion(cellRangeAddress);
		//添加样式
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
	    style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		style.setFont(font);
		
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setDataFormat(format.getFormat("@"));
		style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 13);
		style2.setFont(font2);
		
		HSSFCellStyle style3 = workbook.createCellStyle();
		style3.setDataFormat(format.getFormat("@"));
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font3 = workbook.createFont();
		font3.setFontHeightInPoints((short) 20);
		style3.setFont(font3);
		
		//============ 创建第一行============
		HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell_0 = row0.createCell(0);
		cell_0.setCellStyle(style3);
		short cellHeightLine1 = (short) (30* 0.75f * 20);
		row0.setHeight(cellHeightLine1);
		cell_0.setCellValue("考勤表");
		//============创建第二行============
		//合并单元格，用于显示编码编号
		CellRangeAddress cellRangeAddress2 = new CellRangeAddress(1, 1, 0, 17);
		sheet.addMergedRegion(cellRangeAddress2);
		//合并单元格，用于显示编码编号
		CellRangeAddress cellRangeAddress5 = new CellRangeAddress(2, 2, 0, 17);
		sheet.addMergedRegion(cellRangeAddress5);
		
		ArrayList<String> fields = new ArrayList<>();
		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cell_line1 = row1.createCell(0);
		cell_line1.setCellStyle(style2);
		cell_line1.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell_line1.setCellValue("编码:                                                                  编号:"+classes.getClassesNo());
		short cellHeight = (short) ( 30* 0.75f * 20);
		row1.setHeight(cellHeight);

		//============创建第三行============
		String startTime=classes.getStarttime().split("~")[0];
		String endTime=classes.getStarttime().split("~")[1];
		List<String> dates = new ArrayList<>();
		try {
			dates = SyllabusUtils.getAfterDay(startTime,endTime);
		} catch (ParseException e1) {
			  System.out.println("日期格式解析异常");
		}
		HSSFRow row2 = sheet.createRow(2);
		HSSFCell cell_2 = row2.createCell(0);
		cell_2.setCellStyle(style2);
		cell_2.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell_2.setCellValue("培训班名称： "+classes.getCname()+"                            培训日期:"+startTime.split("-")[0]+"年 "+startTime.split("-")[1]+"月"+startTime.split("-")[2]+"日 -- "+endTime.split("-")[0]+"年"+endTime.split("-")[1]+"月"+endTime.split("-")[2]+"日");
		row2.setHeight(cellHeight);
		//============创建第四行============
		ArrayList<String> fields1 = new ArrayList<>();
		fields1.add("序号");
		fields1.add("姓名");
		fields1.add("性别");
		fields1.add("单位");
		HSSFRow row3 = sheet.createRow(3);
		for (int i = 0; i < 4; i++) {
			//row3.setHeight((short)(30*20));
			HSSFCell cell_1 = row3.createCell(i);
			cell_1.setCellStyle(style);
			cell_1.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell_1.setCellValue(fields1.get(i));
			
			int cellWidth = (int) (50 * 0.75f * 50);
	        sheet.setColumnWidth(i, cellWidth);
	        row3.setHeight(cellHeight);
		}
		for (int i = 4; i < 18; i++) {
			HSSFCell cell_1 = row3.createCell(i);
			HSSFCellStyle styleRi = workbook.createCellStyle();
			styleRi.setDataFormat(format.getFormat("@"));
			styleRi.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
			styleRi.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
			styleRi.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
			styleRi.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			styleRi.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			styleRi.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cell_1.setCellStyle(styleRi);
			cell_1.setCellType(HSSFCell.CELL_TYPE_STRING);
			if(dates!=null&&dates.size()>0){
				cell_1.setCellValue(dates.get(i-4));
			}else{
				cell_1.setCellValue("日");
			}
			
	        int cellWidth = (int) (50 * 0.75f * 50);
	        sheet.setColumnWidth(i, cellWidth);
	        row3.setHeight(cellHeight);
		}
		fields.clear();
		//============创建其它的行============
		//便利学员
		if(studentList!=null){
			for(int i=0;i<studentList.size();i++){
				HSSFRow row_n = sheet.createRow(i+4);
				Student student = studentList.get(i);
				String index=String.valueOf(i+1);
				fields.add(index);
				fields.add(student.getUsername());
				fields.add(student.getEmail());
				if(student.getAddress().trim().length()!=0){
					fields.add(student.getAddress().substring(0, 1));
				}
				else{
					fields.add("");
				}
				
				for(int j=0;j<fields.size();j++){
					HSSFCell cell_n = row_n.createCell(j);
					cell_n.setCellStyle(style);
					cell_n.setCellValue(fields.get(j));
				}
				for(int n=fields.size();n<18;n++){
					HSSFCell cell_n = row_n.createCell(n);
					cell_n.setCellStyle(style);
					int x1 = 50, y1 = 30;
					int[] xys = { x1, y1 };
					POIUtils.drawLine(sheet, row_n, i+4, n, 50, 20, xys);
				}
				fields.clear();
			}
		}
		//合并单元格，用于显示编码编号
		CellRangeAddress cellRangeAddressR1 = new CellRangeAddress(studentList.size()+4, studentList.size()+4, 0, 21);
		sheet.addMergedRegion(cellRangeAddressR1);
		//合并单元格，用于显示编码编号
		CellRangeAddress cellRangeAddressR2 = new CellRangeAddress(studentList.size()+5, studentList.size()+5, 0, 21);
		sheet.addMergedRegion(cellRangeAddressR2);
		//创建脚注
		HSSFRow rowR = sheet.createRow(studentList.size()+4);
		HSSFCell cell_R = rowR.createCell(0);
		cell_R.setCellStyle(style2);
		cell_R.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell_R.setCellValue("注：出勤√ 缺勤X 病假B 事假S                                  使用部门：                                                      处事负责人：         ");
		rowR.setHeight(cellHeight);
		
		HSSFRow rowR2 = sheet.createRow(studentList.size()+5);
		HSSFCell cell_R2 = rowR2.createCell(0);
		cell_R2.setCellStyle(style2);
		cell_R2.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell_R2.setCellValue("保存期限：                                                                                             班主任："+classesTeacher.getUsername());
		rowR2.setHeight(cellHeight);
		
		//============选择下载路径===========
		ServletOutputStream outputStream = response.getOutputStream();
		try {
			String fileName1 = "考勤表_"+classes.getCname()+".xls";  
			response.reset();  //清除缓冲区中的任何数据以及状态代码和标头
			String header = request.getHeader("user-agent");
			if (header.contains("Firefox")) {
				fileName1 = "=?UTF-8?B?"+new BASE64Encoder().encode(fileName1.getBytes("utf-8"))+"?=";
			}else {
				fileName1 = URLEncoder.encode(fileName1, "UTF-8");  
			}
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName1);  
			response.setContentType("application/octet-stream;charset=UTF-8");  
			workbook.write(outputStream);
		} catch (Exception e) {
			outputStream.flush();
			outputStream.close();  
		}  
		outputStream.flush();
		outputStream.close();  
	}
	/**
	 * 导出考勤表
	 * @param mould
	 * @param response
	 * @param request
	 * @param studentList
	 * @param classes 
	 * @param classesTeacher 
	 * @throws IOException
	 */
	public static void exportCheAttendanceByxlsx(Integer mould,HttpServletResponse response,HttpServletRequest request,List<Student> studentList, Teacher classesTeacher, Classes classes) throws IOException{
		//==========准备工作薄============
		//创建工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		//创建sheet页，指定格式
		XSSFSheet sheet = workbook.createSheet("考勤表");
		sheet.setDefaultColumnWidth(5);
		sheet.setColumnWidth(1, 255*10);
		//合并单元格，用于显示表头
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 17);
		sheet.addMergedRegion(cellRangeAddress);
		//添加样式
		XSSFCellStyle style = workbook.createCellStyle();
		XSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框    
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框    
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框    
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		style.setFont(font);
		
		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setDataFormat(format.getFormat("@"));
		style2.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		XSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 13);
		style2.setFont(font2);
		
		XSSFCellStyle style3 = workbook.createCellStyle();
		style3.setDataFormat(format.getFormat("@"));
		style3.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style3.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		XSSFFont font3 = workbook.createFont();
		font3.setFontHeightInPoints((short) 20);
		style3.setFont(font3);
		
		//============ 创建第一行============
		XSSFRow row0 = sheet.createRow(0);
		XSSFCell cell_0 = row0.createCell(0);
		cell_0.setCellStyle(style3);
		short cellHeightLine1 = (short) (30* 0.75f * 20);
		row0.setHeight(cellHeightLine1);
		cell_0.setCellValue("考勤表");
		//============创建第二行============
		//合并单元格，用于显示编码编号
		CellRangeAddress cellRangeAddress2 = new CellRangeAddress(1, 1, 0, 17);
		sheet.addMergedRegion(cellRangeAddress2);
		//合并单元格，用于显示编码编号
		CellRangeAddress cellRangeAddress5 = new CellRangeAddress(2, 2, 0, 17);
		sheet.addMergedRegion(cellRangeAddress5);
		
		ArrayList<String> fields = new ArrayList<>();
		XSSFRow row1 = sheet.createRow(1);
		XSSFCell cell_line1 = row1.createCell(0);
		cell_line1.setCellStyle(style2);
		cell_line1.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell_line1.setCellValue("编码:                                                                 编号:"+classes.getClassesNo());
		short cellHeight = (short) ( 30* 0.75f * 20);
		row1.setHeight(cellHeight);
		
		//============创建第三行============
		String startTime=classes.getStarttime().split("~")[0];
		String endTime=classes.getStarttime().split("~")[1];
		List<String> dates = new ArrayList<>();
		try {
			dates = SyllabusUtils.getAfterDay(startTime,endTime);
		} catch (ParseException e1) {
			  System.out.println("日期格式解析异常");
		}
		XSSFRow row2 = sheet.createRow(2);
		XSSFCell cell_2 = row2.createCell(0);
		cell_2.setCellStyle(style2);
		cell_2.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell_2.setCellValue("培训班名称:"+classes.getCname()+"                     培训日期:"+startTime.split("-")[0]+"年 "+startTime.split("-")[1]+"月"+startTime.split("-")[2]+"日  -- "+endTime.split("-")[0]+"年"+endTime.split("-")[1]+"月"+endTime.split("-")[2]+"日");
		row2.setHeight(cellHeight);
		//============创建第四行============
		ArrayList<String> fields1 = new ArrayList<>();
		fields1.add("序号");
		fields1.add("姓名");
		fields1.add("性别");
		fields1.add("单位");
		XSSFRow row3 = sheet.createRow(3);
		for (int i = 0; i < 4; i++) {
			//row3.setHeight((short)(30*20));
			XSSFCell cell_1 = row3.createCell(i);
			cell_1.setCellStyle(style);
			cell_1.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell_1.setCellValue(fields1.get(i));
			
			int cellWidth = (int) (50 * 0.75f * 50);
			sheet.setColumnWidth(i, cellWidth);
			row3.setHeight(cellHeight);
		}
		for (int i = 4; i < 18; i++) {
			XSSFCell cell_1 = row3.createCell(i);
			XSSFCellStyle styleRi = workbook.createCellStyle();
			styleRi.setDataFormat(format.getFormat("@"));
			styleRi.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框    
			styleRi.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框    
			styleRi.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框    
			styleRi.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
			styleRi.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
			styleRi.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			cell_1.setCellStyle(styleRi);
			cell_1.setCellType(XSSFCell.CELL_TYPE_STRING);
			if(dates!=null&&dates.size()>0){
				cell_1.setCellValue(dates.get(i-4));
			}else{
				cell_1.setCellValue("日");
			}
			
			int cellWidth = (int) (50 * 0.75f * 50);
			sheet.setColumnWidth(i, cellWidth);
			row3.setHeight(cellHeight);
		}
		fields.clear();
		//============创建其它的行============
		//便利学员
		if(studentList!=null){
			for(int i=0;i<studentList.size();i++){
				XSSFRow row_n = sheet.createRow(i+4);
				row_n.setHeight((short) 500);
				Student student = studentList.get(i);
				String index=String.valueOf(i+1);
				fields.add(index);
				fields.add(student.getUsername());
				fields.add(student.getEmail());
				if(student.getAddress().trim().length()!=0){
					fields.add(student.getAddress().substring(0, 1));
				}else{
					fields.add("");
				}
				
				for(int j=0;j<fields.size();j++){
					XSSFCell cell_n = row_n.createCell(j);
					cell_n.setCellStyle(style);
					cell_n.setCellValue(fields.get(j));
				}
				for(int n=fields.size();n<18;n++){
					XSSFCell cell_n = row_n.createCell(n);
					cell_n.setCellStyle(style);
					sheet.setColumnWidth(n, 3*512);
					cell_n.setCellValue("");
				}
				fields.clear();
			}
		}
		//合并单元格，用于显示编码编号
		CellRangeAddress cellRangeAddressR1 = new CellRangeAddress(studentList.size()+4, studentList.size()+4, 0, 21);
		sheet.addMergedRegion(cellRangeAddressR1);
		//合并单元格，用于显示编码编号
		CellRangeAddress cellRangeAddressR2 = new CellRangeAddress(studentList.size()+5, studentList.size()+5, 0, 21);
		sheet.addMergedRegion(cellRangeAddressR2);
		//创建脚注
		XSSFRow rowR = sheet.createRow(studentList.size()+4);
		XSSFCell cell_R = rowR.createCell(0);
		cell_R.setCellStyle(style2);
		cell_R.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell_R.setCellValue("注：出勤√ 缺勤X 病假B 事假S                                  使用部门：                                                      处事负责人：         ");
		rowR.setHeight(cellHeight);
		
		XSSFRow rowR2 = sheet.createRow(studentList.size()+5);
		XSSFCell cell_R2 = rowR2.createCell(0);
		cell_R2.setCellStyle(style2);
		cell_R2.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell_R2.setCellValue("保存期限：                                                                                             班主任："+classesTeacher.getUsername());
		rowR2.setHeight(cellHeight);
		
		//============选择下载路径===========
		ServletOutputStream outputStream = response.getOutputStream();
		try {
			String fileName1 = "考勤表_"+classes.getCname()+".xlsx";  
			response.reset();  //清除缓冲区中的任何数据以及状态代码和标头
			String header = request.getHeader("user-agent");
			if (header.contains("Firefox")) {
				fileName1 = "=?UTF-8?B?"+new BASE64Encoder().encode(fileName1.getBytes("utf-8"))+"?=";
			}else {
				fileName1 = URLEncoder.encode(fileName1, "UTF-8");  
			}
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName1);  
			response.setContentType("application/octet-stream;charset=UTF-8");  
			workbook.write(outputStream);
		} catch (Exception e) {
			outputStream.flush();
			outputStream.close();  
		}  
		outputStream.flush();
		outputStream.close();  
	}
}

