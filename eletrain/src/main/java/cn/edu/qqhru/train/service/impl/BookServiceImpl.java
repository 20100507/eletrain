package cn.edu.qqhru.train.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.qqhru.train.mapper.BookMapper;
import cn.edu.qqhru.train.mapper.PlanBooksMapper;
import cn.edu.qqhru.train.pojo.Book;
import cn.edu.qqhru.train.pojo.BookExample;
import cn.edu.qqhru.train.pojo.BookExample.Criteria;
import cn.edu.qqhru.train.pojo.PlanBooksExample;
import cn.edu.qqhru.train.service.BookService;
import cn.edu.qqhru.train.utils.PageBean;
@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookMapper bookMapper;
	@Autowired PlanBooksMapper planBooksMapper;
	/**
	 * 查询所有教材
	 */
	@Override
	public PageBean<Book> getAllBook(int page, int rows) {
		PageBean<Book> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows,"book_id desc");
		BookExample example = new BookExample();
		Criteria criteria = example.createCriteria();
		criteria.andBookIdIsNotNull();
		List<Book> list = bookMapper.selectByExample(example);
		PageInfo<Book> pageInfo = new PageInfo<>(list);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		return pageBean;
	}
	/**
	 * 删除教材
	 */
	@Override
	public void delBook(int[] ids) {
		BookExample example = new BookExample();
		List<Integer> id = new ArrayList<>();
		for (int i : ids) {
			id.add(i);
		}
		Criteria criteria = example.createCriteria();
		criteria.andBookIdIn(id);
		bookMapper.deleteByExample(example);
		PlanBooksExample example2 = new PlanBooksExample();
		example2.createCriteria().andBookIdIn(id);
		planBooksMapper.deleteByExample(example2);
	}
	/**
	 * 增加教材
	 */
	@Override
	public void addBook(Book book) {
		bookMapper.insert(book);
	}
	/**
	 * 根据id查询一个教材
	 */
	@Override
	public Book getBookOne(int id) {
		Book book = bookMapper.selectByPrimaryKey(id);
		return book;
	}
	/**
	 * 编辑教材
	 */
	@Override
	public void editBook(Book book) {
		bookMapper.updateByPrimaryKey(book);		
	}
	/**
	 * 根据教材名称搜索教材
	 */
	@Override
	public PageBean<Book> getBook(String bname, int page, int rows) {
		PageBean<Book> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows,"book_id desc");
		BookExample example = new BookExample();
		Criteria criteria = example.createCriteria();
		criteria.andBnameLike("%" + bname + "%");
		List<Book> list = bookMapper.selectByExample(example);
		PageInfo<Book> pageInfo = new PageInfo<>(list);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		return pageBean;
	}
	/**
	 * 导入教材
	 */
	@Override
	public void importBook(List<Book> bookList) {
		for (Book book : bookList) {
			bookMapper.insert(book);
		}			
	}
	/**
	 * 导出教材
	 */
	@Override
	public List<Book> getExportBook() {
		BookExample example = new BookExample();
		Criteria criteria = example.createCriteria();
		criteria.andBookIdIsNotNull();
		List<Book> list = bookMapper.selectByExample(example);
		return list;
	}

}
