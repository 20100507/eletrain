package cn.edu.qqhru.train.interceptor;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.Teacher;

/**
 * 拦截登录和资源管理权限
 * <p>Title: LoginInterceptor</p>
 * <p>Description: </p>
 * <p>School: qiqihar university</p> 
 * @author	BQ
 * @date	2017年9月9日下午11:17:16
 * @version 1.0
 */
public class LoginInterceptor implements HandlerInterceptor{
	
	@Value("${LOGIN_URL}")
	private String LOGIN_URL;
	@Value("${}")
	
	//TEACHER_DEFINE 老师  ADMIN_DEFINE 管理员 LEADER_DEFINE 领导 HEAD_DEFINE 班主任
	private static final List<String> ADMIN_DEFINE = new LinkedList<String>(); 
	private static final List<String> LEADER_DEFINE = new LinkedList<String>(); 
	private static final List<String> TEACHER_DEFINE = new LinkedList<String>(); 
	private static final List<String> HEAD_DEFINE = new LinkedList<String>(); 

	static{
		
		//=========================================管理员=======================
		
		//系统的模块
		ADMIN_DEFINE.add("/function/next");
		ADMIN_DEFINE.add("/function/modifyPwd");
		//管理员模块
		ADMIN_DEFINE.add("/admin/getAdminListByPage");
		ADMIN_DEFINE.add("/admin/getAdminListByPageAndCondition");
		ADMIN_DEFINE.add("/admin/toAddAdmin");
		ADMIN_DEFINE.add("/admin/addAdmin");
		ADMIN_DEFINE.add("/admin/toUpdateAdmin");
		ADMIN_DEFINE.add("/admin/updateAdmin");
		ADMIN_DEFINE.add("/admin/deleteAdmin");
		ADMIN_DEFINE.add("/admin/deleteMultipleAdmins");
		ADMIN_DEFINE.add("/admin/initializePassword");
		ADMIN_DEFINE.add("/admin/downloadTemplate");
		ADMIN_DEFINE.add("/admin/importData");
		ADMIN_DEFINE.add("/admin/exportData");
		//教材模块
		ADMIN_DEFINE.add("/books/getAllBooks");
		ADMIN_DEFINE.add("/books/delBook");
		ADMIN_DEFINE.add("/books/addBookPage");
		ADMIN_DEFINE.add("/books/addBook");
		ADMIN_DEFINE.add("/books/editBookPage");
		ADMIN_DEFINE.add("/books/editBook");
		ADMIN_DEFINE.add("/books/searchByBookName");
		ADMIN_DEFINE.add("/books/bookInfo");
		ADMIN_DEFINE.add("/books/download");
		ADMIN_DEFINE.add("/books/upload");
		ADMIN_DEFINE.add("/books/exportFile");
		//班级模块
		ADMIN_DEFINE.add("/classes/list");
		ADMIN_DEFINE.add("/classes/addClassesUI");
		ADMIN_DEFINE.add("/classes/addClasses");
		ADMIN_DEFINE.add("/classes/addClassesUIByClassesTeacher");
		ADMIN_DEFINE.add("/classes/addClassesByClassesTeacher");
		ADMIN_DEFINE.add("/classes/deleteClasses");
		ADMIN_DEFINE.add("/classes/deleteClassesByClassesTeacher");
		ADMIN_DEFINE.add("/classes/updClasses");
		ADMIN_DEFINE.add("/classes/searchByLoginnameByClassesTeacher");
		ADMIN_DEFINE.add("/classes/searchByLoginname");
		ADMIN_DEFINE.add("/classes/updateClassesUI");
		ADMIN_DEFINE.add("/classes/updateClasses");
		ADMIN_DEFINE.add("/classes/updateClassesUIByClassesTeacher");
		ADMIN_DEFINE.add("/classes/updateClassesByClassesTeacher");
		ADMIN_DEFINE.add("/classes/insertOrDeleteUI");
		ADMIN_DEFINE.add("/classes/insertOrDelete");
		ADMIN_DEFINE.add("/classes/insertOrDeleteUIByClassesTeacher");
		ADMIN_DEFINE.add("/classes/insertOrDeleteByClassesTeacher");
		ADMIN_DEFINE.add("/classes/getClassesByTeacherId");
		ADMIN_DEFINE.add("/classes/lookInfo");
		ADMIN_DEFINE.add("/classes/searchClasses");
		ADMIN_DEFINE.add("/classes/delClassesByIds");
		ADMIN_DEFINE.add("/classes/delClassesByIdsByClassesTeacher");
		ADMIN_DEFINE.add("/classes/updClassesByIds");
		ADMIN_DEFINE.add("/classes/exportTeacher");
		ADMIN_DEFINE.add("/classes/exportStudent");
		ADMIN_DEFINE.add("/classes/exportCheAttance");
		ADMIN_DEFINE.add("/classes/exportClassesSyllabus");
		ADMIN_DEFINE.add("/classes/exportClasses");
		ADMIN_DEFINE.add("/classes/classesNoContentChecked");
		//课程模块
		ADMIN_DEFINE.add("/course/getCourseListByPage");
		ADMIN_DEFINE.add("/course/getCourseListByPageAndCondition");
		ADMIN_DEFINE.add("/course/toAddCourse");
		ADMIN_DEFINE.add("/course/findTeacherList");
		ADMIN_DEFINE.add("/course/addCourse");
		ADMIN_DEFINE.add("/course/toUpdateCourse");
		ADMIN_DEFINE.add("/course/updateCourse");
		ADMIN_DEFINE.add("/course/deleteCourse");
		ADMIN_DEFINE.add("/course/deleteMultipleCourses");
		ADMIN_DEFINE.add("/course/downloadTemplate");
		ADMIN_DEFINE.add("/course/importData");
		ADMIN_DEFINE.add("/course/exportData");
		//设备模块
		ADMIN_DEFINE.add("/devices/getAllDevices");
		ADMIN_DEFINE.add("/devices/delDev");
		ADMIN_DEFINE.add("/devices/addDevPage");
		ADMIN_DEFINE.add("/devices/addDev");
		ADMIN_DEFINE.add("/devices/editDevPage");
		ADMIN_DEFINE.add("/devices/editDev");
		ADMIN_DEFINE.add("/devices/searchByDeviceName");
		ADMIN_DEFINE.add("/devices/devInfo");
		ADMIN_DEFINE.add("/devices/download");
		ADMIN_DEFINE.add("/devices/upload");
		ADMIN_DEFINE.add("/devices/exportFile");
		//日志模块
		ADMIN_DEFINE.add("/log/list");
		ADMIN_DEFINE.add("/log/delLog");
		ADMIN_DEFINE.add("/log/searchByLogUsername");
		//统计模块
		ADMIN_DEFINE.add("/logStatistics/getLogStatistics");
		//通知模块
		ADMIN_DEFINE.add("/notice/getNoticeListByPage");
		ADMIN_DEFINE.add("/notice/getNoticeListByPageAndCondition");
		ADMIN_DEFINE.add("/notice/toNoticeInfo");
		ADMIN_DEFINE.add("/notice/deleteNotice");
		ADMIN_DEFINE.add("/notice/addNotice");
		ADMIN_DEFINE.add("/notice/deleteMultipleNotices");
		ADMIN_DEFINE.add("/notice/toAddNotice");
		ADMIN_DEFINE.add("/notice/toUpdateNotice");
		ADMIN_DEFINE.add("/notice/updateNotice");
		//方案模块
		ADMIN_DEFINE.add("/plan/getAllPlan");
		ADMIN_DEFINE.add("/plan/checkPname");
		ADMIN_DEFINE.add("/plan/planInfo");
		ADMIN_DEFINE.add("/plan/delPlan");
		ADMIN_DEFINE.add("/plan/findAdminList");
		ADMIN_DEFINE.add("/plan/findClassesList");
		ADMIN_DEFINE.add("/plan/findDevicesList");
		ADMIN_DEFINE.add("/plan/findBookList");
		ADMIN_DEFINE.add("/plan/findTeaList");
		ADMIN_DEFINE.add("/plan/addPlanPage");
		ADMIN_DEFINE.add("/plan/addPlan");
		ADMIN_DEFINE.add("/plan/editPlanPage");
		ADMIN_DEFINE.add("/plan/editPlan");
		ADMIN_DEFINE.add("/plan/searchByPlanName");
		ADMIN_DEFINE.add("/plan/exportFile");
		ADMIN_DEFINE.add("/plan/download");
		ADMIN_DEFINE.add("/plan/upload");
		ADMIN_DEFINE.add("/plan/downloadAllFile");
		ADMIN_DEFINE.add("/plan/submitApp");
		//流程模块
		ADMIN_DEFINE.add("/process/list");
		ADMIN_DEFINE.add("/process/flowProcessList");
		ADMIN_DEFINE.add("/process/processFlow");
		ADMIN_DEFINE.add("/process/processInfo");
		ADMIN_DEFINE.add("/process/showImage");
		ADMIN_DEFINE.add("/process/searchByPlanName");
		//分数模块
		ADMIN_DEFINE.add("/score/getScoreListByPage");
		ADMIN_DEFINE.add("/score/getScoreListByPageAndCondition");
		ADMIN_DEFINE.add("/score/toAddScore");
		ADMIN_DEFINE.add("/score/addScore");
		ADMIN_DEFINE.add("/score/toUpdateScore");
		ADMIN_DEFINE.add("/score/updateScore");
		ADMIN_DEFINE.add("/score/findCourseList");
		ADMIN_DEFINE.add("/score/findTeacherList");
		ADMIN_DEFINE.add("/score/findClassesList");
		ADMIN_DEFINE.add("/score/findClassesListByTeacherId");
		ADMIN_DEFINE.add("/score/findStudentListByClassesId");
		ADMIN_DEFINE.add("/score/exportData");
		//学生模块
		ADMIN_DEFINE.add("/student/allStu");
		ADMIN_DEFINE.add("/student/allFinishStu");
		ADMIN_DEFINE.add("/student/allNotFinishStu");
		ADMIN_DEFINE.add("/student/delStu");
		ADMIN_DEFINE.add("/student/addPage");
		ADMIN_DEFINE.add("/student/addStu");
		ADMIN_DEFINE.add("/student/editPage");
		ADMIN_DEFINE.add("/student/editStu");
		ADMIN_DEFINE.add("/student/searchByLoginname");
		ADMIN_DEFINE.add("/student/initPassword");
		ADMIN_DEFINE.add("/student/stuInfo");
		ADMIN_DEFINE.add("/student/download");
		ADMIN_DEFINE.add("/student/upload");
		ADMIN_DEFINE.add("/student/exportFile");
		ADMIN_DEFINE.add("/student/checkNo");
		ADMIN_DEFINE.add("/student/allFinishClasses");
		ADMIN_DEFINE.add("/student/allNoFinishClasses");
		//课表管理
		ADMIN_DEFINE.add("/syllabus/list");
		ADMIN_DEFINE.add("/syllabus/searchSyllabus");
		ADMIN_DEFINE.add("/syllabus/addSyllabusUI");
		ADMIN_DEFINE.add("/syllabus/addSyllabusUIByClassesTeacher");
		ADMIN_DEFINE.add("/syllabus/getSyllabusByTeacher");
		ADMIN_DEFINE.add("/syllabus/addSyllabus");
		ADMIN_DEFINE.add("/syllabus/addSyllabusByClassesTeacher");
		ADMIN_DEFINE.add("/syllabus/updateSyllabusUI");
		ADMIN_DEFINE.add("/syllabus/updateSyllabusUIByClassesTeacher");
		ADMIN_DEFINE.add("/syllabus/updateSyllabus");
		ADMIN_DEFINE.add("/syllabus/updateSyllabusByClassesTeacher");
		ADMIN_DEFINE.add("/syllabus/deleteSyllabusByIds");
		ADMIN_DEFINE.add("/syllabus/deleteSyllabusByIdsByClassesTeacher");
		ADMIN_DEFINE.add("/syllabus/exportSyllabus");
		ADMIN_DEFINE.add("/syllabus/checkDatePlace");
		
		//教师模块
		ADMIN_DEFINE.add("/teacher/list");
		ADMIN_DEFINE.add("/teacher/addTeacherUI");
		ADMIN_DEFINE.add("/teacher/updTeacherByIds");
		ADMIN_DEFINE.add("/teacher/addTeacher");
		ADMIN_DEFINE.add("/teacher/addTeacher");
		ADMIN_DEFINE.add("/teacher/deleteTeacher");
		ADMIN_DEFINE.add("/teacher/updateTeacherUI");
		ADMIN_DEFINE.add("/teacher/updateTeacher");
		ADMIN_DEFINE.add("/teacher/init");
		ADMIN_DEFINE.add("/teacher/lookInfo");
		ADMIN_DEFINE.add("/teacher/searchTeacher");
		ADMIN_DEFINE.add("/teacher/delTeacherByIds");
		ADMIN_DEFINE.add("/teacher/downLoad");
		ADMIN_DEFINE.add("/teacher/upload");
		ADMIN_DEFINE.add("/teacher/export");
		ADMIN_DEFINE.add("/teacher/LoginNameContentChecked");
		
		//工具包模块
		ADMIN_DEFINE.add("/utils/uploadImage");
		ADMIN_DEFINE.add("/utils/verifyLoginnameIsExist");
		//===============================领导路径添加================================

		
		//系统功能
		LEADER_DEFINE.add("/function/next");
		LEADER_DEFINE.add("/function/modifyPwd");
		//工具包模块
		LEADER_DEFINE.add("/utils/uploadImage");
		//流程模块
		LEADER_DEFINE.add("/process/list");
		LEADER_DEFINE.add("/process/flowProcessList");
		LEADER_DEFINE.add("/process/submitFlowPage");
		LEADER_DEFINE.add("/process/processFlow");
		LEADER_DEFINE.add("/process/processInfo");
		LEADER_DEFINE.add("/process/showImage");
		LEADER_DEFINE.add("/process/getProcessByName");
		//统计模块
		LEADER_DEFINE.add("/logStatistics/getLogStatistics");
		//通知模块
		LEADER_DEFINE.add("/notice/getNoticeListByPage");
		LEADER_DEFINE.add("/notice/getNoticeListByPageAndCondition");
		LEADER_DEFINE.add("/notice/toNoticeInfo");
		//计划模块
		LEADER_DEFINE.add("/plan/download");
		LEADER_DEFINE.add("/plan/downloadAllFile");
		// 获取培训方案相关文档
		LEADER_DEFINE.add("/plan/exportFile");
	    //==============================班主任==============================
		
		//系统功能
		HEAD_DEFINE.add("/function/next");
		HEAD_DEFINE.add("/function/modifyPwd");
		//班级模块
		
		HEAD_DEFINE.add("/classes/getClassesByClassesTeacherId");
		HEAD_DEFINE.add("/classes/addClassesUIByClassesTeacher");
		HEAD_DEFINE.add("/classes/addClassesByClassesTeacher");
		HEAD_DEFINE.add("/classes/deleteClassesByClassesTeacher");
		HEAD_DEFINE.add("/classes/searchByLoginnameByClassesTeacher");
		HEAD_DEFINE.add("/classes/updateClassesUIByClassesTeacher");
		HEAD_DEFINE.add("/classes/updateClassesByClassesTeacher");
		HEAD_DEFINE.add("/classes/insertOrDeleteUIByClassesTeacher");
		HEAD_DEFINE.add("/classes/insertOrDeleteByClassesTeacher");
		HEAD_DEFINE.add("/classes/getClassesByTeacherId");
		HEAD_DEFINE.add("/classes/lookInfo");
		HEAD_DEFINE.add("/classes/searchClasses");
		HEAD_DEFINE.add("/classes/delClassesByIdsByClassesTeacher");
		HEAD_DEFINE.add("/classes/updClassesByIds");
		HEAD_DEFINE.add("/classes/exportTeacher");
		HEAD_DEFINE.add("/classes/exportStudent");
		HEAD_DEFINE.add("/classes/exportCheAttance");
		HEAD_DEFINE.add("/classes/exportClassesSyllabus");
		HEAD_DEFINE.add("/classes/searchClassesClassesTeacher");
		
		//课程模块
		HEAD_DEFINE.add("/course/getCourseListByPage");
		HEAD_DEFINE.add("/course/getCourseListByPageAndCondition");
		HEAD_DEFINE.add("/course/toAddCourse");
		HEAD_DEFINE.add("/course/findTeacherList");
		HEAD_DEFINE.add("/course/addCourse");
		HEAD_DEFINE.add("/course/toUpdateCourse");
		HEAD_DEFINE.add("/course/updateCourse");
		HEAD_DEFINE.add("/course/deleteCourse");
		HEAD_DEFINE.add("/course/deleteMultipleCourses");
		HEAD_DEFINE.add("/course/downloadTemplate");
		HEAD_DEFINE.add("/course/importData");
		HEAD_DEFINE.add("/course/exportData");
		//工具包模块
		HEAD_DEFINE.add("/utils/uploadImage");
		//分数模块
		HEAD_DEFINE.add("/score/getScoreListByPage");
		HEAD_DEFINE.add("/score/getScoreListByPageAndCondition");
		HEAD_DEFINE.add("/score/toAddScore");
		HEAD_DEFINE.add("/score/addScore");
		HEAD_DEFINE.add("/score/toUpdateScore");
		HEAD_DEFINE.add("/score/updateScore");
		HEAD_DEFINE.add("/score/findCourseList");
		HEAD_DEFINE.add("/score/findTeacherList");
		HEAD_DEFINE.add("/score/findClassesList");
		HEAD_DEFINE.add("/score/findClassesListByTeacherId");
		HEAD_DEFINE.add("/score/findStudentListByClassesId");
		HEAD_DEFINE.add("/score/exportData");
		//课表管理
		HEAD_DEFINE.add("/syllabus/getClassesSyllabusByTeacher");
		HEAD_DEFINE.add("/syllabus/getSyllabusByTeacher");
		HEAD_DEFINE.add("/syllabus/list");
		HEAD_DEFINE.add("/syllabus/searchSyllabus");
		HEAD_DEFINE.add("/syllabus/addSyllabusUIByClassesTeacher");
		HEAD_DEFINE.add("/syllabus/addSyllabus");
		HEAD_DEFINE.add("/syllabus/addSyllabusByClassesTeacher");
		HEAD_DEFINE.add("/syllabus/updateSyllabusUIByClassesTeacher");
		HEAD_DEFINE.add("/syllabus/updateSyllabus");
		HEAD_DEFINE.add("/syllabus/updateSyllabusByClassesTeacher");
		HEAD_DEFINE.add("/syllabus/deleteSyllabusByIdsByClassesTeacher");
		HEAD_DEFINE.add("/syllabus/exportSyllabus");
		HEAD_DEFINE.add("/syllabus/exportSyllabusByClassesTeacher");
		//通知模块
		HEAD_DEFINE.add("/notice/getNoticeListByPage");
		HEAD_DEFINE.add("/notice/getNoticeListByPageAndCondition");
		HEAD_DEFINE.add("/notice/toNoticeInfo");
		//统计模块
		HEAD_DEFINE.add("/logStatistics/getLogStatistics");
				
	    //======================外聘老师===============================
		
		//系统功能
		TEACHER_DEFINE.add("/function/next");
		TEACHER_DEFINE.add("/function/modifyPwd");
		//通知模块
		TEACHER_DEFINE.add("/notice/getNoticeListByPage");
		TEACHER_DEFINE.add("/notice/getNoticeListByPageAndCondition");
		TEACHER_DEFINE.add("/notice/toNoticeInfo");
		//班级管理
		TEACHER_DEFINE.add("/classes/list");
		TEACHER_DEFINE.add("/classes/searchByLoginnameByClassesTeacher");
		TEACHER_DEFINE.add("/classes/getClassesByTeacherId");
		TEACHER_DEFINE.add("/classes/lookInfo");
		TEACHER_DEFINE.add("/classes/searchClasses");
		TEACHER_DEFINE.add("/classes/exportTeacher");
		TEACHER_DEFINE.add("/classes/exportStudent");
		TEACHER_DEFINE.add("/classes/exportCheAttance");
		TEACHER_DEFINE.add("/classes/exportClassesSyllabus");
		//分数管理
		TEACHER_DEFINE.add("/score/getScoreListByPage");
		TEACHER_DEFINE.add("/score/findClassesListByTeacherId");
		TEACHER_DEFINE.add("/score/getScoreListByPageAndCondition");
		//课表管理
		TEACHER_DEFINE.add("/syllabus/list");
		TEACHER_DEFINE.add("/syllabus/searchSyllabus");
		TEACHER_DEFINE.add("/syllabus/getSyllabusByTeacher");
		TEACHER_DEFINE.add("/syllabus/exportSyllabus");
		//报表管理
		TEACHER_DEFINE.add("/logStatistics/getLogStatistics");
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object user = request.getSession().getAttribute("user");
		String uri = request.getRequestURI();
		
		Admin admin = null;
		Teacher teacher = null;
		if(user == null){
			response.sendRedirect(LOGIN_URL);
			//拦截
			return false;
		}else{
			if(user instanceof Admin){
				//1.管理员   2.领导[三位]
				admin = (Admin) user;
				Integer identify1 = admin.getIdentify();
				if(identify1 == 1){
					if(ADMIN_DEFINE.contains(uri)){
						return true;
					}else{
						response.sendRedirect(LOGIN_URL+"/auth.html");
						return false;
					}
				}
				if(identify1 == 3){
					if(LEADER_DEFINE.contains(uri)){
						return true;
					}else{
						response.sendRedirect(LOGIN_URL+"/auth.html");
						return false;
					}
				}
				
				if(identify1 == 4){
					if(LEADER_DEFINE.contains(uri)){
						return true;
					}else{
						response.sendRedirect(LOGIN_URL+"/auth.html");
						return false;
					}
				}
				
				if(identify1 == 5){
					if(LEADER_DEFINE.contains(uri)){
						return true;
					}else{
						response.sendRedirect(LOGIN_URL+"/auth.html");
						return false;
					}
				}
			}else{
			  //3.外聘老师 4.班主任
				if(user instanceof Teacher){
					teacher = (Teacher) user;
					Integer identify3 = teacher.getIdentify();
					if(identify3 == 6){
						if(HEAD_DEFINE.contains(uri)){
							return true;
						}else{
							response.sendRedirect(LOGIN_URL+"/auth.html");
							return false;
						}
					}
					if(identify3 == 2){
						if(TEACHER_DEFINE.contains(uri)){
							return true;
						}else{
							response.sendRedirect(LOGIN_URL+"/auth.html");
							return false;
						}
					}
				}
			}
		}
		return false;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
