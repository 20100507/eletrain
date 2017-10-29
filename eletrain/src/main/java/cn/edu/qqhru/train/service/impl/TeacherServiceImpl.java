package cn.edu.qqhru.train.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.qqhru.train.mapper.AdminMapper;
import cn.edu.qqhru.train.mapper.ClassesMapper;
import cn.edu.qqhru.train.mapper.ClassesTeacherMapper;
import cn.edu.qqhru.train.mapper.PlanTeacherMapper;
import cn.edu.qqhru.train.mapper.TeacherMapper;
import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.AdminExample;
import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.ClassesExample;
import cn.edu.qqhru.train.pojo.ClassesTeacherExample;
import cn.edu.qqhru.train.pojo.ClassesTeacherKey;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.pojo.TeacherExample;
import cn.edu.qqhru.train.pojo.TeacherExample.Criteria;
import cn.edu.qqhru.train.service.TeacherService;
import cn.edu.qqhru.train.utils.PageBean;

@Service
@Qualifier("TeacherService")
public class TeacherServiceImpl implements TeacherService {
	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private ClassesTeacherMapper classesTeacherMapper;
	/**
	 * 查询所有的教师
	 */
	@Override
	public List<Teacher> getAllTeacher(){
		TeacherExample example = new TeacherExample();
		Criteria criteria = example.createCriteria();
		criteria.andTeacherIdIsNotNull();
		List<Teacher> teacherList = teacherMapper.selectByExample(example);
		if(teacherList!=null&&teacherList.size()>0){
			return teacherList;
		}
		else{
			return Collections.emptyList();
		}
	}
	/**
	 * 查询所有的在职/离职教师
	 */
	@Override
	public List<Teacher> getAllWorkTeacher(String type){
		TeacherExample example = new TeacherExample();
		Criteria criteria = example.createCriteria();
		criteria.andTeacherIdIsNotNull();
		if(type.contentEquals("z")){
			criteria.andIsfinishEqualTo(0);
		}
		else{
			criteria.andIsfinishEqualTo(1);
		}
		List<Teacher> teacherList = teacherMapper.selectByExample(example);
		if(teacherList!=null&&teacherList.size()>0){
			return teacherList;
		}
		else{
			return Collections.emptyList();
		}
	}
	/**
	 * 添加教师
	 */
	@Override
	public void addTeacher(Teacher teacher) {
		String index1 = String.valueOf(new Date().getTime()).substring(8);
		String index2 = teacher.getIdcard().substring(10);
		teacher.setLoginname(index1+index2);
		teacher.setPassword("123456");
		teacher.setCreatetime(new Date());
		teacher.setUpdatetime(null);
		teacherMapper.insert(teacher);
	}
	/**
	 * 教师离职，通过id
	 */
	@Override
	public void deleteTeacher(Integer teacherId) {
		//教师离职
		Teacher teacher = teacherMapper.selectByPrimaryKey(teacherId);
		if(teacher!=null){
			teacher.setIsfinish(1);
			teacherMapper.updateByPrimaryKey(teacher);
		}
	}

	/**
	 * 通过id查询教师
	 */
	@Override
	public Teacher getTeacherBYid(Integer teacherId) {
		Teacher teacher = teacherMapper.selectByPrimaryKey(teacherId);
		if(teacher!=null){
			return teacher;
		}
		else{
			return new Teacher();
		}
	}
	/**
	 * 通过id更新Teacher
	 */
	@Override
	public void updateTeacher(Teacher teacher) {
    	Teacher exitTeacher = teacherMapper.selectByPrimaryKey(teacher.getTeacherId());
    	if(exitTeacher!=null){
    		teacher.setLoginname(exitTeacher.getLoginname());
    		teacher.setIsfinish(exitTeacher.getIsfinish());
    		teacher.setUpdatetime(new Date());
    		teacher.setPassword(exitTeacher.getPassword());
    		teacher.setCreatetime(exitTeacher.getCreatetime());
    		teacherMapper.updateByPrimaryKey(teacher);
    	}
	}
	/**
	 * 分页查询所有的Teacher
	 */
	@Override
	public PageBean<Teacher> getAllTeacher(int page, int rows,int type) {
		PageBean<Teacher> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		TeacherExample example = new TeacherExample();
		example.setOrderByClause("createtime desc");
		Criteria criteria = example.createCriteria();
		if(type==0){
			criteria.andTeacherIdIsNotNull();
			criteria.andIsfinishEqualTo(0);
		}else{
			criteria.andTeacherIdIsNotNull();
			criteria.andIsfinishEqualTo(1);
		}
		List<Teacher> list = teacherMapper.selectByExample(example);
		PageInfo<Teacher> pageInfo = new PageInfo<>(list);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		return pageBean;
	}
	/**
	 * 教师批量离职
	 */
	@Override
	public void delTeacherByIds(Integer[] ids) {
		TeacherExample example = new TeacherExample();
		Criteria criteria = example.createCriteria();
		//将数组转换成list集合
		ArrayList<Integer> idList =new ArrayList<Integer>(Arrays.asList(ids));
		criteria.andTeacherIdIn(idList);
		List<Teacher> teacherList = teacherMapper.selectByExample(example);
		if(teacherList!=null&&teacherList.size()>0){
			for (Teacher teacher : teacherList) {
				teacher.setIsfinish(1);
				teacherMapper.updateByPrimaryKey(teacher);
			}
		}
		//teacherMapper.deleteByExample(example);
		/*//批量删除和Plan的关联关系
		PlanTeacherExample planTeacherExample = new PlanTeacherExample();
		cn.edu.qqhru.train.pojo.PlanTeacherExample.Criteria ptCriteria = planTeacherExample.createCriteria();
		ptCriteria.andTeacherIdIn(idList);
		planTeacherMapper.deleteByExample(planTeacherExample);*/
	}
	/**
	 * 初始化密码
	 */
	@Override
	public void init(Integer teacherId) {
		Teacher teacher = teacherMapper.selectByPrimaryKey(teacherId);
		if(teacher!=null){
			teacher.setPassword("123456");
			teacherMapper.updateByPrimaryKey(teacher);
		}
	}
	/**
	 * 通过索引查询Teacher
	 */
	@Override
	public Teacher lookInfo(Integer index) {
		Teacher teacher = teacherMapper.selectByPrimaryKey(index);
		if(teacher!=null){
			return teacher;
		}else{
			return new Teacher();
		}
	}
	/**
	 * 批量导入在职Teacher
	 */
	@Override
	public void importWorkTeacher(List<Teacher> teacherList) {
		if(teacherList!=null&&teacherList.size()>0){
			for(Teacher teacher:teacherList){
				String index1 = String.valueOf(new Date().getTime()).substring(8);
				String index2 = teacher.getIdcard().substring(10);
				teacher.setLoginname(index1+index2);
				teacher.setIsfinish(0);
				teacher.setCreatetime(new Date());
				teacher.setPassword("123456");
				teacherMapper.insert(teacher);
			}
		}
	}
	/**
	 * 批量导入离职Teacher
	 */
	@Override
	public void importreSignTeacher(List<Teacher> teacherList) {
		if(teacherList!=null&&teacherList.size()!=0){
			for(Teacher teacher:teacherList){
				teacher.setIsfinish(1);
				teacher.setIdentify(2);
				teacherMapper.insert(teacher);
			}
		}
	}
	/**
	 * 通过名字获得教师
	 */
	@Override
	public Teacher getOneTeacherByCname(String username){
		TeacherExample example = new TeacherExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<Teacher> teacherList = teacherMapper.selectByExample(example);
		if(teacherList!=null&teacherList.size()>0){
			return teacherList.get(0);
		}else{
			return new Teacher();
		}
	}
	/**
	 * 通过登录名查询教师
	 */
	@Override
	@Deprecated
	public Teacher searchTeracher(String index) {
		TeacherExample example = new TeacherExample();
		Criteria criteria = example.createCriteria();
		criteria.andLoginnameEqualTo(index);
		List<Teacher> teacherList = teacherMapper.selectByExample(example);
		if(teacherList!=null&&teacherList.size()>0){
			Teacher teacher = teacherList.get(0);
			return teacher;
		}else{
			return new Teacher();
		}
	}
	/**
	 * 通过classesId查询教师
	 */
	@Override
	public List<Teacher> getTeacherByClassesId(Integer classesId) {
		ClassesTeacherExample example = new ClassesTeacherExample();
		cn.edu.qqhru.train.pojo.ClassesTeacherExample.Criteria criteria = example.createCriteria();
		criteria.andClassesIdEqualTo(classesId);
		List<ClassesTeacherKey> classesTeacherList = classesTeacherMapper.selectByExample(example);
		
		ArrayList<Integer> arrayList = new ArrayList<>();
		if(classesTeacherList!=null&&classesTeacherList.size()>0){
			for(ClassesTeacherKey ct:classesTeacherList){
				arrayList.add(ct.getTeacherId());
			}
		}
		
		List<Teacher> teacherList = new ArrayList<>();
		if(arrayList!=null&arrayList.size()>0){
			TeacherExample teacherExample = new TeacherExample();
			Criteria teacherCriteria = teacherExample.createCriteria();
			teacherCriteria.andTeacherIdIn(arrayList);
			teacherList = teacherMapper.selectByExample(teacherExample);
		}
		
		if(teacherList!=null&&teacherList.size()>0){
			return teacherList;
		}else{
			return Collections.emptyList();
		}
	}
	/**
	 * 通过身份证号和状态查询Teacher
	 */
	@Override
	public List<Teacher> getTeacherByCardIdAndState(String index, Integer state) {
		TeacherExample teacherExample = new TeacherExample();
		Criteria tCriteria = teacherExample.createCriteria();
		tCriteria.andIdcardEqualTo(index);
		tCriteria.andIsfinishEqualTo(state);
		List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
		if(teacherList!=null&&teacherList.size()>0){
			return teacherList;
		}else{
			return Collections.emptyList();
		}
	}
	/**
	 * 通过姓名和状态查询Teacher
	 */
	@Override
	public List<Teacher> getTeacherByUsernameAndState(String index, Integer state) {
		TeacherExample teacherExample = new TeacherExample();
		Criteria tCriteria = teacherExample.createCriteria();
		tCriteria.andUsernameEqualTo(index);
		tCriteria.andIsfinishEqualTo(state);
		List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
		if(teacherList!=null&&teacherList.size()>0){
			return teacherList;
		}else{
			return Collections.emptyList();
		}
	}
	/**
	 * 查询所有的在职教师
	 */
	@Override
	public PageBean<Teacher> getAllWorkTeacher(int page, int rows) {
		PageBean<Teacher> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		TeacherExample example = new TeacherExample();
		Criteria criteria = example.createCriteria();
		criteria.andTeacherIdIsNotNull();
		criteria.andIsfinishEqualTo(0);
		List<Teacher> list = teacherMapper.selectByExample(example);
		PageInfo<Teacher> pageInfo = new PageInfo<>(list);
		//封装分页信息
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		//返回数据
		return pageBean;
	}
	/**
	 * 通过登录名获得教师
	 */
	@Override
	public PageBean<Teacher> getTeacherByLoginname(String index, int page, int rows) {
		PageBean<Teacher> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		
		TeacherExample teacherExample = new TeacherExample();
		Criteria tCriteria = teacherExample.createCriteria();
		tCriteria.andLoginnameEqualTo(index);
		List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
		
		PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(teacherList);
		return pageBean;
		
	}
	/**
	 * 通过用户名获得教师
	 */
	@Override
	public PageBean<Teacher> getTeacherByUsername(String index, int page, int rows) {
		PageBean<Teacher> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		
		TeacherExample teacherExample = new TeacherExample();
		Criteria tCriteria = teacherExample.createCriteria();
		tCriteria.andUsernameEqualTo(index);
		List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
		
		PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(teacherList);
		return pageBean;
	}
	/**
	 * 通过身份证获得教师
	 */
	@Override
	public PageBean<Teacher> getTeacherByCardId(String index, int page, int rows) {
		PageBean<Teacher> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		
		TeacherExample teacherExample = new TeacherExample();
		Criteria tCriteria = teacherExample.createCriteria();
		tCriteria.andIdcardEqualTo(index);
		List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
		
		PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(teacherList);
		return pageBean;
	}
	/**
	 * 通过教师id数组更新教师
	 */
	@Override
	public void updTeacherByIds(Integer[] teacherIds) {
		TeacherExample example = new TeacherExample();
		Criteria criteria = example.createCriteria();
		//将数组转换成list集合
		ArrayList<Integer> idList =new ArrayList<Integer>(Arrays.asList(teacherIds));
		criteria.andTeacherIdIn(idList);
		List<Teacher> teacherList = teacherMapper.selectByExample(example);
		if(teacherList!=null&&teacherList.size()>0){
			for (Teacher teacher : teacherList) {
				teacher.setIsfinish(0);
				teacherMapper.updateByPrimaryKey(teacher);
			}
		}
	}
	/**
	 * 获得所有的班主任
	 */
	@Override
	public List<Teacher> getClassesTeacher() {
		TeacherExample teacherExample = new TeacherExample();
		Criteria tCriteria = teacherExample.createCriteria();
		tCriteria.andIdentifyEqualTo(6);
		tCriteria.andIsfinishEqualTo(0);
		List<Teacher> classesTeacherList = teacherMapper.selectByExample(teacherExample);
		if(classesTeacherList!=null&&classesTeacherList.size()>0){
			return classesTeacherList;
		}else{
			return Collections.emptyList();
		}
	}
	/**
	 * 通过教师id数组获得教师集合
	 */
	@Override
	public List<Teacher> getTeacherByteacherIds(List<Integer> teacherIds) {
		TeacherExample teacherExample = new TeacherExample();
		Criteria teacherCriteria = teacherExample.createCriteria();
		if(teacherIds != null && teacherIds.size() > 0){
			teacherCriteria.andTeacherIdIn(teacherIds);
			List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
			return teacherList;
		}else{
			return Collections.emptyList();
		}
	}
	@Override
	public List<Teacher> getTeacherByUsername(String index) {
		TeacherExample example = new TeacherExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(index);
		List<Teacher> teacherList = teacherMapper.selectByExample(example);
		if(teacherList!=null&teacherList.size()>0){
			return teacherList;
		}else{
			return Collections.emptyList();
		}
	}
	/**
	 * 判断导入数据的身份证号是否重复
	 */
	@Override
	public boolean checkLoginName(String replace) {
		TeacherExample teacherExample = new TeacherExample();
		Criteria teacherCriteria = teacherExample.createCriteria();
		teacherCriteria.andIdcardEqualTo(replace);
		List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
		
		if(teacherList!=null&&teacherList.size()>0){
			return true;
		}
		else{
			return false;
		}
	}
	
}
