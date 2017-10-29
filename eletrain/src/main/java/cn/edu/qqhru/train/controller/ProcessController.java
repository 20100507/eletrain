package cn.edu.qqhru.train.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.Application;
import cn.edu.qqhru.train.pojo.Approveinfo;
import cn.edu.qqhru.train.pojo.Log;
import cn.edu.qqhru.train.service.ProcessService;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.ApplicationVo;
import cn.edu.qqhru.train.vo.ApproveInfoVo;
import cn.edu.qqhru.train.vo.PositionVo;
import cn.edu.qqhru.train.vo.TaskViewVo;

/**
 * 流程处理
 * <p>Title: ProcessController</p>
 * <p>Description: </p>
 * <p>School: qiqihar university</p> 
 * @author	BQ
 * @date	2017年9月11日下午8:05:11
 * @version 1.0
 */
@Controller
@RequestMapping("/process")
public class ProcessController {
	
	@Autowired
	private ProcessService processService;
	
	/**
	 * 获取所有流程列表
	 * @Description 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView getAllProcess(@RequestParam(value="page",defaultValue="1") int page, 
			@RequestParam(value="rows",defaultValue="10")int rows,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Admin admin = null;
		Object user = request.getSession().getAttribute("user");
		if(user instanceof Admin){
			admin = (Admin) user;
		}
		PageBean<ApplicationVo> pageBean = processService.list(page,rows,admin);
		modelAndView.addObject("pageBean", pageBean);
		modelAndView.setViewName("process-list");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	
	/**
	 * 根据当前登录人查询对应的任务列表，包装成@List<TaskViewVo>返回
	 * <p>Title: getProcessing</p>
	 * <p>Description: </p>
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping("/flowProcessList")
	public ModelAndView getProcessing(@RequestParam(value="page",defaultValue="1") int page, 
			@RequestParam(value="rows",defaultValue="10")int rows,
			HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		Admin admin = null;
		Object user = request.getSession().getAttribute("user");
		if(user instanceof Admin){
			admin = (Admin) user;
		}
		PageBean<TaskViewVo> list = processService.getProcessing(page, rows, admin);
		modelAndView.addObject("pageBean", list);
		modelAndView.setViewName("process-exe");
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return modelAndView;
	}
	
	
	/**
	 * 跳转到审批页面
	 * <p>Title: submitFlowPage</p>
	 * <p>Description: </p>
	 * @param id
	 * @return
	 */
	@RequestMapping("/submitFlowPage")
	public ModelAndView submitFlowPage(@RequestParam(value="id")int id,int taskId,
			HttpServletRequest request){
		Application app = processService.getFlowOne(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("appInfo",app);
		modelAndView.addObject("taskId",taskId);
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		modelAndView.setViewName("process-submit");
		return modelAndView;
	}
	
	/**
	 * 处理审批审批意见
	 * <p>Title: processFlow</p>
	 * <p>Description: </p>
	 * @param approveinfo
	 * @return
	 */
	@RequestMapping("/processFlow")
	public String processFlow(Approveinfo approveinfo,String taskId,HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView();
		Admin admin = null;
		Object user = request.getSession().getAttribute("user");
		if(user != null && user instanceof Admin){
			admin = (Admin) user;
		}
		processService.processFlow(approveinfo,taskId,admin);
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		return "redirect:/process/flowProcessList?menuId="+menuId;
	}
	
	/**
	 * 查询该方案的流程扭转详情
	 * @Description 
	 * @param id
	 * @return
	 */
	@RequestMapping("/processInfo")
	public ModelAndView processInfo(int appId,@RequestParam(value="page",defaultValue="1") int page, 
			@RequestParam(value="rows",defaultValue="10")int rows,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		 PageBean<ApproveInfoVo> pageBean = processService.findProcessInfoByAppId(appId);
		modelAndView.addObject("pageBean", pageBean);
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		modelAndView.setViewName("process-info");
		return modelAndView;
	}
	/**
	 * 查看流程图片
	 */
	@RequestMapping("/showImage")
	public ModelAndView showPng(String pdId,HttpServletResponse response,String applicationId,
			HttpServletRequest request){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("image/x-png");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			//使用GUI画图【硬编码】
			String path = request.getSession().getServletContext().getRealPath("/") + "process\\plan.png";
	        //这个坐标作为流程变量 流程任务全部结束 流程变量消失 ,所以 审批完成 流程进展图 不可以显示
			PositionVo showPngWithXY = showPngWithXY(applicationId);
	        BufferedImage bimg=ImageIO.read(new FileInputStream(path));
	         //得到Graphics2D 对象
	        Graphics2D g2d=(Graphics2D)bimg.getGraphics();
	        //设置颜色和画笔粗细
	        g2d.setColor(new Color(255,0,0));
	        g2d.setStroke(new BasicStroke(5));
	        int x = showPngWithXY.getX();
	        int y = showPngWithXY.getY();
	        int width = showPngWithXY.getWidth();
	        int height = showPngWithXY.getHeight();
	        g2d.drawRect(x, y, width, height);
	        g2d.setColor(Color.BLUE);
	        g2d.setFont(new Font("楷体",Font.CENTER_BASELINE,15));
	        g2d.drawString("审批流程图[齐齐哈尔电力培训中心]",10,50);
	        //保存新图片
	        ImageIO.write(bimg, "png",outputStream);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询出宽度和高度
	 * <p>Title: showPngWithXY</p>
	 * <p>Description: </p>
	 * @param applicationId
	 * @return
	 */
	private PositionVo showPngWithXY(String applicationId){
		Task task = processService.findTaskByApplicationId(Integer.parseInt(applicationId));
		PositionVo position = processService.findCoordingByTask(task);
		return position;
	}
	
	/**
	 * 根据方案名称查询
	 * <p>Title: searchByProcessName</p>
	 * <p>Description: </p>
	 * @param request
	 * @param processName
	 * @param page
	 * @param rows
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/searchByPlanName")
	public ModelAndView searchByPlanName(HttpServletRequest request,
			String planName,@RequestParam(value="page",defaultValue="1") int page, 
			@RequestParam(value="rows",defaultValue="10")int rows) throws UnsupportedEncodingException{
		String a = planName;
		try {
			planName = URLDecoder.decode(planName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView();
		PageBean<ApplicationVo> pageBean = processService.getPlanByName(planName, page, rows);
		modelAndView.addObject("pageBean", pageBean);
		modelAndView.addObject("url", "searchByPlanName?PlanName=" + URLEncoder.encode(a,"iso8859-1"));
		modelAndView.addObject("planName", planName);
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		modelAndView.setViewName("process-list");
		return modelAndView;
	}
	
	/**
	 * 领导登录审批列表的查询
	 * <p>Title: getProcessByName</p>
	 * <p>Description: </p>
	 * @param planName
	 * @param admin
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getProcessByName")
	public  ModelAndView getProcessByName(String planName,Admin admin,
			@RequestParam(value="page",defaultValue="1") int page, 
			@RequestParam(value="rows",defaultValue="10")int rows,
			HttpServletRequest request) throws UnsupportedEncodingException{
		String a = planName;
		try {
			planName = URLDecoder.decode(planName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView();
		Object user = request.getSession().getAttribute("user");
		if(user instanceof Admin){
			admin = (Admin) user;
		}
		PageBean<TaskViewVo> list = processService.getProcessByName(planName,admin,page, rows);
		modelAndView.addObject("pageBean", list);
		modelAndView.addObject("url", "getProcessByName?planName=" + URLEncoder.encode(a,"iso8859-1"));
		modelAndView.addObject("planName", planName);
		String menuId = request.getParameter("menuId");
		modelAndView.addObject("menuId", menuId);
		modelAndView.setViewName("process-exe");
		return modelAndView;
	}
	
}
