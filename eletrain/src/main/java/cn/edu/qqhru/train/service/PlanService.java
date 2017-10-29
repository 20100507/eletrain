package cn.edu.qqhru.train.service;

import java.util.List;

import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.Plan;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.PlanVo;

public interface PlanService {
	// 获取所有方案信息,分页
	PageBean<PlanVo> getAllPlan(int page, int rows);
	// 根据id获取方案信息
	PlanVo getPlanOne(int id);
	// 删除方案信息
	void delPlan(int [] ids);
	// 添加方案
	void addPlan(Plan plan,Integer[] classIds,Integer[] deviceIds,Integer[] bookIds,Integer[] teaIds);
	// 编辑方案
	void editPlan(Plan plan,Integer[] classIds,Integer[] deviceIds,Integer[] bookIds,Integer[] teaIds);
	// 根据方案名称查询方案信息，分页
	// 查询指定设备
	PageBean<PlanVo> getPlan(String pname,int page,int rows);
	
	//提交申请
	boolean submitApp(Admin admin, int id);
	
	//==================================根据标识符那人的名字
	String getUsernameByIdentify(int identify);
	// 获取指定管理员
	List<Admin> getAdminList();
	// 获取指定班级
	List<Classes> getClassesList();
	// 获取指定教师
	List<Teacher> getTeacherList();
	// 根据多个id获取培训方案
	List<Plan> getPlanByIds(int[] ids);
	// 校验方案名称
	boolean checkPname(String pname);
}
