package cn.edu.qqhru.train.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.qqhru.train.pojo.Log;
import cn.edu.qqhru.train.service.LogService;
import cn.edu.qqhru.train.utils.PageBean;

@Controller
@RequestMapping("/log")
public class LogController {
	
	@Autowired
	LogService logService;
	
	/**
	 * 日志列表
	 * <p>Title: list</p>
	 * <p>Description: </p>
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value="page",defaultValue="1") int page, 
			@RequestParam(value="rows",defaultValue="10") int rows,
			HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		PageBean<Log> pageBean = logService.getLogList(page,rows);
		modelAndView.addObject("url", "list?");
		modelAndView.addObject("pageBean", pageBean);
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		modelAndView.setViewName("log-list");
		return modelAndView;
	}
	
	/**
	 * 删除登录记录
	 * @Description 
	 * @param logId
	 * @return
	 */
	@RequestMapping("/delLog")
	public String delStu(@RequestParam(value="logId")int[] logId,Model model,HttpServletRequest request) {
		logService.delLog(logId);
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		return "log-list";
	}
	
	/**
	 * 根据用户名查找登录人
	 * <p>Title: searchByLogUsername</p>
	 * <p>Description: </p>
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/searchByLogUsername")
	public ModelAndView searchByLogUsername(HttpServletRequest request,
			String logName,@RequestParam(value="page",defaultValue="1") int page, 
			@RequestParam(value="rows",defaultValue="10")int rows) throws UnsupportedEncodingException{
		String a = logName;
		try {
			logName = URLDecoder.decode(logName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView();
		PageBean<Log> pageBean = logService.getLog(logName, page, rows);
		modelAndView.addObject("pageBean", pageBean);
		modelAndView.addObject("url", "searchByLogUsername?logName=" + URLEncoder.encode(a,"iso8859-1"));
		modelAndView.addObject("logName", logName);
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		modelAndView.setViewName("log-list");
		return modelAndView;
	}
	
}
