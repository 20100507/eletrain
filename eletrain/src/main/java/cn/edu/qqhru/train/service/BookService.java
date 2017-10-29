package cn.edu.qqhru.train.service;

import java.util.List;

import cn.edu.qqhru.train.pojo.Book;
import cn.edu.qqhru.train.utils.PageBean;

public interface BookService {
	// 获取所有教材信息,分页
	PageBean<Book> getAllBook(int page, int rows);
	// 删除教材信息
	void delBook(int [] ids);
	// 添加一条教材信息
	void addBook(Book book);
	// 根据id获取教材信息
	Book getBookOne(int id);
	// 修改教材信息
	void editBook(Book book);
	// 查询指定教材
	PageBean<Book> getBook(String bname,int page,int rows);
	// 批量导入教材信息
	void importBook(List<Book> bookList);
	// 获取所有设备信息，导出
	List<Book> getExportBook();
}
