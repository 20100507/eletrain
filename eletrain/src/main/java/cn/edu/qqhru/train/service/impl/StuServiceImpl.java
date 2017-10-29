package cn.edu.qqhru.train.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.qqhru.train.mapper.ClassesMapper;
import cn.edu.qqhru.train.mapper.ScoreMapper;
import cn.edu.qqhru.train.mapper.StudentMapper;
import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.ClassesExample;
import cn.edu.qqhru.train.pojo.Score;
import cn.edu.qqhru.train.pojo.ScoreExample;
import cn.edu.qqhru.train.pojo.Student;
import cn.edu.qqhru.train.pojo.StudentExample;
import cn.edu.qqhru.train.pojo.StudentExample.Criteria;
import cn.edu.qqhru.train.service.StuService;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.StudentExportVo;
@Service
public class StuServiceImpl implements StuService {
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private ClassesMapper classesMapper;
	@Autowired
	private ScoreMapper scoreMapper;
	@Override
	/**
	 * 查询所有学生信息
	 */
	public PageBean<Student> getAllStu(int page, int rows) {
		PageBean<Student> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows,"createtime desc");
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andStudentIdIsNotNull();
		List<Student> list = studentMapper.selectByExample(example);
		PageInfo<Student> pageInfo = new PageInfo<>(list);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		return pageBean;
		
	}
	/**
	 * 删除学生
	 */
	@Override
	public void delStu(int[] ids) {
		StudentExample example = new StudentExample();
		List<Integer> id = new ArrayList<>();
		for (int i : ids) {
			id.add(i);
		}
		Criteria criteria = example.createCriteria();
		criteria.andStudentIdIn(id);
		List<Student> list = studentMapper.selectByExample(example);
		for (Student student : list) {
			student.setUpdatetime(new Date());
			student.setIsfinish(1);
			studentMapper.updateByPrimaryKey(student);
		}
	}
	/**
	 * 增加学生
	 */
	@Override
	public void addStu(Student stu) {
		if (stu.getEducation()=="") {
			stu.setEducation("未填");
		}
		if (stu.getAddress()=="") {
			stu.setAddress("未填");
		}
		stu.setCreatetime(new Date());
		stu.setUpdatetime(new Date());
		stu.setIsfinish(0);
		stu.setIdentify(2);
		stu.setPassword("123456");
		if (stu.getPicpath()==null) {
			stu.setPicpath("images/default.jpg");
		}
		studentMapper.insert(stu);
	}
	/**
	 * 获得一条学生信息
	 */
	@Override
	public Student getStuOne(int id) {
		Student student = studentMapper.selectByPrimaryKey(id);
		return student;
	}
	/**
	 * 编辑学生
	 */
	@Override
	public String editStu(Student stu,boolean flag) {
		Student stuOld = studentMapper.selectByPrimaryKey(stu.getStudentId());
		if (stu.getEducation().equals("a")) {
			stu.setEducation("未填");
		}
		stu.setLoginname(stuOld.getLoginname());
		stu.setPassword(stuOld.getPassword());
		stu.setCreatetime(stuOld.getCreatetime());
		stu.setUpdatetime(new Date());
		stu.setIsfinish(stuOld.getIsfinish());
		stu.setIdentify(stuOld.getIdentify());
		stu.setClassesId(stuOld.getClassesId());
		if (flag == false) {
			stu.setPicpath(stuOld.getPicpath());
		}
		studentMapper.updateByPrimaryKey(stu);
		return stuOld.getPicpath();
	}
	/**
	 * 获取学生
	 */
	@Override
	public PageBean<Student> getStu(String Idcard,int page, int rows) {
		PageBean<Student> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows,"createtime desc");
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdcardLike("%" + Idcard + "%");
		List<Student> list = studentMapper.selectByExample(example);
		PageInfo<Student> pageInfo = new PageInfo<>(list);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		return pageBean;
	}
	/**
	 * 初始化密码
	 */
	@Override
	public void initPassword(int id) {
		Student stu = studentMapper.selectByPrimaryKey(id);
		stu.setPassword("123456");
		studentMapper.updateByPrimaryKey(stu);
	}
	/**
	 * 导入学生
	 */
	@Override
	public void importStu(List<Student> stuList) {
		for (Student student : stuList) {
			student.setIsfinish(0);
			student.setIdentify(2);
			student.setPassword("123456");
			student.setCreatetime(new Date());
			student.setUpdatetime(new Date());
			student.setPicpath("images/default.jpg");
			studentMapper.insert(student);
		}
	}
	/**
	 * 导出学生
	 */
	@Override
	public List<StudentExportVo> getExportStu(Integer classesId,Integer isFinish) {
		List<StudentExportVo> listVo = new ArrayList<>();
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andClassesIdEqualTo(classesId).andIsfinishEqualTo(isFinish);
		List<Student> list = studentMapper.selectByExample(example);
		if (list.size() == 0) {
			return null;
		}else {
			
		
		for (Student student : list) {
			StudentExportVo stu = new StudentExportVo();
			stu.setLoginname(student.getLoginname());
			stu.setUsername(student.getUsername());
			stu.setEmail(student.getEmail());
			stu.setTelephone(student.getTelephone());
			stu.setEducation(student.getEducation());
			stu.setIdcard(student.getIdcard());
			stu.setAddress(student.getAddress());
			Classes classes = classesMapper.selectByPrimaryKey(student.getClassesId());
			stu.setClassName(classes.getCname());
			if (isFinish==1) {
				ScoreExample example2 = new ScoreExample();
				example2.createCriteria().andClassesIdEqualTo(classesId).andStudentIdEqualTo(student.getStudentId());
				List<Score> listScore = scoreMapper.selectByExample(example2);
				if(listScore != null && listScore.size() > 0){
					if (listScore.get(0).getTheoryscore()!=null) {
						stu.setTheoryscore(listScore.get(0).getTheoryscore());
					}
					if (listScore.get(0).getPracticescore()!=null) {
						stu.setPracticescore(listScore.get(0).getPracticescore());
					}
					if (listScore.get(0).getTotal()!=null) {
						stu.setTotal(listScore.get(0).getTotal());
					}
					if (listScore.get(0).getCertificateno()!=null) {
						stu.setCertificateNo(listScore.get(0).getCertificateno());
					}
				}
			}
			listVo.add(stu);
		}
	}
		return listVo;
	}
	@Override
	public List<Student> getStuByClassId(Integer classesId) {
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andClassesIdEqualTo(classesId);
		List<Student> list = studentMapper.selectByExample(example);
		return list;
	}
	@Override
	public List<Student> getStuOneByUsername(String username) {
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<Student> studentList = studentMapper.selectByExample(example);
		if(studentList.size() > 0){
			return studentList;
		}
		return null;
	}
	@Override
	public Student getStuOneByIdcard(String idcard) {
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdcardEqualTo(idcard);
		List<Student> studentList = studentMapper.selectByExample(example);
		if(studentList.size() == 1){
			return studentList.get(0);
		}
		return null;
	}
	@Override
	public List<Student> getStuByIdcard(String idcard) {
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdcardEqualTo(idcard);
		List<Student> list = studentMapper.selectByExample(example);
		return list;
	}
	@Override
	public PageBean<Student> getAllFinishStu(int page, int rows) {
		PageBean<Student> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows,"createtime desc");
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andStudentIdIsNotNull().andIsfinishEqualTo(1);
		List<Student> list = studentMapper.selectByExample(example);
		PageInfo<Student> pageInfo = new PageInfo<>(list);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		return pageBean;
	}
	@Override
	public PageBean<Student> getAllNotFinishStu(int page, int rows) {
		PageBean<Student> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows,"createtime desc");
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andStudentIdIsNotNull().andIsfinishEqualTo(0);
		List<Student> list = studentMapper.selectByExample(example);
		PageInfo<Student> pageInfo = new PageInfo<>(list);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		return pageBean;
	}
	/**
	 * 校验学号
	 */
	@Override
	public boolean checkNo(String loginname) {
		StudentExample example = new StudentExample();
		Criteria criteria = example.createCriteria();
		criteria.andLoginnameEqualTo(loginname);
		List<Student> list = studentMapper.selectByExample(example);
		if (list.size()>0) {
			return true;
		}
		return false;
	}
	@Override
	public List<Classes> getAllNoFinishClass() {
		ClassesExample example = new ClassesExample();
		example.createCriteria().andCapacityEqualTo(0);
		List<Classes> list = classesMapper.selectByExample(example);
		return list;
	}
	@Override
	public List<Classes> getAllFinishClass() {
		ClassesExample example = new ClassesExample();
		example.createCriteria().andCapacityEqualTo(1);
		List<Classes> list = classesMapper.selectByExample(example);
		return list;
	}

}
