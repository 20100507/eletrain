package cn.edu.qqhru.train.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.Student;
import cn.edu.qqhru.train.vo.ClassesVo;
import sun.misc.BASE64Encoder;

public class ClassesStuDownUtils {
	public static void exportReport(Integer mould,HttpServletRequest request,HttpServletResponse response,Classes classes, String fileName,List<String> fields,List<Student> studentList) throws IOException{
		if(mould==1){
			exportReportByxlsx(response,request, classes,  fileName, fields,studentList);
		}
		else if(mould==2){
			exportReportByxls(response,request,classes, fileName, fields,studentList);
		}
		else{
			System.out.println("发出脚本提示");
		}
	}
	public static void exportReportByxlsx(
			HttpServletResponse response,
			HttpServletRequest request,
			Classes classes, 
			String fileName,
			List<String> fields, 
			List<Student> studentList) throws IOException{
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
		//合并单元格，用于显示表头
		CellRangeAddress cellRangeAddress1 = new CellRangeAddress(1, 1, 0, fields.size()-1);
		sheet.addMergedRegion(cellRangeAddress1);
		//添加样式
		XSSFCellStyle style = workbook.createCellStyle();
		XSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		style.setFont(font);
		//样式2
		XSSFCellStyle styleLine2 = workbook.createCellStyle();
		XSSFDataFormat formatLine2 = workbook.createDataFormat();
		styleLine2.setDataFormat(formatLine2.getFormat("@"));
		styleLine2.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleLine2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		XSSFFont fontLine2 = workbook.createFont();
		fontLine2.setFontHeightInPoints((short) 11);
		styleLine2.setFont(fontLine2);
		//============ 创建第一行============
		XSSFRow row0 = sheet.createRow(0);
		XSSFCell cell_0 = row0.createCell(0);
		cell_0.setCellStyle(style);
		cell_0.setCellValue(fileName);
		//============ 创建第二行============
		XSSFRow rowZ = sheet.createRow(1);
		XSSFCell cell_Z = rowZ.createCell(0);
		cell_Z.setCellStyle(styleLine2);
		cell_Z.setCellValue("培训项目名称："+classes.getCname()+"                    编号：   "+classes.getClassesNo()+"                    培训时间："+classes.getStarttime());
		//============创建第三行============
		XSSFRow row1 = sheet.createRow(2);
		for (int i = 0; i < fields.size(); i++) {
			row1.setHeight((short)(30*20));
			XSSFCell cell_1 = row1.createCell(i);
			cell_1.setCellStyle(style);
			cell_1.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell_1.setCellValue(fields.get(i));
		}
		fields.clear();
		//============创建其它的行============
		
		//遍历学员
		if(studentList!=null){
			for(int i=0;i<studentList.size();i++){
				XSSFRow row_n = sheet.createRow(i+3);
				Student student = studentList.get(i);
				if(xuhao.contains("序号")){
					int index=i+1;
					fields.add(String.valueOf(index));
				}
				
				fields.add(student.getUsername());
				fields.add(student.getEmail());
				fields.add(student.getIdcard());
				fields.add(student.getAddress());
				fields.add(classes.getCreatetime());
				fields.add(student.getTelephone());
				fields.add(student.getLoginname());
				fields.add("");
				
				sheet.setColumnWidth(0, 1500);
				sheet.setColumnWidth(1, 5*512);
				sheet.setColumnWidth(2, 3*512);
				sheet.setColumnWidth(3, 10*512);
				sheet.setColumnWidth(4, 20*512);
				sheet.setColumnWidth(5, 6*512);
				sheet.setColumnWidth(6, 6*512);
				sheet.setColumnWidth(7, 8*512);
				sheet.setColumnWidth(8, 5*512);
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
			String fileName1 = fileName+".xlsx";  
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

	public static void exportReportByxls(
			HttpServletResponse response,
			HttpServletRequest request,
			Classes classes, 
			String fileName,
			List<String> fields, 
			List<Student> studentList) throws IOException{
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
		//合并单元格，用于显示表头
		CellRangeAddress cellRangeAddress1 = new CellRangeAddress(1, 1, 0, fields.size()-1);
		sheet.addMergedRegion(cellRangeAddress1);
		//添加样式
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		style.setFont(font);
		//样式2
		HSSFCellStyle styleLine2 = workbook.createCellStyle();
		HSSFDataFormat formatLine2 = workbook.createDataFormat();
		styleLine2.setDataFormat(formatLine2.getFormat("@"));
		styleLine2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleLine2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont fontLine2 = workbook.createFont();
		fontLine2.setFontHeightInPoints((short) 11);
		styleLine2.setFont(fontLine2);
		//============ 创建第一行============
		HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell_0 = row0.createCell(0);
		cell_0.setCellStyle(style);
		cell_0.setCellValue(fileName);
		//============ 创建第二行============
		HSSFRow rowZ = sheet.createRow(1);
		HSSFCell cell_Z = rowZ.createCell(0);
		cell_Z.setCellStyle(styleLine2);
		cell_Z.setCellValue("培训项目名称："+classes.getCname()+"      编号：   "+classes.getClassesNo()+"         培训时间："+classes.getStarttime());
		//============创建第三行============
		HSSFRow row1 = sheet.createRow(2);
		for (int i = 0; i < fields.size(); i++) {
			row1.setHeight((short)(30*20));
			HSSFCell cell_1 = row1.createCell(i);
			cell_1.setCellStyle(style);
			cell_1.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell_1.setCellValue(fields.get(i));
		}
		fields.clear();
		//============创建其它的行============
		
		//便利学员
		if(studentList!=null){
			for(int i=0;i<studentList.size();i++){
				HSSFRow row_n = sheet.createRow(i+3);
				Student student = studentList.get(i);
				if(xuhao.contains("序号")){
					int index=i+1;
					fields.add(String.valueOf(index));
				}
				
				fields.add(student.getUsername());
				fields.add(student.getEmail());
				fields.add(student.getIdcard());
				fields.add(student.getAddress());
				fields.add(classes.getCreatetime());
				fields.add(student.getTelephone());
				fields.add(student.getLoginname());
				fields.add("");
				
				sheet.setColumnWidth(0, 1500);
				sheet.setColumnWidth(1, 5*512);
				sheet.setColumnWidth(2, 3*512);
				sheet.setColumnWidth(3, 13*512);
				sheet.setColumnWidth(4, 20*512);
				sheet.setColumnWidth(5, 8*512);
				sheet.setColumnWidth(6, 8*512);
				sheet.setColumnWidth(7, 8*512);
				sheet.setColumnWidth(8, 5*512);
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
			String fileName1 = fileName+".xls";  
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
	
	//导出班级
	public static void exportClasses(Integer mould,HttpServletRequest request,HttpServletResponse response, String fileName,List<String> fields,List<Student> studentList,List<ClassesVo> classesList) throws IOException{
		if(mould==1){
			exportClassesByxlsx(response,request,  fileName, fields,classesList);
		}
		else if(mould==2){
			exportClassesByxls(response,request, fileName, fields,classesList);
		}
		else{
			System.out.println("发出脚本提示");
		}
	}
	public static void exportClassesByxlsx(
			HttpServletResponse response,
			HttpServletRequest request,
			String fileName,
			List<String> fields, 
			List<ClassesVo> classesList) throws IOException{
		String xuhao=fields.get(0);
		//==========准备工作薄============
		//创建工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		//创建sheet页，指定格式
		XSSFSheet sheet = workbook.createSheet(fileName);
		sheet.setDefaultColumnWidth(20);
		//合并单元格，用于显示表头
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 1, 0, fields.size()-1);
		sheet.addMergedRegion(cellRangeAddress);
		/*//合并单元格，用于显示表头
		CellRangeAddress cellRangeAddress1 = new CellRangeAddress(1, 1, 0, fields.size()-1);
		sheet.addMergedRegion(cellRangeAddress1);*/
		//添加样式
		XSSFCellStyle style = workbook.createCellStyle();
		XSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		style.setFont(font);
		//样式2
		XSSFCellStyle styleLine2 = workbook.createCellStyle();
		XSSFDataFormat formatLine2 = workbook.createDataFormat();
		styleLine2.setDataFormat(formatLine2.getFormat("@"));
		styleLine2.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleLine2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		XSSFFont fontLine2 = workbook.createFont();
		fontLine2.setFontHeightInPoints((short) 11);
		styleLine2.setFont(fontLine2);
		//============ 创建第一行============
		XSSFRow row0 = sheet.createRow(0);
		XSSFCell cell_0 = row0.createCell(0);
		cell_0.setCellStyle(style);
		cell_0.setCellValue(fileName);
		/*//============ 创建第二行============
		XSSFRow rowZ = sheet.createRow(1);
		XSSFCell cell_Z = rowZ.createCell(0);
		cell_Z.setCellStyle(styleLine2);
		cell_Z.setCellValue("培训项目名称：                                                                           编号：                                                                        培训时间：");
		*///============创建第三行============
		XSSFRow row1 = sheet.createRow(2);
		for (int i = 0; i < fields.size(); i++) {
			row1.setHeight((short)(30*20));
			XSSFCell cell_1 = row1.createCell(i);
			cell_1.setCellStyle(style);
			cell_1.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell_1.setCellValue(fields.get(i));
		}
		fields.clear();
		//============创建其它的行============
		//遍历班级
		if(classesList!=null){
			for(int i=0;i<classesList.size();i++){
				XSSFRow row_n = sheet.createRow(i+3);
				Classes classes = classesList.get(i).getClasses();
				if(xuhao.contains("序号")){
					int index=i+1;
					fields.add(String.valueOf(index));
				}
				if(classes.getClassesNo()!=null){
					fields.add(classes.getClassesNo());
				}
				if(classes.getCname()!=null){
					fields.add(classes.getCname());
				}
				if(classes.getCapacity()!=null){
					if(classes.getCapacity()==0){
						fields.add("正常");
					}
					else{
						fields.add("已结课");
					}
				}
				if(classes.getCreatetime()!=null){
					fields.add(classes.getCreatetime());
				}
				if(classes.getStarttime()!=null){
					fields.add(classes.getStarttime());
				}
				if(classesList.get(i).getClassesTeacher()!=null){
					fields.add(classesList.get(i).getClassesTeacher().getUsername());
				}
				if(classesList.get(i).getClassesCount()!=null){
					fields.add(String.valueOf(classesList.get(i).getClassesCount()));
				}
				
				sheet.setColumnWidth(0, 10*512);
				sheet.setColumnWidth(1, 10*512);
				sheet.setColumnWidth(2, 5*512);
				sheet.setColumnWidth(3, 8*512);
				sheet.setColumnWidth(4, 13*512);
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
	
	public static void exportClassesByxls(
			HttpServletResponse response,
			HttpServletRequest request,
			String fileName,
			List<String> fields, 
			List<ClassesVo> classesList) throws IOException{
		String xuhao=fields.get(0);
		//==========准备工作薄============
		//创建工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建sheet页，指定格式
		HSSFSheet sheet = workbook.createSheet(fileName);
		sheet.setDefaultColumnWidth(20);
		//合并单元格，用于显示表头
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 1, 0, fields.size()-1);
		sheet.addMergedRegion(cellRangeAddress);
		//合并单元格，用于显示表头
		/*CellRangeAddress cellRangeAddress1 = new CellRangeAddress(1, 1, 0, fields.size()-1);
		sheet.addMergedRegion(cellRangeAddress1);*/
		//添加样式
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		style.setFont(font);
		//样式2
		HSSFCellStyle styleLine2 = workbook.createCellStyle();
		HSSFDataFormat formatLine2 = workbook.createDataFormat();
		styleLine2.setDataFormat(formatLine2.getFormat("@"));
		styleLine2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleLine2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont fontLine2 = workbook.createFont();
		fontLine2.setFontHeightInPoints((short) 11);
		styleLine2.setFont(fontLine2);
		//============ 创建第一行============
		HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell_0 = row0.createCell(0);
		cell_0.setCellStyle(style);
		cell_0.setCellValue(fileName);
		/*//============ 创建第二行============
		HSSFRow rowZ = sheet.createRow(1);
		HSSFCell cell_Z = rowZ.createCell(0);
		cell_Z.setCellStyle(styleLine2);
		cell_Z.setCellValue("培训项目名称：                                                                           编号：                                                                        培训时间：");
		*///============创建第三行============
		HSSFRow row1 = sheet.createRow(2);
		for (int i = 0; i < fields.size(); i++) {
			row1.setHeight((short)(30*20));
			HSSFCell cell_1 = row1.createCell(i);
			cell_1.setCellStyle(style);
			cell_1.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell_1.setCellValue(fields.get(i));
		}
		fields.clear();
		//============创建其它的行============
		//遍历班级
		if(classesList!=null){
			for(int i=0;i<classesList.size();i++){
				HSSFRow row_n = sheet.createRow(i+3);
				Classes classes = classesList.get(i).getClasses();
				if(xuhao.contains("序号")){
					int index=i+1;
					fields.add(String.valueOf(index));
				}
				if(classes.getClassesNo()!=null){
					fields.add(classes.getClassesNo());
				}
				if(classes.getCname()!=null){
					fields.add(classes.getCname());
				}
				if(classes.getCapacity()!=null){
					if(classes.getCapacity()==0){
						fields.add("正常");
					}
					else{
						fields.add("已结课");
					}
				}
				if(classes.getCreatetime()!=null){
					fields.add(classes.getCreatetime());
				}
				if(classes.getStarttime()!=null){
					fields.add(classes.getStarttime());
				}
				if(classesList.get(i).getClassesTeacher()!=null){
					fields.add(classesList.get(i).getClassesTeacher().getUsername());
				}
				if(classesList.get(i).getClassesCount()!=null){
					fields.add(String.valueOf(classesList.get(i).getClassesCount()));
				}
				
				sheet.setColumnWidth(0, 10*512);
				sheet.setColumnWidth(1, 10*512);
				sheet.setColumnWidth(2, 5*512);
				sheet.setColumnWidth(3, 8*512);
				sheet.setColumnWidth(4, 13*512);
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
	
}

