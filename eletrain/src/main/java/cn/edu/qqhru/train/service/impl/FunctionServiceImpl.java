package cn.edu.qqhru.train.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.qqhru.train.mapper.AdminMapper;
import cn.edu.qqhru.train.mapper.FunctionMapper;
import cn.edu.qqhru.train.mapper.RoleFunctionMapper;
import cn.edu.qqhru.train.mapper.StudentMapper;
import cn.edu.qqhru.train.mapper.TeacherMapper;
import cn.edu.qqhru.train.mapper.UserRoleMapper;
import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.AdminExample;
import cn.edu.qqhru.train.pojo.RoleFunctionExample;
import cn.edu.qqhru.train.pojo.RoleFunctionKey;
import cn.edu.qqhru.train.pojo.Student;
import cn.edu.qqhru.train.pojo.StudentExample;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.pojo.TeacherExample;
import cn.edu.qqhru.train.pojo.UserRoleExample;
import cn.edu.qqhru.train.pojo.UserRoleExample.Criteria;
import cn.edu.qqhru.train.pojo.UserRoleKey;
import cn.edu.qqhru.train.service.FunctionService;
import cn.edu.qqhru.train.vo.FunctionBean;

/**
 * 登录和权限加载service层
 * <p>Title: FunctionServiceImpl</p>
 * <p>Description: </p>
 * <p>School: qiqihar university</p> 
 * @author	BQ
 * @date	2017年9月5日下午8:09:33
 * @version 1.0
 */
@Service
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private RoleFunctionMapper roleFunctionMapper;
	@Autowired
	private FunctionMapper functionMapper;
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private StudentMapper studentMapper;

	@Override
	public List<FunctionBean> getAuthFunctionByIdentify(String identify) {
		String roleId = null;
		String code = null;
		List<FunctionBean> parFunctionBeanByPage = null;
		UserRoleExample example = new UserRoleExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUserIdEqualTo(identify);
		List<UserRoleKey> selectByExample = userRoleMapper.selectByExample(example);
		if (selectByExample != null && selectByExample.size() > 0) {
			UserRoleKey userRoleKey = selectByExample.get(0);
			roleId = userRoleKey.getRoleId();
		}
		RoleFunctionExample functionExample = new RoleFunctionExample();
		cn.edu.qqhru.train.pojo.RoleFunctionExample.Criteria createCriteria2 = functionExample.createCriteria();
		if(roleId != null){
			createCriteria2.andRoleIdEqualTo(roleId);
		}
		List<RoleFunctionKey> selectByExample2 = roleFunctionMapper.selectByExample(functionExample);
		if (selectByExample2 != null && selectByExample2.size() > 0) {
			RoleFunctionKey roleFunctionKey = selectByExample2.get(0);
			code = roleFunctionKey.getFunctionId();
		}
		if(code != null){
			parFunctionBeanByPage = functionMapper.getParFunctionBeanByCode(code);
			return parFunctionBeanByPage;
		}
		return new ArrayList<FunctionBean>();
	}

	@Override
	public List<FunctionBean> getFunctionBeanById(String id) {
		List<FunctionBean> functionBeanById = functionMapper.getFunctionBeanById(id);
		return functionBeanById;
	}

	@Override
	public Object getIdentifyByUserAndPwd(String username, String password) {
		Admin admin = null;
		Teacher teacher = null;
		AdminExample adminExample = new AdminExample();
		cn.edu.qqhru.train.pojo.AdminExample.Criteria adminExampleCriteria = adminExample.createCriteria();
		if(username == null || password == null){
			adminExampleCriteria.andPasswordEqualTo("software141BQBQBQBQBQBQBQBQBBQBQBQBQBQBQBQBQBQ");
			adminExampleCriteria.andLoginnameEqualTo("software141BQBQBQBQBQBQBQBQBBQBQBQBQBQBQBQBQBQ");
		}else{
			adminExampleCriteria.andPasswordEqualTo(password);
			adminExampleCriteria.andLoginnameEqualTo(username);
		}
		List<Admin> adminSelectByExample = adminMapper.selectByExample(adminExample);
		if (adminSelectByExample != null && adminSelectByExample.size() > 0) {
			admin = adminSelectByExample.get(0);
			return admin;
		}
		TeacherExample teacherExample = new TeacherExample();
		cn.edu.qqhru.train.pojo.TeacherExample.Criteria teachercreateCriteria = teacherExample.createCriteria();
		if(username == null || password == null){
			teachercreateCriteria.andPasswordEqualTo("software141BQBQBQBQBQBQBQBQBBQBQBQBQBQBQBQBQBQ");
			teachercreateCriteria.andLoginnameEqualTo("software141BQBQBQBQBQBQBQBQBBQBQBQBQBQBQBQBQBQ");
		}else{
			teachercreateCriteria.andLoginnameEqualTo(username);
			teachercreateCriteria.andPasswordEqualTo(password);
		}
		List<Teacher> teacherSelectByExample = teacherMapper.selectByExample(teacherExample);
		if (teacherSelectByExample != null && teacherSelectByExample.size() > 0) {
			teacher = teacherSelectByExample.get(0);
			return teacher;
		}
		return null;
	}

	@Override
	public boolean modifyPwd(Object obj, String newPwd) {
		if(obj instanceof Admin){
			Admin oldAdmin = adminMapper.selectByPrimaryKey(((Admin) obj).getAdminId());
			oldAdmin.setEmail(oldAdmin.getEmail());
			oldAdmin.setIdentify(oldAdmin.getIdentify());
			oldAdmin.setLoginname(oldAdmin.getLoginname());
			oldAdmin.setPassword(newPwd);
			oldAdmin.setUsername(oldAdmin.getUsername());
			int flag = adminMapper.updateByPrimaryKey(oldAdmin);
			if(flag == 1){
				return true;
			}else{
				return false;
			}
		}
		if(obj instanceof Teacher){
			Teacher oldTeacher = teacherMapper.selectByPrimaryKey(((Teacher) obj).getTeacherId());
			oldTeacher.setTelephone(oldTeacher.getTelephone());
			oldTeacher.setAddress(oldTeacher.getAddress());
			oldTeacher.setEducation(oldTeacher.getEducation());
			oldTeacher.setIdcard(oldTeacher.getIdcard());
			oldTeacher.setEmail(oldTeacher.getEmail());
			oldTeacher.setIdentify(oldTeacher.getIdentify());
			oldTeacher.setLoginname(oldTeacher.getLoginname());
			oldTeacher.setPassword(newPwd);
			oldTeacher.setUpdatetime(oldTeacher.getUpdatetime());
			oldTeacher.setCreatetime(oldTeacher.getCreatetime());
			int flag = teacherMapper.updateByPrimaryKey(oldTeacher);
			if(flag == 1){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	

}
