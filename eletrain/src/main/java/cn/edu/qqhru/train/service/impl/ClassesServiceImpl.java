package cn.edu.qqhru.train.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.qqhru.train.mapper.ClassesMapper;
import cn.edu.qqhru.train.mapper.ClassesPlanMapper;
import cn.edu.qqhru.train.mapper.ClassesTeacherMapper;
import cn.edu.qqhru.train.mapper.CourseMapper;
import cn.edu.qqhru.train.mapper.StudentMapper;
import cn.edu.qqhru.train.mapper.SyllabusMapper;
import cn.edu.qqhru.train.mapper.TeacherMapper;
import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.ClassesExample;
import cn.edu.qqhru.train.pojo.ClassesPlan;
import cn.edu.qqhru.train.pojo.ClassesPlanExample;
import cn.edu.qqhru.train.pojo.ClassesTeacherExample;
import cn.edu.qqhru.train.pojo.ClassesTeacherExample.Criteria;
import cn.edu.qqhru.train.pojo.ClassesTeacherKey;
import cn.edu.qqhru.train.pojo.Course;
import cn.edu.qqhru.train.pojo.CourseExample;
import cn.edu.qqhru.train.pojo.Student;
import cn.edu.qqhru.train.pojo.StudentExample;
import cn.edu.qqhru.train.pojo.Syllabus;
import cn.edu.qqhru.train.pojo.SyllabusExample;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.pojo.TeacherExample;
import cn.edu.qqhru.train.service.ClassesService;
import cn.edu.qqhru.train.service.TeacherService;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.CourseDateVo;

@Service
@Qualifier("TeacherService")
public class ClassesServiceImpl implements ClassesService {
	@Autowired
	private ClassesMapper classesMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private ClassesTeacherMapper classTeacherMapper;
	@Autowired
	private ClassesPlanMapper classPlanMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private SyllabusMapper syllabusMapper;
	/**
	 * 添加班级
	 */
	@Override
	public void addClasses(Classes classes,Integer[] teacherIds, String startTime, String endTime) {
		//插入班级
		classes.setCapacity(0);//设置班级状态：0表示正常，1表示已结课
		String startAndEndTime=startTime+"~"+endTime;
		classes.setStarttime(startAndEndTime);//设置班级授课时间段
		classesMapper.insert(classes);
		Integer classesId = classes.getClassesId();
		ClassesTeacherKey classesTeacher = new ClassesTeacherKey();
		if(teacherIds.length>0){
			for(int i=0;i<teacherIds.length;i++){
				classesTeacher.setClassesId(classesId);
				classesTeacher.setTeacherId(teacherIds[i]);
				classTeacherMapper.insert(classesTeacher);
			}
		}
	}
	/**
	 * 通过id删除班级
	 */
	@Override
	public void deleteClasses(Integer classesId) {
		//修改班级状态
		Classes exitClasses = classesMapper.selectByPrimaryKey(classesId);
		if(exitClasses!=null){
			exitClasses.setCapacity(1);
			classesMapper.updateByPrimaryKey(exitClasses);
		}
		//标注班级已经结课
		ClassesPlanExample classesPlanExample = new ClassesPlanExample();
		cn.edu.qqhru.train.pojo.ClassesPlanExample.Criteria cpCriteria = classesPlanExample.createCriteria();
		cpCriteria.andClassesIdEqualTo(classesId);
		List<ClassesPlan> cpList = classPlanMapper.selectByExample(classesPlanExample);
		if(cpList!=null&&cpList.size()>0){
			for (ClassesPlan classesPlan : cpList) {
				classesPlan.setCname(classesPlan.getCname()+"(已结课)");
				classPlanMapper.updateByPrimaryKey(classesPlan);
			}
		}
	}
	/**
	 * 通过id查询班级
	 */
	@Override
	public Classes getClassesBYid(Integer classesId) {
		Classes classes = classesMapper.selectByPrimaryKey(classesId);
		if(classes!=null){
			return classes;
		}
		else{
			return new Classes();
		}
	}
	/**
	 * 通过id更新班级
	 */
	@Override
	public void updateClasses(Classes classes,Integer[] teacherIds, String startTime, String endTime) {
		//更新班级信息
		Integer classesId = classes.getClassesId();
		Classes exitclasses = classesMapper.selectByPrimaryKey(classesId);
		String startAndEndTime=startTime+"~"+endTime;
		classes.setStarttime(startAndEndTime);//设置班级授课时间段
    	classes.setCapacity(exitclasses.getCapacity());
    	classesMapper.updateByPrimaryKey(classes);
    	//删除旧的关联信息
    	ClassesTeacherExample example = new ClassesTeacherExample();
		Criteria criteria = example.createCriteria();
		criteria.andClassesIdEqualTo(classesId);
		classTeacherMapper.deleteByExample(example);
    	//增加新的关联关系
		ClassesTeacherKey classesTeacher = new ClassesTeacherKey();
		if(teacherIds.length>0){
			for(int i=0;i<teacherIds.length;i++){
				classesTeacher.setClassesId(classesId);
				classesTeacher.setTeacherId(teacherIds[i]);
				classTeacherMapper.insert(classesTeacher);
			}
		}
	}
	/**
	 * 分页查询所有的Classes
	 */
	@Override
	public PageBean<Classes> getAllClasses(int page, int rows,int type) {
		PageBean<Classes> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		ClassesExample example = new ClassesExample();
		example.setOrderByClause("createtime desc");
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria criteria = example.createCriteria();
		criteria.andClassesIdIsNotNull();
		if(type==0){
			criteria.andCapacityEqualTo(0);
			criteria.andClassesIdIsNotNull();
		}else{
			criteria.andClassesIdIsNotNull();
			criteria.andCapacityEqualTo(1);
		}
		List<Classes> list = classesMapper.selectByExample(example);
		PageInfo<Classes> pageInfo = new PageInfo<>(list);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		return pageBean;
	}
	/**
	 * 批量删除班级
	 */
	@Override
	public void delClassesByIds(Integer[] ids) {
		//修改班级状态
		ClassesExample example = new ClassesExample();
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria criteria = example.createCriteria();
		//将数组转换成list集合
		ArrayList<Integer> idList =new ArrayList<Integer>(Arrays.asList(ids));
		criteria.andClassesIdIn(idList);
		List<Classes> classesList = classesMapper.selectByExample(example);
		if(classesList!=null&&classesList.size()>0){
			for (Classes classes : classesList) {
				classes.setCapacity(1);
				classesMapper.updateByPrimaryKey(classes);
			}
		}
		//标注班级已结课
		ClassesPlanExample classesPlanExample = new ClassesPlanExample();
		cn.edu.qqhru.train.pojo.ClassesPlanExample.Criteria cpCriteria = classesPlanExample.createCriteria();
		cpCriteria.andClassesIdIn(idList);
		List<ClassesPlan> cpList = classPlanMapper.selectByExample(classesPlanExample);
		if(cpList!=null&&cpList.size()>0){
			for (ClassesPlan classesPlan : cpList) {
				classesPlan.setCname(classesPlan.getCname()+"(已结课)");
				classPlanMapper.updateByPrimaryKey(classesPlan);
			}
		}
	}
	/**
	 * 通过classesId查询教师
	 */
	@Override
	public List<Teacher> getTeacherByCid(Integer classesId) {
		//通过classesId查询出中间表
		ClassesTeacherExample classesTeacherExample = new ClassesTeacherExample();
		Criteria ctCriteria = classesTeacherExample.createCriteria();
		ctCriteria.andClassesIdEqualTo(classesId);
		List<ClassesTeacherKey> ctList = classTeacherMapper.selectByExample(classesTeacherExample);
		//遍历出班级对应的教师id
		ArrayList<Integer> idList = new ArrayList<>();
		if(ctList!=null&&ctList.size()>0){
			for(ClassesTeacherKey ctk :ctList){
				idList.add(ctk.getTeacherId());
			}
		}
		//通过教师id查询教师
		TeacherExample teacherExample = new TeacherExample();
		cn.edu.qqhru.train.pojo.TeacherExample.Criteria tCriteria = teacherExample.createCriteria();
		tCriteria.andTeacherIdIn(idList);
		List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
		if(teacherList!=null&teacherList.size()>0){
			return teacherList;
		}
		else{
			return Collections.emptyList();
		}
	}
	/**
	 * 通过classesId查询Student
	 * @return
	 */
	public List<Student> getStudentByClassesId(Integer classesId){
		StudentExample example = new StudentExample();
		cn.edu.qqhru.train.pojo.StudentExample.Criteria criteria = example.createCriteria();
		criteria.andClassesIdEqualTo(classesId);
		List<Student> exitStuList = studentMapper.selectByExample(example);
		if(exitStuList!=null&&exitStuList.size()>0){
			return exitStuList;
		}
		else{
			return Collections.emptyList();
		}
	}
	/**
	 * 通过studentId数组查询Student
	 * @param ids
	 * @return
	 */
	public List<Student> getStudentByIds(Integer[] ids){
		StudentExample example = new StudentExample();
		cn.edu.qqhru.train.pojo.StudentExample.Criteria criteria = example.createCriteria();
		ArrayList<Integer> list = new ArrayList<>(Arrays.asList(ids));
		criteria.andStudentIdIn(list);
		List<Student> stuList = studentMapper.selectByExample(example);
		if(stuList!=null&&stuList.size()>0){
			return stuList;
		}
		else{
			return Collections.emptyList();
		}
	}
	/**
	 * 给班级添加/删除学生
	 */
	@Override
	public void addOrDelStudentToClasses(Integer classesId, Integer[] ids,Integer type) {
		/*//==========清空班级原有的学生==========
		//查询classes_id等于classesId的学生
		List<Student> exitStuList = getStudentByClassesId(classesId);
		//设置classesId为null,更新到数据库
		for(Student stu :exitStuList){
			stu.setClassesId(null);
			studentMapper.updateByPrimaryKey(stu);
		}*/
		//==========添加学生==========
		if(type==0){
			List<Student> stuList = getStudentByIds(ids);
			if(stuList!=null&&stuList.size()>0){
				for(Student stu:stuList){
					stu.setClassesId(classesId);
					studentMapper.updateByPrimaryKey(stu);
				}
			}
		}
		//==========删除学生==========
		else{
			List<Student> stuList = getStudentByIds(ids);
			if(stuList!=null&&stuList.size()>0){
				for(Student stu:stuList){
					stu.setClassesId(null);
					studentMapper.updateByPrimaryKey(stu);
				}
			}
		}
	}
	/**
	 * 查询所有的班级
	 */
	@Override
	public List<Classes> getAllClasses() {
		ClassesExample example = new ClassesExample();
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria criteria = example.createCriteria();
		criteria.andClassesIdIsNotNull();
		List<Classes> classesList = classesMapper.selectByExample(example);
		if(classesList!=null&&classesList.size()>0){
			return classesList;
		}else{
			return Collections.emptyList();
		}
	}
	/**
	 * 通过classesId查询学生
	 */
	@Override
	public List<Student> getStudentByCid(Integer classesId) {
		StudentExample studentExample = new StudentExample();
		cn.edu.qqhru.train.pojo.StudentExample.Criteria stuCriteria = studentExample.createCriteria();
		stuCriteria.andClassesIdEqualTo(classesId);
		List<Student> stuList = studentMapper.selectByExample(studentExample);
		if(stuList!=null&&stuList.size()>0){
			return stuList;
		}
		else{
			return Collections.emptyList();
		}
	}
	/**
	 * 通过班级id查询学生人数
	 */
	@Override
	public Integer getCountByCid(Integer classesId) {
		StudentExample studentExample = new StudentExample();
		cn.edu.qqhru.train.pojo.StudentExample.Criteria stuCriteria = studentExample.createCriteria();
		stuCriteria.andClassesIdEqualTo(classesId);
		int count = studentMapper.countByExample(studentExample);
		return count;
	}
	
	/*8
	 * (non-Javadoc)
	 * @see cn.edu.qqhru.train.service.ClassesService#getCourseByCourseId(java.lang.Integer)
	 */
	@Override
	public Course getCourseByCourseId(Integer courseId) {
		Course course = courseMapper.selectByPrimaryKey(courseId);
		if(course!=null){
			return course;
		}
		else{
			return new Course();
		}
	}
	/**
	 * 通过班级id查询课表
	 */
	@Override
	public List<Syllabus> getSyllabusByClassesId(Integer classesId) {
		SyllabusExample syllabusExample = new SyllabusExample();
		syllabusExample.setOrderByClause("week ASC");
		cn.edu.qqhru.train.pojo.SyllabusExample.Criteria syllCriteria = syllabusExample.createCriteria();
		syllCriteria.andClassIdEqualTo(classesId);
		List<Syllabus> syllList = syllabusMapper.selectByExample(syllabusExample);
		if(syllList!=null&&syllList.size()>0){
			return syllList;
		}
		else{
			return Collections.emptyList();
		}
	}
	/**
	 * 通过班级id和状态查询Classes
	 */
	@Override
	public Classes getClassesByClassesIdAndZhangTai(Integer classesId,Integer zhuangtai) {
		ClassesExample classesExample = new ClassesExample();
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria cCriteria = classesExample.createCriteria();
		cCriteria.andCapacityEqualTo(zhuangtai);
		cCriteria.andClassesIdEqualTo(classesId);
		List<Classes> classesList = classesMapper.selectByExample(classesExample);
		if(classesList!=null&&classesList.size()>0){
			return classesList.get(0);
		}
		else{
			return new Classes();
		}
	}
	/**
	 * 通过班级编号和状态查询Classes
	 */
	@Override
	public PageBean<Classes> getClassesByClassesNoAndZhangTai(int page,int rows,String index,Integer zhuangtai) {
		PageBean<Classes> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		ClassesExample classesExample = new ClassesExample();
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria cCriteria = classesExample.createCriteria();
		cCriteria.andCapacityEqualTo(zhuangtai);
		cCriteria.andClassesNoEqualTo(index);
		List<Classes> classesList = classesMapper.selectByExample(classesExample);
		PageInfo<Classes> pageInfo = new PageInfo<>(classesList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(classesList);
		/*ClassesExample classesExample = new ClassesExample();
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria cCriteria = classesExample.createCriteria();
		cCriteria.andCapacityEqualTo(zhuangtai);
		cCriteria.andClassesNoEqualTo(index);
		List<Classes> classesList = classesMapper.selectByExample(classesExample);*/
		return pageBean;
	}
	/**
	 * 通过班级名称和状态查询Classes
	 */
	@Override
	public PageBean<Classes> getClassesByClassesNameAndZhangTai(int page,int rows,String index,Integer zhuangtai) {
		PageBean<Classes> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		ClassesExample classesExample = new ClassesExample();
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria cCriteria = classesExample.createCriteria();
		cCriteria.andCapacityEqualTo(zhuangtai);
		cCriteria.andCnameEqualTo(index);
		List<Classes> classesList = classesMapper.selectByExample(classesExample);
		PageInfo<Classes> pageInfo = new PageInfo<>(classesList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(classesList);
		/*ClassesExample classesExample = new ClassesExample();
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria cCriteria = classesExample.createCriteria();
		cCriteria.andCapacityEqualTo(zhuangtai);
		cCriteria.andCnameEqualTo(index);
		List<Classes> classesList = classesMapper.selectByExample(classesExample);*/
		return pageBean;
	}
	/**
	 * 通过班级Id数组获得班级
	 */
	@Override
	public List<Classes> getClassesByclassesIds(List<Integer> classesIds){
		ClassesExample classesExample = new ClassesExample();
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria cCriteria = classesExample.createCriteria();
		cCriteria.andClassesIdIn(classesIds);
		List<Classes> classesList = classesMapper.selectByExample(classesExample);
		if(classesList!=null&&classesList.size()>0){
			return classesList;
		}
		else{
			return Collections.emptyList();
		}
	}
	/**
	 * 通过教师id查询教师的授课班级(不包含已经结课的班级)
	 */
	@Override
	public List<Classes> getClassesByTeacherId(Integer teacherId){
		//查询出中间表
		ClassesTeacherExample classesTeacherExample = new ClassesTeacherExample();
		cn.edu.qqhru.train.pojo.ClassesTeacherExample.Criteria ctCriteria = classesTeacherExample.createCriteria();
		ctCriteria.andTeacherIdEqualTo(teacherId);
		List<ClassesTeacherKey> ClassesTeacherKey = classTeacherMapper.selectByExample(classesTeacherExample);
		//查询关联的班级信息																
		List<Integer> classesIds = new ArrayList<>();
		if(ClassesTeacherKey!=null&&ClassesTeacherKey.size()>0){
			for (ClassesTeacherKey ctk : ClassesTeacherKey) {
				classesIds.add(ctk.getClassesId());
			}
		}
		List<Classes> classesList = new ArrayList<>();
		if(classesIds!=null&&classesIds.size()>0){
			ClassesExample classesExample = new ClassesExample();
			cn.edu.qqhru.train.pojo.ClassesExample.Criteria cCriteria = classesExample.createCriteria();
			cCriteria.andClassesIdIn(classesIds);
			cCriteria.andCapacityEqualTo(0);
			classesList = classesMapper.selectByExample(classesExample);
		}
		
		return classesList;
	}
	@Override
	public List<CourseDateVo> getPackageSyllabusByclassesId(Integer calssesId){
		//查询出课表信息
		SyllabusExample syllabusExample = new SyllabusExample();
		syllabusExample.setOrderByClause("week ASC");
		cn.edu.qqhru.train.pojo.SyllabusExample.Criteria syllCriteria = syllabusExample.createCriteria();
		syllCriteria.andClassIdEqualTo(calssesId);
		List<Syllabus> syllList = syllabusMapper.selectByExample(syllabusExample);
		//封装课表信息
		ArrayList<CourseDateVo> cdvList = new ArrayList<>();
		if(syllList!=null&&syllList.size()>0){
			for(Syllabus syll:syllList){
				Course amCourse=null;
				Course pmCourse=null;
				Course niCourse=null;
				CourseDateVo courseDateVo = new CourseDateVo();
				//封装日期
				courseDateVo.setDate(syll.getWeek());
				//封装上午课程
				if(syll.getAmfirst().length()!=0){
					amCourse = getCourseByCourseId(Integer.parseInt(syll.getAmfirst()));
					courseDateVo.setAmCourse(amCourse);
				}
				//封装下午课程
				if(syll.getPmfirst().length()!=0){
					pmCourse = getCourseByCourseId(Integer.parseInt(syll.getPmfirst()));
					courseDateVo.setPmCourse(pmCourse);
				}
				//封装晚上课程
				if(syll.getNight().length()!=0){
					niCourse = getCourseByCourseId(Integer.parseInt(syll.getNight()));
					courseDateVo.setNiCourse(niCourse);
				}
				//封装上午老师
				if(amCourse!=null){
					Teacher amTeacher = getTeacherBYid(amCourse.getTeacherId());
					courseDateVo.setAmTeacher(amTeacher);
				}
				//封装下午老师
				if(pmCourse!=null){
					Teacher pmTeacher = getTeacherBYid(pmCourse.getTeacherId());
					courseDateVo.setPmTeacher(pmTeacher);
				}
				//封装晚上老师
				if(niCourse!=null){
					Teacher niTeacher = getTeacherBYid(niCourse.getTeacherId());
					courseDateVo.setNiTeacher(niTeacher);
				}
				//添加到list集合中
				cdvList.add(courseDateVo);
			}
		}
		return cdvList;
	}
	/**
	 * 通过teacherId获得教师
	 * @param teacherId
	 */
	public Teacher getTeacherBYid(Integer teacherId){
		Teacher teacher = teacherMapper.selectByPrimaryKey(teacherId);
		if(teacher!=null){
			return teacher;
		}
		else{
			return new Teacher();
		}
	}
	/**
	 * 通过教师id获得班级教师列表 
	 */
	@Override
	public List<ClassesTeacherKey> getClassesTeacherListByTeacherId(int teacherId) {
		ClassesTeacherExample classesTeacherExample = new ClassesTeacherExample();
		classesTeacherExample.createCriteria().andTeacherIdEqualTo(teacherId);
		List<ClassesTeacherKey> classesTeacherList = classTeacherMapper.selectByExample(classesTeacherExample);
		if(classesTeacherList!=null&&classesTeacherList.size() > 0){
			return classesTeacherList;
		}
		else{
			return Collections.emptyList();
		}
	}
	/**
	 * 通过班级编号查询班级
	 */
	@Override
	public PageBean<Classes> getClassesByClassesNo(int page, int rows, String index) {
		PageBean<Classes> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		ClassesExample classesExample = new ClassesExample();
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria cCriteria = classesExample.createCriteria();
		cCriteria.andClassesNoEqualTo(index);
		List<Classes> classesList = classesMapper.selectByExample(classesExample);
		//封装分页信息
		PageInfo<Classes> pageInfo = new PageInfo<>(classesList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(classesList);
		return pageBean;
	}
	/**
	 * 通过班级名称查询班级
	 */
	@Override
	public PageBean<Classes> getClassesByClassesName(int page, int rows, String index) {
		PageBean<Classes> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		ClassesExample classesExample = new ClassesExample();
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria cCriteria = classesExample.createCriteria();
		cCriteria.andCnameEqualTo(index);
		List<Classes> classesList = classesMapper.selectByExample(classesExample);
		//封装分页信息
		PageInfo<Classes> pageInfo = new PageInfo<>(classesList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(classesList);
		return pageBean;
	}
	@Override
	public void updClassesByIds(Integer[] classesIds) {
		//修改班级状态
		ClassesExample example = new ClassesExample();
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria criteria = example.createCriteria();
		//将数组转换成list集合
		ArrayList<Integer> idList =new ArrayList<Integer>(Arrays.asList(classesIds));
		criteria.andClassesIdIn(idList);
		List<Classes> classesList = classesMapper.selectByExample(example);
		if(classesList!=null&&classesList.size()>0){
			for (Classes classes : classesList) {
				classes.setCapacity(0);
				classesMapper.updateByPrimaryKey(classes);
			}
		}
		//标注班级已重新开课
		ClassesPlanExample classesPlanExample = new ClassesPlanExample();
		cn.edu.qqhru.train.pojo.ClassesPlanExample.Criteria cpCriteria = classesPlanExample.createCriteria();
		cpCriteria.andClassesIdIn(idList);
		List<ClassesPlan> cpList = classPlanMapper.selectByExample(classesPlanExample);
		if(cpList!=null&&cpList.size()>0){
			for (ClassesPlan classesPlan : cpList) {
				classesPlan.setCname(classesPlan.getCname().replace("(已结课)", ""));
				classPlanMapper.updateByPrimaryKey(classesPlan);
			}
		}
	}
	/**
	 * 通过id恢复班级
	 */
	@Override
	public void updClasses(Integer classesId) {
		//修改班级状态
		Classes exitClasses = classesMapper.selectByPrimaryKey(classesId);
		if(exitClasses!=null){
			exitClasses.setCapacity(0);
			classesMapper.updateByPrimaryKey(exitClasses);
		}
		//标注班级已经结课
		ClassesPlanExample classesPlanExample = new ClassesPlanExample();
		cn.edu.qqhru.train.pojo.ClassesPlanExample.Criteria cpCriteria = classesPlanExample.createCriteria();
		cpCriteria.andClassesIdEqualTo(classesId);
		List<ClassesPlan> cpList = classPlanMapper.selectByExample(classesPlanExample);
		if(cpList!=null&&cpList.size()>0){
			for (ClassesPlan classesPlan : cpList) {
				classesPlan.setCname(classesPlan.getCname().replace("(已结课)", ""));
				classPlanMapper.updateByPrimaryKey(classesPlan);
			}
		}
	}
	/**
	 *通过老师id获得他所教课的班级 
	 */
	@Override
	public PageBean<Classes> getClassesByLectureTeacherId(Integer teacherId, int page, int rows) {
		//查询出老师教的课程的id
		CourseExample courseExample = new CourseExample();
		cn.edu.qqhru.train.pojo.CourseExample.Criteria courseCriteria = courseExample.createCriteria();
		courseCriteria.andTeacherIdEqualTo(teacherId);
		List<Course> courseList = courseMapper.selectByExample(courseExample);
		List<Integer> courseIds=new ArrayList<>();
		if(courseList!=null&&courseList.size()>0){
			for (Course course : courseList) {
				courseIds.add(course.getCourseId());
			}
		}
		//遍历所有的课表
		SyllabusExample syllabusExample = new SyllabusExample();
		cn.edu.qqhru.train.pojo.SyllabusExample.Criteria syllCriteria = syllabusExample.createCriteria();
		syllCriteria.andSyllabusIdIsNotNull();
		List<Syllabus> syllabusList = syllabusMapper.selectByExample(syllabusExample);
		List<Syllabus> teacherSyllabusList = new ArrayList<>();
		if(syllabusList!=null&&syllabusList.size()>0){
			for (Syllabus syllabus : syllabusList) {
				if(courseIds.contains(syllabus.getAmfirst().trim().length()==0?"nullValue":Integer.valueOf(syllabus.getAmfirst()))){
					teacherSyllabusList.add(syllabus);
				}
				else if(courseIds.contains(syllabus.getPmfirst().trim().length()==0?"nullValue":Integer.valueOf(syllabus.getPmfirst()))){
					teacherSyllabusList.add(syllabus);
				}
				else if(courseIds.contains(syllabus.getNight().trim().length()==0?"nullValue":Integer.valueOf(syllabus.getNight()))){
					teacherSyllabusList.add(syllabus);
				}
			}
		}
		//遍历出班级id
		List<Integer> classesIdList=new ArrayList<>();
		if(teacherSyllabusList!=null&&teacherSyllabusList.size()>0){
			for (Syllabus syllabus : teacherSyllabusList) {
				classesIdList.add(syllabus.getClassId());
			}
		}
		//查询出班级
		
		PageBean<Classes> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);

		List<Classes> classesList=new ArrayList<>();
		if(classesIdList!=null&&classesIdList.size()>0){
			ClassesExample classesExample = new ClassesExample();
			cn.edu.qqhru.train.pojo.ClassesExample.Criteria classesCriteria = classesExample.createCriteria();
			classesCriteria.andClassesIdIn(classesIdList);
			classesCriteria.andCapacityEqualTo(0);
			classesList = classesMapper.selectByExample(classesExample);
		}
		
		PageInfo<Classes> pageInfo = new PageInfo<>(classesList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(classesList);
		return pageBean;
	}
	
	/**
	 *通过老师id获得他所教课的班级(不带分页)
	 */
	@Override
	public List<Classes> getClassesByLectureTeacherIdNoPage(Integer teacherId){
		//查询出老师教的课程的id
		CourseExample courseExample = new CourseExample();
		cn.edu.qqhru.train.pojo.CourseExample.Criteria courseCriteria = courseExample.createCriteria();
		courseCriteria.andTeacherIdEqualTo(teacherId);
		List<Course> courseList = courseMapper.selectByExample(courseExample);
		List<Integer> courseIds=new ArrayList<>();
		if(courseList!=null&&courseList.size()>0){
			for (Course course : courseList) {
				courseIds.add(course.getCourseId());
			}
		}
		//遍历所有的课表
		SyllabusExample syllabusExample = new SyllabusExample();
		cn.edu.qqhru.train.pojo.SyllabusExample.Criteria syllCriteria = syllabusExample.createCriteria();
		syllCriteria.andSyllabusIdIsNotNull();
		List<Syllabus> syllabusList = syllabusMapper.selectByExample(syllabusExample);
		List<Syllabus> teacherSyllabusList = new ArrayList<>();
		if(syllabusList!=null&&syllabusList.size()>0){
			for (Syllabus syllabus : syllabusList) {
				if(courseIds.contains(syllabus.getAmfirst().trim().length()==0?"nullValue":Integer.valueOf(syllabus.getAmfirst()))){
					teacherSyllabusList.add(syllabus);
				}
				else if(courseIds.contains(syllabus.getPmfirst().trim().length()==0?"nullValue":Integer.valueOf(syllabus.getPmfirst()))){
					teacherSyllabusList.add(syllabus);
				}
				else if(courseIds.contains(syllabus.getNight().trim().length()==0?"nullValue":Integer.valueOf(syllabus.getNight()))){
					teacherSyllabusList.add(syllabus);
				}
			}
		}
		//遍历出班级id
		List<Integer> classesIdList=new ArrayList<>();
		if(teacherSyllabusList!=null&&teacherSyllabusList.size()>0){
			for (Syllabus syllabus : teacherSyllabusList) {
				classesIdList.add(syllabus.getClassId());
			}
		}
		//查询出班级
		List<Classes> classesList=new ArrayList<>();
		if(classesIdList!=null&&classesIdList.size()>0){
			ClassesExample classesExample = new ClassesExample();
			cn.edu.qqhru.train.pojo.ClassesExample.Criteria classesCriteria = classesExample.createCriteria();
			classesCriteria.andClassesIdIn(classesIdList);
			classesCriteria.andCapacityEqualTo(0);
			classesList = classesMapper.selectByExample(classesExample);
		}
		return classesList;
	}
	/**
	 * 通过教师id获得班级
	 */
	@Override
	public List<Classes> getClassesByClassesTeacherId(Integer teacherId) {
		ClassesTeacherExample classesTeacherExample = new ClassesTeacherExample();
		Criteria ctCriteria = classesTeacherExample.createCriteria();
		ctCriteria.andTeacherIdEqualTo(teacherId);
		List<ClassesTeacherKey> classesTeacherList = classTeacherMapper.selectByExample(classesTeacherExample);
		List<Classes> classesList = new ArrayList<>();
		if(classesTeacherList!=null&&classesTeacherList.size()>0){
			for (ClassesTeacherKey classesTeacherKey : classesTeacherList) {
				ClassesExample classesExample = new ClassesExample();
				cn.edu.qqhru.train.pojo.ClassesExample.Criteria cCriteria = classesExample.createCriteria();
				cCriteria.andClassesIdEqualTo(classesTeacherKey.getClassesId());
				cCriteria.andCapacityEqualTo(0);
				List<Classes> cList = classesMapper.selectByExample(classesExample);
				if(cList.size()!=0){
					Classes classes = cList.get(0);
					classesList.add(classes);
				}
			}
		}
		return classesList;
	}
	@Override
	public PageBean<Student> getAllExecuteStu(int classesId,int page, int rows) {
		PageBean<Student> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		StudentExample studentExample = new StudentExample();
		cn.edu.qqhru.train.pojo.StudentExample.Criteria stuCriteria = studentExample.createCriteria();
		stuCriteria.andIsfinishEqualTo(0);
		stuCriteria.andClassesIdIsNull();
//		stuCriteria.andClassesIdNotEqualTo(classesId);
		List<Student> studentList = studentMapper.selectByExample(studentExample);
		//封装分页信息
		PageInfo<Student> pageInfo = new PageInfo<>(studentList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(studentList);
		return pageBean;
	}
	@Override
	public PageBean<Student> getAllStuByClassesId(Integer classesId, int page, int rows) {
		PageBean<Student> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		StudentExample studentExample = new StudentExample();
		cn.edu.qqhru.train.pojo.StudentExample.Criteria stuCriteria = studentExample.createCriteria();
		stuCriteria.andClassesIdEqualTo(classesId);
		List<Student> studentList = studentMapper.selectByExample(studentExample);
		//封装分页信息
		PageInfo<Student> pageInfo = new PageInfo<>(studentList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(studentList);
		return pageBean;
	}
	/**
	 * 获得班主任所带的所有班级
	 */
	@Override
	public List<Classes> getAllClassesByClassesTeacher(Integer teacherId) {
		ClassesTeacherExample classesTeacherExample = new ClassesTeacherExample();
		Criteria ctCriteria = classesTeacherExample.createCriteria();
		ctCriteria.andTeacherIdEqualTo(teacherId);
		List<ClassesTeacherKey> ctList = classTeacherMapper.selectByExample(classesTeacherExample);
		List<Classes> classesList=new ArrayList<>();
		if(ctList!=null&&ctList.size()>0){
			for (ClassesTeacherKey classesTeacherKey : ctList) {
				Classes classes = classesMapper.selectByPrimaryKey(classesTeacherKey.getClassesId());
				classesList.add(classes);
			}
		}
		return classesList;
	}
	/**
	 * 班级学生毕业
	 */
	@Override
	public void deleteStudent(List <Integer> classesIds) {
		List<Student> stuList=new ArrayList<>();
		if(classesIds!=null&&classesIds.size()>0){
			StudentExample studentExample = new StudentExample();
			cn.edu.qqhru.train.pojo.StudentExample.Criteria stuCriteria = studentExample.createCriteria();
			stuCriteria.andClassesIdIn(classesIds);
			stuList = studentMapper.selectByExample(studentExample);
		}
		if(stuList!=null&&stuList.size()>0){
			for (Student student : stuList) {
				student.setIsfinish(1);
				studentMapper.updateByPrimaryKey(student);
			}
		}
	}
	/**
	 * 恢复学生
	 */
	@Override
	public void updStudent(List<Integer> classesIds) {
		List<Student> stuList=new ArrayList<>();
		if(classesIds!=null&&classesIds.size()>0){
			StudentExample studentExample = new StudentExample();
			cn.edu.qqhru.train.pojo.StudentExample.Criteria stuCriteria = studentExample.createCriteria();
			stuCriteria.andClassesIdIn(classesIds);
			stuList = studentMapper.selectByExample(studentExample);
		}
		if(stuList!=null&&stuList.size()>0){
			for (Student student : stuList) {
				student.setIsfinish(0);
				studentMapper.updateByPrimaryKey(student);
			}
		}
	}
	/**
	 * 分页查询班主任所带的班级
	 */
	@Override
	public PageBean<Classes> getClassesByClassesTeacherId(int page, int rows,String index,Integer searchWay, Integer teacherId,Integer type) {
		
		ClassesTeacherExample classesTeacherExample = new ClassesTeacherExample();
		Criteria ctCriteria = classesTeacherExample.createCriteria();
		ctCriteria.andTeacherIdEqualTo(teacherId);
		List<ClassesTeacherKey> classesTeacherList = classTeacherMapper.selectByExample(classesTeacherExample);
		
		List<Integer> classesIdList = new ArrayList<>();
		if(classesTeacherList!=null&&classesTeacherList.size()>0){
			for (ClassesTeacherKey classesTeacherKey : classesTeacherList) {
				classesIdList.add(classesTeacherKey.getClassesId());
			}
		}
		
		PageBean<Classes> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		
		List<Classes> classesList = new ArrayList<>();
		if(classesIdList!=null&&classesIdList.size()>0){
			ClassesExample classesExample = new ClassesExample();
			classesExample.setOrderByClause("createtime desc");
			cn.edu.qqhru.train.pojo.ClassesExample.Criteria cCriteria = classesExample.createCriteria();
			cCriteria.andClassesIdIn(classesIdList);
			if(type!=null){
				if(type==0){
					cCriteria.andCapacityEqualTo(0);
				}else{
					cCriteria.andCapacityEqualTo(1);
				}
			}
			else{
				//按班级编号查
				if(searchWay==0){
					cCriteria.andClassesNoEqualTo(index);
				}
				//按班级名称查
				else{
					cCriteria.andCnameEqualTo(index);
				}
			}
			classesList = classesMapper.selectByExample(classesExample);
			
		}
		PageInfo<Classes> pageInfo = new PageInfo<>(classesList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(classesList);
		return pageBean;
	}
	/**
	 * 计算班级数量
	 * @param index
	 * @param searchWay
	 * @param teacherId
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unused")
	private Integer computeCount(String index,Integer searchWay, Integer teacherId,Integer type){
		ClassesTeacherExample classesTeacherExample = new ClassesTeacherExample();
		Criteria ctCriteria = classesTeacherExample.createCriteria();
		ctCriteria.andTeacherIdEqualTo(teacherId);
		List<ClassesTeacherKey> classesTeacherList = classTeacherMapper.selectByExample(classesTeacherExample);
		
		List<Classes> classesList = new ArrayList<>();
		if(classesTeacherList!=null&&classesTeacherList.size()>0){
			for (ClassesTeacherKey classesTeacherKey : classesTeacherList) {
				ClassesExample classesExample = new ClassesExample();
				cn.edu.qqhru.train.pojo.ClassesExample.Criteria cCriteria = classesExample.createCriteria();
				cCriteria.andClassesIdEqualTo(classesTeacherKey.getClassesId());
				if(type!=null){
					if(type==0){
						cCriteria.andCapacityEqualTo(0);
					}else{
						cCriteria.andCapacityEqualTo(1);
					}
				}
				else{
					//按班级编号查
					if(searchWay==0){
						cCriteria.andClassesNoEqualTo(index);
					}
					//按班级名称查
					else{
						cCriteria.andCnameEqualTo(index);
					}
				}
				List<Classes> cList = classesMapper.selectByExample(classesExample);
				if(cList.size()!=0){
					Classes classes = cList.get(0);
					classesList.add(classes);
				}
			}
		}
		return classesList.size();
	}
	/**
	 * 通过classesIds分页查询课表
	 */
	@Override
	public PageBean<Syllabus> getSyllabusByClassesIds(List<Integer> classesIds, int page, int rows) {
		PageBean<Syllabus> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		List<Syllabus> sullabusList = new ArrayList<>();
		if(classesIds!=null&&classesIds.size()>0){
			SyllabusExample syllabusExample = new SyllabusExample();
			cn.edu.qqhru.train.pojo.SyllabusExample.Criteria syllCriteria = syllabusExample.createCriteria();
			syllCriteria.andClassIdIn(classesIds);
			sullabusList = syllabusMapper.selectByExample(syllabusExample);
		}
		
		PageInfo<Syllabus> pageInfo = new PageInfo<>(sullabusList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(sullabusList);
		return pageBean;
	}
	/**
	 *获得所有的课表
	 */
	@Override
	public List<CourseDateVo> getSyllabusByClassesIds(List<Integer> classesIds) {
		List<Syllabus> sullabusList = new ArrayList<>();
		if(classesIds!=null&&classesIds.size()>0){
			SyllabusExample syllabusExample = new SyllabusExample();
			cn.edu.qqhru.train.pojo.SyllabusExample.Criteria syllCriteria = syllabusExample.createCriteria();
			syllCriteria.andClassIdIn(classesIds);
			sullabusList = syllabusMapper.selectByExample(syllabusExample);
		}
		
		ArrayList<CourseDateVo> cdvList = new ArrayList<>();
		for (Syllabus syll : sullabusList) {
			Course amCourse=null; 
			Course pmCourse=null; 
			Course niCourse=null; 
			CourseDateVo courseDateVo = new CourseDateVo();
			// 封装日期
			courseDateVo.setDate(syll.getWeek());
			// 封装课表id
			courseDateVo.setSyllabusId(syll.getSyllabusId());
			// 封装上午课程
			if(syll.getAmfirst().trim().length()!=0){
				amCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getAmfirst()));
				courseDateVo.setAmCourse(amCourse);
			}
			// 封装下午课程
			if(syll.getPmfirst().trim().length()!=0){
				pmCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getPmfirst()));
				courseDateVo.setPmCourse(pmCourse);
			}
			// 封装晚上课程
			if(syll.getNight().trim().length()!=0){
				niCourse = courseMapper.selectByPrimaryKey(Integer.parseInt(syll.getNight()));
				courseDateVo.setNiCourse(niCourse);
			}
			// 封装上午老师
			if(amCourse!=null){
				Teacher amTeacher = teacherMapper.selectByPrimaryKey(amCourse.getTeacherId());
				courseDateVo.setAmTeacher(amTeacher);
			}
			// 封装下午老师
			if(pmCourse!=null){
				Teacher pmTeacher = teacherMapper.selectByPrimaryKey(pmCourse.getTeacherId());
				courseDateVo.setPmTeacher(pmTeacher);
			}
			// 封装晚上老师
			if(niCourse!=null){
				Teacher niTeacher = teacherMapper.selectByPrimaryKey(niCourse.getTeacherId());
				courseDateVo.setNiTeacher(niTeacher);
			}

			//封装班级
			Classes classes = classesMapper.selectByPrimaryKey(syll.getClassId());
			courseDateVo.setClasses(classes);
			
			// 添加到list集合中
			cdvList.add(courseDateVo);
		}
		
		return cdvList;
	}
	/**
	 * 通过班级名称获得班级
	 */
	@Override
	public List<Classes> getClassesByClassesName(String index) {
		ClassesExample classesExample = new ClassesExample();
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria classesCriteria = classesExample.createCriteria();
		classesCriteria.andCnameEqualTo(index);
		List<Classes> classesList = classesMapper.selectByExample(classesExample);
		if(classesList!=null&&classesList.size()>0){
			return classesList;
		}
		else{
			return Collections.emptyList();
		}
	}
	
}
