package cn.edu.qqhru.train.service;

import java.util.List;

import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.Student;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.StudentExportVo;

public interface StuService {
	// 获取所有学生信息,分页
	PageBean<Student> getAllStu(int page, int rows);
	// 获取所有已毕业学生信息,分页
	PageBean<Student> getAllFinishStu(int page, int rows);
	// 获取所有已毕业学生信息,分页
	PageBean<Student> getAllNotFinishStu(int page, int rows);
	// 删除学生信息
	void delStu(int [] ids);
	// 添加一条学生信息
	void addStu(Student stu);
	// 根据id获取学生信息
	Student getStuOne(int id);
	// 修改学生信息
	String editStu(Student stu,boolean flag);
	// 查询指定学生
	PageBean<Student> getStu(String loginname,int page,int rows);
	// 初始化密码
	void initPassword(int id);
	// 批量导入学生信息
	void importStu(List<Student> stuList);
	// 获取所有学生信息，导出
	List<StudentExportVo> getExportStu(Integer classesId,Integer isFinish);
	// 根据classesId，查询学生列表
	List<Student> getStuByClassId(Integer classesId);
	// 根据username获取学生信息
	List<Student> getStuOneByUsername(String username);
	//根据身份证号获取一个学生信息
	Student getStuOneByIdcard(String idcard);
	// 根据身份证号取多个学生信息
	List<Student> getStuByIdcard(String idcard);
	// 学号校验
	boolean checkNo(String loginname);
	// 查询所有未结束班级
	List<Classes> getAllNoFinishClass();
	// 查询所有已结束班级
	List<Classes> getAllFinishClass();
}
