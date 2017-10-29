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

import cn.edu.qqhru.train.pojo.Book;
import cn.edu.qqhru.train.service.BookService;
import cn.edu.qqhru.train.utils.DownUtils;
import cn.edu.qqhru.train.utils.PageBean;

@Controller
@RequestMapping("/books")
public class BookController {
	private String filename;
	@Autowired
	private BookService bookService;
	/**
	 * 查询所有教材
	 * @Description 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/getAllBooks")
	public ModelAndView getAllBooks(@RequestParam(value="page",defaultValue="1") int page,
			@RequestParam(value="rows",defaultValue="10")int rows,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		PageBean<Book> pageBean = bookService.getAllBook(page, rows);
		modelAndView.addObject("url", "getAllBooks?");
		modelAndView.addObject("pageBean", pageBean);
		modelAndView.setViewName("book-list");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	/**
	 * 删除教材
	 * @Description 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delBook")
	public String delStu(@RequestParam(value="ids")int[] ids,Model model,HttpServletRequest request) {
		bookService.delBook(ids);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "book-list";
	}
	/**
	 * 增加教材页面跳转
	 * @Description 
	 * @return
	 */
	@RequestMapping("/addBookPage")
	public String addPage(Model model,HttpServletRequest request) {
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "add-book";
	}
	/**
	 * 增加教材
	 * @Description 
	 * @param stu
	 * @return
	 */
	@RequestMapping("/addBook")
	public String addDev(Book book,Model model,HttpServletRequest request) {
		bookService.addBook(book);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "redirect:/books/getAllBooks";
	}
	/**
	 * 编辑教材页面跳转
	 * @Description 
	 * @param id
	 * @return
	 */
	@RequestMapping("/editBookPage")
	public ModelAndView editPage(@RequestParam(value="id")int id,HttpServletRequest request) {
		Book book = bookService.getBookOne(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("bookInfo", book);
		modelAndView.setViewName("edit-book");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	/**
	 * 编辑教材
	 * @Description 
	 * @param stu
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/editBook")
	public String editStu(Book Book,HttpServletResponse response,Model model,HttpServletRequest request) throws Exception {
		bookService.editBook(Book);
    	response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('修改成功');window.location.href='/books/getAllBooks?menuId=7'</script>");
		out.flush();
		out.close();
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "book-list";
	}
	/**
	 * 以教材名称搜索教材
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/searchByBookName")
	public ModelAndView searchByLoginname(HttpServletRequest request,String bname,@RequestParam(value="page",defaultValue="1") int page, @RequestParam(value="rows",defaultValue="10")int rows) throws UnsupportedEncodingException {
		String a = bname;
		try {
			bname = URLDecoder.decode(bname,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView();
		PageBean<Book> pageBean = bookService.getBook(bname, page, rows);
		modelAndView.addObject("pageBean", pageBean);
		modelAndView.addObject("url", "searchByBookName?bname=" + URLEncoder.encode(a,"iso8859-1"));
		modelAndView.addObject("bname", bname);
		modelAndView.setViewName("book-list");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	/**
	 * 查询教材详情
	 * @Description 
	 * @param id
	 * @return
	 */
	@RequestMapping("/bookInfo")
	public ModelAndView stuInfo(int id,HttpServletRequest request) {
		Book book = bookService.getBookOne(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("bookInfo", book);
		modelAndView.setViewName("book-info");
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
			filename = "教材信息录入表.xlsx";
		}else {
			filename = "教材信息录入表.xls";
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
	public String upload(@RequestParam("file") CommonsMultipartFile file,HttpServletResponse response,Model model,HttpServletRequest request) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		InputStream inputStream = file.getInputStream();
		List<String> fields = new ArrayList<>();
		List<Book> bookList = new ArrayList<>();
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
					if (fields.size()>0) {
					Book book = new Book();
					book.setBname(fields.get(0).replace(" ", ""));
					book.setAuthor(fields.get(1).replace(" ", ""));
					book.setPress(fields.get(2).replace(" ", ""));
					book.setCount(Integer.parseInt(fields.get(3).replace(" ", "")));
					bookList.add(book);
					fields.clear();
					}
				}  
				
			} catch (Exception e) {
				inputStream.close();
				out.print("<script>alert('导入的文件内容不符合格式，请按照模板写入');window.location.href='/books/getAllBooks?menuId=7'</script>");
				out.flush();
				out.close();
				String menuId = request.getParameter("menuId");
				model.addAttribute("menuId", menuId);
				return "book-list";
			}
			bookService.importBook(bookList);
			inputStream.close();
			out.print("<script>alert('导入成功');window.location.href='/books/getAllBooks?menuId=7'</script>");
			out.flush();
			out.close();
			String menuId = request.getParameter("menuId");
			model.addAttribute("menuId", menuId);
			return "book-list";
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
					if (fields.size()>0) {
					Book book = new Book();
					book.setBname(fields.get(0).replace(" ", ""));
					book.setAuthor(fields.get(1).replace(" ", ""));
					book.setPress(fields.get(2).replace(" ", ""));
					book.setCount(Integer.parseInt(fields.get(3).replace(" ", "")));
					bookList.add(book);
					fields.clear();
					}
				}  
				
			} catch (Exception e) {
				inputStream.close();
				out.print("<script>alert('导入的文件内容不符合格式，请按照模板写入');window.location.href='/books/getAllBooks?menuId=7'</script>");
				out.flush();
				out.close();
				String menuId = request.getParameter("menuId");
				model.addAttribute("menuId", menuId);
				return "book-list";
			}
			bookService.importBook(bookList);
			inputStream.close();
			out.print("<script>alert('导入成功');window.location.href='/books/getAllBooks?menuId=7'</script>");
			out.flush();
			out.close();
			String menuId = request.getParameter("menuId");
			model.addAttribute("menuId", menuId);
			return "book-list";
		}
		inputStream.close();
		out.print("<script>alert('文件格式不正确');window.location.href='/books/getAllBooks?menuId=7'</script>");
		out.flush();
		out.close();
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "book-list";
	}
	/**
	 * 导出Xls
	 */
	@RequestMapping("/exportFile")
	public ModelAndView exportFile(int type,HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Book> exportBook = bookService.getExportBook();
		List<String> fields = new ArrayList<>();
		fields.add("教材名称");
		fields.add("作者");
		fields.add("出版社");
		fields.add("数量");
		DownUtils.exportReport(type,request, response,null, "教材信息",null, fields, null, null, null,null,exportBook,null,null);
		return null;
	}
}
