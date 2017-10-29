package cn.edu.qqhru.train.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.qqhru.train.mapper.AdminMapper;
import cn.edu.qqhru.train.mapper.ApplicationMapper;
import cn.edu.qqhru.train.mapper.ApproveinfoMapper;
import cn.edu.qqhru.train.mapper.BookMapper;
import cn.edu.qqhru.train.mapper.ClassesMapper;
import cn.edu.qqhru.train.mapper.ClassesPlanMapper;
import cn.edu.qqhru.train.mapper.DeviceMapper;
import cn.edu.qqhru.train.mapper.PlanBooksMapper;
import cn.edu.qqhru.train.mapper.PlanDevicesMapper;
import cn.edu.qqhru.train.mapper.PlanMapper;
import cn.edu.qqhru.train.mapper.PlanTeacherMapper;
import cn.edu.qqhru.train.mapper.TeacherMapper;
import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.AdminExample;
import cn.edu.qqhru.train.pojo.Application;
import cn.edu.qqhru.train.pojo.ApplicationExample;
import cn.edu.qqhru.train.pojo.Approveinfo;
import cn.edu.qqhru.train.pojo.ApproveinfoExample;
import cn.edu.qqhru.train.pojo.Book;
import cn.edu.qqhru.train.pojo.BookExample;
import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.ClassesExample;
import cn.edu.qqhru.train.pojo.ClassesPlan;
import cn.edu.qqhru.train.pojo.ClassesPlanExample;
import cn.edu.qqhru.train.pojo.Device;
import cn.edu.qqhru.train.pojo.DeviceExample;
import cn.edu.qqhru.train.pojo.Plan;
import cn.edu.qqhru.train.pojo.PlanBooks;
import cn.edu.qqhru.train.pojo.PlanBooksExample;
import cn.edu.qqhru.train.pojo.PlanDevices;
import cn.edu.qqhru.train.pojo.PlanDevicesExample;
import cn.edu.qqhru.train.pojo.PlanExample;
import cn.edu.qqhru.train.pojo.PlanExample.Criteria;
import cn.edu.qqhru.train.pojo.PlanTeacher;
import cn.edu.qqhru.train.pojo.PlanTeacherExample;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.pojo.TeacherExample;
import cn.edu.qqhru.train.service.PlanService;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.PlanVo;

@Service
public class PlanServiceImpl implements PlanService {
	@Autowired
	private PlanMapper planMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private ClassesMapper classesMapper;
	@Autowired
	private BookMapper bookMapper;
	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private PlanDevicesMapper PlanDevicesMapper;
	@Autowired
	private PlanBooksMapper planBooksMapper;
	@Autowired
	private ClassesPlanMapper classesPlanMapper;
	@Autowired
	private PlanTeacherMapper planTeacherMapper;

	// BQ
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskSerivce;
	@Autowired
	private ApplicationMapper applicationMapper;
	@Autowired
	private ApproveinfoMapper approveinfoMapper;

	@Override
	public PageBean<PlanVo> getAllPlan(int page, int rows) {
		PageBean<PlanVo> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows, "createtime desc");
		PlanExample example = new PlanExample();
		Criteria criteria = example.createCriteria();
		criteria.andPlanIdIsNotNull();
		List<Plan> list = planMapper.selectByExample(example);
		List<PlanVo> list2 = new ArrayList<>();
		for (Plan plan : list) {
			PlanVo planVo = new PlanVo();
			Admin admin = adminMapper.selectByPrimaryKey(plan.getAdminId());
			planVo.setPlanId(plan.getPlanId());
			planVo.setAdminId(plan.getAdminId());
			planVo.setCreatetime(plan.getCreatetime());
			planVo.setSign(plan.getSign());
			planVo.setExamName(plan.getExamName());
			planVo.setUpdatetime(plan.getUpdatetime());
			planVo.setPlanAim(plan.getPlanAim());
			planVo.setAbility(plan.getAbility());
			planVo.setScale(plan.getScale());
			planVo.setPlanContent(plan.getPlanContent());
			planVo.setPlanPattern(plan.getPlanPattern());
			planVo.setPlanRequirement(plan.getPlanRequirement());
			planVo.setExamPattern(plan.getExamPattern());
			planVo.setPname(plan.getPname());
			planVo.setAdminName(admin.getUsername());
			list2.add(planVo);
		}
		PageInfo<PlanVo> pageInfo = new PageInfo<>(list2);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list2);
		return pageBean;
	}

	@Override
	public PlanVo getPlanOne(int id) {
		List<String> deviceNames = new ArrayList<>();
		List<String> bookNames = new ArrayList<>();
		List<String> teacherNames = new ArrayList<>();
		List<String> classesNames = new ArrayList<>();
		List<Integer> teacherIds = new ArrayList<>();
		List<Integer> bookIds = new ArrayList<>();
		List<Integer> devIds = new ArrayList<>();
		List<Integer> classesIds = new ArrayList<>();
		PlanVo planVo = new PlanVo();
		Plan plan = planMapper.selectByPrimaryKey(id);
		// 获取本培训方案的设备名称
		PlanDevicesExample example = new PlanDevicesExample();
		example.createCriteria().andPlanIdEqualTo(id);
		List<PlanDevices> list = PlanDevicesMapper.selectByExample(example);
		for (PlanDevices planDevices : list) {
			deviceNames.add(planDevices.getDname());
			devIds.add(planDevices.getDeviceId());
		}
		// 获取本培训方案的教材名称
		PlanBooksExample example2 = new PlanBooksExample();
		example2.createCriteria().andPlanIdEqualTo(id);
		List<PlanBooks> list2 = planBooksMapper.selectByExample(example2);
		for (PlanBooks planBooks : list2) {
			bookNames.add(planBooks.getBname());
			bookIds.add(planBooks.getBookId());
		}
		// 获取本培训方案的讲师名称
		PlanTeacherExample example3 = new PlanTeacherExample();
		example3.createCriteria().andPlanIdEqualTo(id);
		List<PlanTeacher> list3 = planTeacherMapper.selectByExample(example3);
		for (PlanTeacher planTeacher : list3) {
			teacherNames.add(planTeacher.getTname());
			teacherIds.add(planTeacher.getTeacherId());
		}
		// 获取本培训方案的班级名称
		ClassesPlanExample example4 = new ClassesPlanExample();
		example4.createCriteria().andPlanIdEqualTo(id);
		List<ClassesPlan> list4 = classesPlanMapper.selectByExample(example4);
		for (ClassesPlan classesPlan : list4) {
			classesNames.add(classesPlan.getCname());
			classesIds.add(classesPlan.getClassesId());
		}
		// 获取本培训方案的负责人名称
		Admin admin = adminMapper.selectByPrimaryKey(plan.getAdminId());
		planVo.setPlanId(plan.getPlanId());
		planVo.setAdminId(plan.getAdminId());
		planVo.setCreatetime(plan.getCreatetime());
		planVo.setSign(plan.getSign());
		planVo.setExamName(plan.getExamName());
		planVo.setUpdatetime(plan.getUpdatetime());
		planVo.setPlanAim(plan.getPlanAim());
		planVo.setAbility(plan.getAbility());
		planVo.setScale(plan.getScale());
		planVo.setPlanContent(plan.getPlanContent());
		planVo.setPlanPattern(plan.getPlanPattern());
		planVo.setPlanRequirement(plan.getPlanRequirement());
		planVo.setExamPattern(plan.getExamPattern());
		planVo.setPname(plan.getPname());
		planVo.setAdminName(admin.getUsername());
		planVo.setDevNames(deviceNames);
		planVo.setBookNames(bookNames);
		planVo.setTeacherNames(teacherNames);
		planVo.setClassesNames(classesNames);
		planVo.setBookIds(bookIds);
		planVo.setTeacherIds(teacherIds);
		planVo.setDevIds(devIds);
		planVo.setClassesIds(classesIds);
		return planVo;
	}

	@Override
	public void delPlan(int[] ids) {
		List<Integer> id = new ArrayList<>();
		for (int i : ids) {
			id.add(i);
		}

		PlanExample example = new PlanExample();
		Criteria criteria = example.createCriteria();
		criteria.andPlanIdIn(id);
		planMapper.deleteByExample(example);

		PlanDevicesExample example2 = new PlanDevicesExample();
		example2.createCriteria().andPlanIdIn(id);
		PlanDevicesMapper.deleteByExample(example2);

		PlanBooksExample example3 = new PlanBooksExample();
		example3.createCriteria().andPlanIdIn(id);
		planBooksMapper.deleteByExample(example3);

		PlanTeacherExample example4 = new PlanTeacherExample();
		example4.createCriteria().andPlanIdIn(id);
		planTeacherMapper.deleteByExample(example4);

		ClassesPlanExample example5 = new ClassesPlanExample();
		example5.createCriteria().andPlanIdIn(id);
		classesPlanMapper.deleteByExample(example5);
		
		ApplicationExample example6 = new ApplicationExample();
		example6.createCriteria().andPlanIdIn(id);
		List<Application> list = applicationMapper.selectByExample(example6);
		for (Application application : list) {
			ApproveinfoExample example7 = new ApproveinfoExample();
			example7.createCriteria().andApplicationIdEqualTo(application.getApplicationId());
			approveinfoMapper.deleteByExample(example7);
		}
		applicationMapper.deleteByExample(example6);
		
		
	}

	@Override
	public void addPlan(Plan plan, Integer[] classIds, Integer[] deviceIds, Integer[] bookIds, Integer[] teaIds) {
		List<Integer> device = new ArrayList<>();
		List<Integer> book = new ArrayList<>();
		List<Integer> classes = new ArrayList<>();
		List<Integer> teacher = new ArrayList<>();
		for (Integer integer : teaIds) {
			teacher.add(integer);
		}
		for (Integer integer : bookIds) {
			book.add(integer);
		}
		for (Integer integer : deviceIds) {
			device.add(integer);
		}
		for (Integer integer : classIds) {
			classes.add(integer);
		}
		plan.setCreatetime(new Date());
		plan.setUpdatetime(new Date());
		plan.setSign(0);
		planMapper.insert(plan);

		DeviceExample example = new DeviceExample();
		example.createCriteria().andDeviceIdIn(device);
		List<Device> list = deviceMapper.selectByExample(example);
		for (Device device2 : list) {
			PlanDevices planDevices = new PlanDevices();
			planDevices.setDeviceId(device2.getDeviceId());
			planDevices.setDname(device2.getDname());
			planDevices.setPlanId(plan.getPlanId());
			PlanDevicesMapper.insert(planDevices);
		}

		BookExample example1 = new BookExample();
		example1.createCriteria().andBookIdIn(book);
		List<Book> list2 = bookMapper.selectByExample(example1);
		for (Book book2 : list2) {
			PlanBooks planBooks = new PlanBooks();
			planBooks.setBname(book2.getBname());
			planBooks.setBookId(book2.getBookId());
			planBooks.setPlanId(plan.getPlanId());
			planBooksMapper.insert(planBooks);
		}

		TeacherExample example2 = new TeacherExample();
		example2.createCriteria().andTeacherIdIn(teacher);
		List<Teacher> list3 = teacherMapper.selectByExample(example2);
		for (Teacher teacher2 : list3) {
			PlanTeacher planTeacher = new PlanTeacher();
			planTeacher.setTeacherId(teacher2.getTeacherId());
			planTeacher.setTname(teacher2.getUsername());
			planTeacher.setPlanId(plan.getPlanId());
			planTeacherMapper.insert(planTeacher);
		}

		ClassesExample example3 = new ClassesExample();
		example3.createCriteria().andClassesIdIn(classes);
		List<Classes> list4 = classesMapper.selectByExample(example3);
		for (Classes classes2 : list4) {
			ClassesPlan classesPlan = new ClassesPlan();
			classesPlan.setClassesId(classes2.getClassesId());
			classesPlan.setCname(classes2.getCname());
			classesPlan.setPlanId(plan.getPlanId());
			classesPlanMapper.insert(classesPlan);
		}
	}

	@Override
	public void editPlan(Plan plan, Integer[] classIds, Integer[] deviceIds, Integer[] bookIds, Integer[] teaIds) {
		Plan planOld = planMapper.selectByPrimaryKey(plan.getPlanId());
		plan.setCreatetime(planOld.getCreatetime());
		plan.setUpdatetime(new Date());
		planMapper.updateByPrimaryKey(plan);

		PlanTeacherExample example = new PlanTeacherExample();
		example.createCriteria().andPlanIdEqualTo(plan.getPlanId());
		planTeacherMapper.deleteByExample(example);

		PlanBooksExample example2 = new PlanBooksExample();
		example2.createCriteria().andPlanIdEqualTo(plan.getPlanId());
		planBooksMapper.deleteByExample(example2);

		PlanDevicesExample example3 = new PlanDevicesExample();
		example3.createCriteria().andPlanIdEqualTo(plan.getPlanId());
		PlanDevicesMapper.deleteByExample(example3);

		ClassesPlanExample example4 = new ClassesPlanExample();
		example4.createCriteria().andPlanIdEqualTo(plan.getPlanId());
		classesPlanMapper.deleteByExample(example4);

		List<Integer> device = new ArrayList<>();
		List<Integer> book = new ArrayList<>();
		List<Integer> classes = new ArrayList<>();
		List<Integer> teacher = new ArrayList<>();
		for (Integer integer : teaIds) {
			teacher.add(integer);
		}
		for (Integer integer : bookIds) {
			book.add(integer);
		}
		for (Integer integer : deviceIds) {
			device.add(integer);
		}
		for (Integer integer : classIds) {
			classes.add(integer);
		}

		DeviceExample example5 = new DeviceExample();
		example5.createCriteria().andDeviceIdIn(device);
		List<Device> list = deviceMapper.selectByExample(example5);
		for (Device device2 : list) {
			PlanDevices planDevices = new PlanDevices();
			planDevices.setDeviceId(device2.getDeviceId());
			planDevices.setDname(device2.getDname());
			planDevices.setPlanId(plan.getPlanId());
			PlanDevicesMapper.insert(planDevices);
		}

		BookExample example6 = new BookExample();
		example6.createCriteria().andBookIdIn(book);
		List<Book> list2 = bookMapper.selectByExample(example6);
		for (Book book2 : list2) {
			PlanBooks planBooks = new PlanBooks();
			planBooks.setBname(book2.getBname());
			planBooks.setBookId(book2.getBookId());
			planBooks.setPlanId(plan.getPlanId());
			planBooksMapper.insert(planBooks);
		}
		TeacherExample example7 = new TeacherExample();
		example7.createCriteria().andTeacherIdIn(teacher);
		List<Teacher> list3 = teacherMapper.selectByExample(example7);
		for (Teacher teacher2 : list3) {
			PlanTeacher planTeacher = new PlanTeacher();
			planTeacher.setTeacherId(teacher2.getTeacherId());
			planTeacher.setTname(teacher2.getUsername());
			planTeacher.setPlanId(plan.getPlanId());
			planTeacherMapper.insert(planTeacher);
		}

		ClassesExample example8 = new ClassesExample();
		example8.createCriteria().andClassesIdIn(classes);
		List<Classes> list4 = classesMapper.selectByExample(example8);
		for (Classes classes2 : list4) {
			ClassesPlan classesPlan = new ClassesPlan();
			classesPlan.setClassesId(classes2.getClassesId());
			classesPlan.setCname(classes2.getCname());
			classesPlan.setPlanId(plan.getPlanId());
			classesPlanMapper.insert(classesPlan);
		}
	}

	@Override
	public PageBean<PlanVo> getPlan(String pname, int page, int rows) {
		PageBean<PlanVo> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows, "createtime desc");
		PlanExample example = new PlanExample();
		Criteria criteria = example.createCriteria();
		criteria.andPnameLike("%" + pname + "%");
		List<Plan> list = planMapper.selectByExample(example);
		List<PlanVo> list2 = new ArrayList<>();
		for (Plan plan : list) {
			PlanVo planVo = new PlanVo();
			Admin admin = adminMapper.selectByPrimaryKey(plan.getAdminId());
			planVo.setPlanId(plan.getPlanId());
			planVo.setAdminId(plan.getAdminId());
			planVo.setCreatetime(plan.getCreatetime());
			planVo.setSign(plan.getSign());
			planVo.setExamName(plan.getExamName());
			planVo.setUpdatetime(plan.getUpdatetime());
			planVo.setPlanAim(plan.getPlanAim());
			planVo.setAbility(plan.getAbility());
			planVo.setScale(plan.getScale());
			planVo.setPlanContent(plan.getPlanContent());
			planVo.setPlanPattern(plan.getPlanPattern());
			planVo.setPlanRequirement(plan.getPlanRequirement());
			planVo.setExamPattern(plan.getExamPattern());
			planVo.setPname(plan.getPname());
			planVo.setAdminName(admin.getUsername());
			list2.add(planVo);
		}
		PageInfo<PlanVo> pageInfo = new PageInfo<>(list2);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list2);
		return pageBean;
	}

	// ==================================================================
	/**
	 * 1.部署流程定义 2.启动流程实例 3.完成第一次申请提交任务
	 */
	@Override
	public boolean submitApp(Admin admin, int id) {
		Application application = new Application();
		String pname = "";
		Plan plan = null;
		Admin adminNext = null;
		PlanExample example = new PlanExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andPlanIdEqualTo(id);
		List<Plan> planList = planMapper.selectByExample(example);
		if (planList != null && planList.size() > 0) {
			plan = planList.get(0);
			pname = plan.getPname();
		}

		AdminExample adminExample = new AdminExample();
		cn.edu.qqhru.train.pojo.AdminExample.Criteria createCriteria2 = adminExample.createCriteria();
		createCriteria2.andIdentifyEqualTo(3);
		List<Admin> selectByExample = adminMapper.selectByExample(adminExample);
		if(selectByExample != null && selectByExample.size() > 0){
			adminNext = selectByExample.get(0);
		}
		application.setApplyDate(new Date());
		application.setPlanId(id);
		// 0.未提交 1.审批中 2.未通过 3.通过
		application.setStatus(1);
		application.setTitle(pname);
		application.setUserId(admin.getAdminId());
		Deployment deploy = null;
		// 流程部署ID
		String processId = null;
		// 流程定义ID
		String deployDef = null;
		try {
			//流程部署
			DeploymentBuilder createDeployment = repositoryService.createDeployment();
			createDeployment.addClasspathResource("flow/plan.bpmn");
			createDeployment.addClasspathResource("flow/plan.png");
			createDeployment.category("计划审批");
			createDeployment.name("计划审批");
			deploy = createDeployment.deploy();
			
			//根据流程部署查询流程定义
			processId = deploy.getId();
			ProcessDefinitionQuery createProcessDefinitionQuery = repositoryService.createProcessDefinitionQuery();
			ProcessDefinitionQuery deploymentId = createProcessDefinitionQuery.deploymentId(processId);
			ProcessDefinition singleResult = deploymentId.singleResult();
			deployDef = singleResult.getId();
			
			//添加流程定义ID 便于查询图片资源
			application.setPdId(deployDef);
			applicationMapper.insert(application);
			
			Map<String, Object> variables = new HashMap<String, Object>();
			application.setUserId(adminNext.getAdminId());
			variables.put("application", application);
			variables.put("applicationId",application.getApplicationId());
			// 启动流程实例
			ProcessInstance pi = runtimeService.startProcessInstanceById(deployDef, variables);
			TaskQuery query = taskSerivce.createTaskQuery();
			query.taskAssignee(application.getUserId() + "");
			query.processInstanceId(pi.getId());
			Task task = query.singleResult();
			String taskId = task.getId();
			// 完成提交申请
			taskSerivce.complete(taskId);
			plan.setSign(1);
			planMapper.updateByExample(plan, example);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public String getUsernameByIdentify(int identify) {
		AdminExample adminExample = new AdminExample();
		Admin admin = null;
		cn.edu.qqhru.train.pojo.AdminExample.Criteria createCriteria = adminExample.createCriteria();
		createCriteria.andIdentifyEqualTo(identify);
		List<Admin> adminList = adminMapper.selectByExample(adminExample);
		if(adminList != null && adminList.size() > 0){
			admin = adminList.get(0);
		}
		return admin.getUsername();
	}

	@Override
	public List<Admin> getAdminList() {
		List<Integer> identity = new ArrayList<>();
		identity.add(3);
		identity.add(4);
		identity.add(5);
		AdminExample example = new AdminExample();
		cn.edu.qqhru.train.pojo.AdminExample.Criteria criteria = example.createCriteria();
		criteria.andIdentifyNotIn(identity).andLoginnameNotEqualTo("000000");
		List<Admin> list = adminMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<Classes> getClassesList() {
		ClassesExample example = new ClassesExample();
		cn.edu.qqhru.train.pojo.ClassesExample.Criteria criteria = example.createCriteria();
		criteria.andCapacityEqualTo(0);
		List<Classes> list = classesMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<Teacher> getTeacherList() {
		TeacherExample example = new TeacherExample();
		cn.edu.qqhru.train.pojo.TeacherExample.Criteria criteria = example.createCriteria();
		criteria.andIsfinishEqualTo(0);
		List<Teacher> list = teacherMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<Plan> getPlanByIds(int[] ids) {
		List<Integer> id = new ArrayList<>();
		for (int i : ids) {
			id.add(i);
		}
		PlanExample example = new PlanExample();
		Criteria criteria = example.createCriteria();
		criteria.andPlanIdIn(id);
		List<Plan> list = planMapper.selectByExample(example);
		return list;
	}

	@Override
	public boolean checkPname(String pname) {
		PlanExample example = new PlanExample();
		Criteria criteria = example.createCriteria();
		criteria.andPnameEqualTo(pname);
		List<Plan> list = planMapper.selectByExample(example);
		if (list.size()>0) {
			return true;
		}
		return false;
	}
	
	
}
