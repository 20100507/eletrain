package cn.edu.qqhru.train.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.Notice;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.service.AdminService;
import cn.edu.qqhru.train.service.FunctionService;
import cn.edu.qqhru.train.service.LogService;
import cn.edu.qqhru.train.service.NoticeService;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.FunctionBean;
import cn.edu.qqhru.train.vo.NoticeVo;

/**
 * 登录和权限加载
 * <p>Title: FunctionController</p>
 * <p>Description: </p>
 * <p>School: qiqihar university</p> 
 * @author	BQ
 * @date	2017年9月13日下午3:04:58
 * @version 1.0
 */
@RequestMapping("/function")
@Controller
public class FunctionController {

	@Autowired
	private FunctionService functionService;

	@Autowired
	private LogService logService;
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired AdminService adminService;

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		Object obj = null;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (request.getSession().getAttribute("user") != null) {
			return "index";
		}
		if (request.getSession().getAttribute("user") == null) {
			obj = functionService.getIdentifyByUserAndPwd(username, password);
			if (obj == null) {
				try {
					request.getSession().setAttribute("errorName", username);
					request.getSession().setAttribute("error", "用户名或者密码错误！");
					return "redirect:/index.jsp";
				} catch (Exception e) {
				}
			}
			
			// 把登录行为记录到数据库
			String ip = getIpAddr(request);
			logService.recordLog(obj,ip);
		}
		if (obj instanceof Admin) {
			Admin admin = (Admin) obj;
			request.getSession().setAttribute("user", admin);
			List<FunctionBean> authFunctionByIdentify = functionService
					.getAuthFunctionByIdentify(admin.getIdentify().toString());
			request.getSession().setAttribute("functionParent", authFunctionByIdentify);
			PageBean<NoticeVo> pageBean = noticeService.getNoticeListPageBean(admin.getIdentify(), 1, 10);
			List<NoticeVo> list2 = pageBean.getList();
			for (NoticeVo noticeVo : list2) {
				Admin admin1 = adminService.getAdmin(noticeVo.getAdminId());
				noticeVo.setAdmin(admin1);
			}
			int count = noticeService.getCount(admin.getIdentify());
			request.getSession().setAttribute("count1", count);
			request.getSession().setAttribute("notices", list2);
		}

		if (obj instanceof Teacher) {
			Teacher teacher = (Teacher) obj;
			request.getSession().setAttribute("user", teacher);
			List<FunctionBean> authFunctionByIdentify = functionService
					.getAuthFunctionByIdentify(teacher.getIdentify().toString());
			request.getSession().setAttribute("functionParent", authFunctionByIdentify);
			PageBean<NoticeVo> pageBean = noticeService.getNoticeListPageBean(teacher.getIdentify(), 1, 10);
			List<NoticeVo> list2 = pageBean.getList();
			for (NoticeVo noticeVo : list2) {
				Admin admin1 = adminService.getAdmin(noticeVo.getAdminId());
				noticeVo.setAdmin(admin1);
			}
			int count = noticeService.getCount(teacher.getIdentify());
			request.getSession().setAttribute("count1", count);
			request.getSession().setAttribute("notices", list2);
		}
		
		
		return "index";
	}

	@RequestMapping("/next")
	@ResponseBody
	public List<FunctionBean> getFunctionBeanById(String id) {
		List<FunctionBean> functionBean = functionService.getFunctionBeanById(id);
		return functionBean;
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/index.jsp";
	}

	@RequestMapping("/modifyPwd")
	@ResponseBody
	public String modifyPwd(HttpServletRequest request, HttpServletResponse response) {
		String newPwd = request.getParameter("newPwd");
		if (newPwd.trim().equals("") || newPwd == null || newPwd.trim().contains(" ")) {
			return "error";
		}
		boolean flag = false;
		Object user = request.getSession().getAttribute("user");
		if (user == null) {
			try {
				response.sendRedirect("index.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		flag = functionService.modifyPwd(user, newPwd);
		if (flag) {
			return "success";
		}
		return "error";
	}

	/**
	 *  获取IP
	 * <p>Title: getIpAddr</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
}
