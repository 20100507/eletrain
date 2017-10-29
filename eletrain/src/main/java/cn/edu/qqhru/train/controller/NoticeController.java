package cn.edu.qqhru.train.controller;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.Notice;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.service.AdminService;
import cn.edu.qqhru.train.service.NoticeService;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.DeleteMultipleObjects;
import cn.edu.qqhru.train.vo.NoticeVo;

/**
 * <p>Title: NoticeController</p>
 * <p>Description: 公告模块的Controller</p>
 * <p>School: qiqihar university</p> 
 * @author	白鹏飞
 * @date	2017年9月2日下午4:26:20
 * @version 1.0.0
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * <p>Title: getNoticeListByPage</p>
	 * <p>Description: 以分页的方式显示所有公告信息</p>
	 * @param identity
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	@RequestMapping("/getNoticeListByPage")
	public String getNoticeListByPage(@RequestParam(value="page",defaultValue="1") int page, 
										@RequestParam(value="rows",defaultValue="8") int rows,
										Model model,
										HttpServletRequest request){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		int identity = 0;
		if (user instanceof Admin){
			Admin admin = (Admin) user;
			identity = admin.getIdentify();
		}else if (user instanceof Teacher) {
			Teacher teacher = (Teacher) user;
			identity = teacher.getIdentify();
		}
		
		PageBean<NoticeVo> pageBean = noticeService.getNoticeListPageBean(identity, page, rows);
		for(NoticeVo notice : pageBean.getList()){
			Admin admin = adminService.getAdmin(notice.getAdminId());
			notice.setAdmin(admin);
		}
		model.addAttribute("pageBean", pageBean);
		
		String path = "getNoticeListByPage?menuId="+menuId;
		model.addAttribute("path", path);
		if(identity == 1){
			return "manage-notice";
		}
		return "notice-list";
	}
	
	/**
	 * <p>Title: getNoticeListByPageAndCondition</p>
	 * <p>Description: 分页显示搜索出来的公告信息</p>
	 * @param identity
	 * @param page
	 * @param rows
	 * @param condition
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/getNoticeListByPageAndCondition")
	public String getNoticeListByPageAndCondition(@RequestParam(value="page",defaultValue="1") int page, 
													@RequestParam(value="rows",defaultValue="8") int rows, 
													@RequestParam String condition, Model model,
													HttpServletRequest request) throws Exception{
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		if(condition == null || condition == ""){
			return "redirect:/notice/getNoticeListByPage?menuId="+menuId+"&page="+page;
		}
		
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		int identity = 0;
		if (user instanceof Admin){
			Admin admin = (Admin) user;
			identity = admin.getIdentify();
		}else if (user instanceof Teacher) {
			Teacher teacher = (Teacher) user;
			identity = teacher.getIdentify();
		}
		//解决get方式提交乱码
		String condition1 = new String(condition.getBytes("ISO-8859-1"),"utf-8"); //转码UTF8
		int condition2 = 0;
		if(adminService.getAdminByUsername(condition1) != null) {
			condition2 = adminService.getAdminByUsername(condition1).getAdminId();
		}
		
		PageBean<NoticeVo> pageBean = noticeService.getNoticeListByPageAndCondition(condition2, identity, page, rows);
		for(NoticeVo notice : pageBean.getList()){
			Admin admin = adminService.getAdmin(notice.getAdminId());
			notice.setAdmin(admin);
		}
		model.addAttribute("pageBean", pageBean);
		String path = "getNoticeListByPageAndCondition?menuId="+menuId+"&condition="+condition1;
		model.addAttribute("path", path);
		if(identity == 1){
			return "manage-notice";
		}
		return "notice-list";
	}
	
	/**
	 * <p>Title: toNoticeInfo</p>
	 * <p>Description: 跳转到notice-info.jsp页面</p>
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/toNoticeInfo")
	public String toNoticeInfo(Integer id, Model model, HttpServletRequest request){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		NoticeVo noticeVo = noticeService.getNoticeById(id);
		Admin admin = adminService.getAdmin(noticeVo.getAdminId());
		noticeVo.setAdmin(admin);
		model.addAttribute("notice", noticeVo);
		return "notice-info";
	}
	
	/**
	 * <p>Title: deleteNotice</p>
	 * <p>Description: 删除公告模块</p>
	 * @param id
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteNotice")
	public String deleteNotice(@RequestParam Integer id, Model model, HttpServletRequest request){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		boolean flag = noticeService.deleteNoticeById(id);
		if(flag){
			
		}else {
			
		}
		return "redirect:/notice/getNoticeListByPage?menuId="+menuId;
	}
	
	/**
	 * <p>Title: deleteMultipleNotices</p>
	 * <p>Description: 批量删除公告模块</p>
	 * @param deleteMultipleObjects
	 * @return
	 */
	@RequestMapping("/deleteMultipleNotices")
	public String deleteMultipleNotices(DeleteMultipleObjects deleteMultipleObjects, HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		if(deleteMultipleObjects.getIds() != null){
			noticeService.deleteMultipleNotices(deleteMultipleObjects.getIds());
		}
		return "redirect:/notice/getNoticeListByPage?menuId="+menuId;
	}
	
	/**
	 * <p>Title: toAddNotice</p>
	 * <p>Description: 跳转到add-notice.jsp页面</p>
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddNotice")
	public String toAddNotice(HttpServletRequest request, Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		return "add-notice";
	}
	
	/**
	 * <p>Title: addNotice</p>
	 * <p>Description: 新增公告模块</p>
	 * @param notice
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/addNotice")
	public String addNotice(Notice notice, HttpServletRequest request, Model model) throws ParseException{
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		if(StringUtils.hasText(notice.getTitle()) && StringUtils.hasText(notice.getContent()) && notice.getIsReleased() != null){
			HttpSession session = request.getSession();
			Admin admin = (Admin) session.getAttribute("user");
			notice.setAdminId(admin.getAdminId());
			notice.setCreatetime(new Date());
			boolean flag = noticeService.addNotice(notice);
			if(flag){
				return "redirect:/notice/getNoticeListByPage?menuId="+menuId;
			}
		}
		return "add-notice";
	}
	
	/**
	 * <p>Title: toAddNotice</p>
	 * <p>Description: 跳转到update-notice.jsp页面</p>
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/toUpdateNotice")
	public String toUpdateNotice(@RequestParam Integer id, Model model, HttpServletRequest request){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		
		Notice notice = noticeService.getNoticeById(id);
		model.addAttribute("notice", notice);
		return "update-notice";
	}
	
	@RequestMapping("/updateNotice")
	public String updateNotice(Notice notice, Model model, HttpServletRequest request){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId", menuId);
		if(StringUtils.hasText(notice.getTitle()) && StringUtils.hasText(notice.getContent()) && notice.getIsReleased() != null){
			boolean flag = noticeService.updateNotice(notice);
			if(flag){
				return "redirect:/notice/getNoticeListByPage?menuId="+menuId;
			}
		}
		return "update-notice";
	}
	
	
}
