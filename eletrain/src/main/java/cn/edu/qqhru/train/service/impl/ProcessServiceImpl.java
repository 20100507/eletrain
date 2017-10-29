package cn.edu.qqhru.train.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.qqhru.train.mapper.AdminMapper;
import cn.edu.qqhru.train.mapper.ApplicationMapper;
import cn.edu.qqhru.train.mapper.ApproveinfoMapper;
import cn.edu.qqhru.train.mapper.PlanMapper;
import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.AdminExample;
import cn.edu.qqhru.train.pojo.Application;
import cn.edu.qqhru.train.pojo.ApplicationExample;
import cn.edu.qqhru.train.pojo.ApplicationExample.Criteria;
import cn.edu.qqhru.train.pojo.Approveinfo;
import cn.edu.qqhru.train.pojo.ApproveinfoExample;
import cn.edu.qqhru.train.pojo.Plan;
import cn.edu.qqhru.train.pojo.PlanExample;
import cn.edu.qqhru.train.service.ProcessService;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.ApplicationVo;
import cn.edu.qqhru.train.vo.ApproveInfoVo;
import cn.edu.qqhru.train.vo.PositionVo;
import cn.edu.qqhru.train.vo.TaskViewVo;

/**
 * 流程运行service
 * <p>Title: ProcessServiceImpl</p>
 * <p>Description: </p>
 * <p>School: qiqihar university</p> 
 * @author	BQ
 * @date	2017年9月11日上午12:01:37
 * @version 1.0
 */
@Service
public class ProcessServiceImpl implements ProcessService {
	
	@Autowired
	private ApplicationMapper applicationMapper;
	@Autowired
	private ApproveinfoMapper approveinfoMapper;
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private PlanMapper planMapper;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	
	/**
		查询个人的所有的流程列表
	 * <p>Title: list</p>
	 * <p>Description: </p>
	 * @param page
	 * @param rows
	 * @param admin
	 * @return
	 * @see cn.edu.qqhru.train.service.ProcessService#list(int, int, cn.edu.qqhru.train.pojo.Admin)
	 */
	public PageBean<ApplicationVo> list(int page, int rows,Admin admin) {
		PageBean<ApplicationVo> pageBean = new PageBean<>();
		Integer adminId = null;
		if(admin != null){
			 adminId = admin.getAdminId();
		}
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows,"apply_date desc");
		ApplicationExample applicationExample = new ApplicationExample();
		cn.edu.qqhru.train.pojo.ApplicationExample.Criteria createCriteria = applicationExample.createCriteria();
		if(admin != null){
			createCriteria.andUserIdEqualTo(adminId);
		}
		List<Application> list = applicationMapper.selectByExample(applicationExample);
		List<ApplicationVo> applicationVoList = new ArrayList<>(); 
		if(list != null && list.size() > 0){
			for (Application application : list) {
				ApplicationVo applicationVo = new ApplicationVo();
				String username = adminMapper.selectByPrimaryKey(application.getUserId()).getUsername();
				applicationVo.setApplicationId(application.getApplicationId());
				applicationVo.setApplyDate(application.getApplyDate());
				applicationVo.setStatus(application.getStatus());
				applicationVo.setPlanId(application.getPlanId());
				applicationVo.setTitle(application.getTitle());
				applicationVo.setUsername(username);
				applicationVo.setUserId(application.getUserId());
				applicationVo.setPdId(application.getPdId());
				applicationVoList.add(applicationVo);
			}	
		}
		PageInfo<ApplicationVo> pageInfo = new PageInfo<>(applicationVoList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(applicationVoList);
		return pageBean;
	}
	
	/**
	 * 查看流程图
	 * <p>Title: showPng</p>
	 * <p>Description: </p>
	 * @param pdId
	 * @return
	 * @see cn.edu.qqhru.train.service.ProcessService#showPng(java.lang.String)
	 */
	@Override
	public InputStream showPng(String pdId) {
		return repositoryService.getProcessDiagram(pdId);
	}

	@Override
	public PageBean<TaskViewVo> getProcessing(int page, int rows, Admin admin) {
		PageBean<TaskViewVo> pageBean = new PageBean<>();
		PageHelper.startPage(page, rows);
		TaskQuery query = taskService.createTaskQuery();
		query.taskAssignee(admin.getAdminId()+"");
		query.orderByTaskCreateTime().desc();
		List<Task> taskList = query.list();
		List<TaskViewVo> list = new ArrayList<>();
		for (Task task : taskList) {
			Integer userId = 0;
			String taskId = task.getId();
			Application application = (Application) taskService.getVariable(taskId, "application");
			ApplicationExample applicationExample = new ApplicationExample();
			Criteria createCriteria = applicationExample.createCriteria();
			createCriteria.andApplicationIdEqualTo(application.getApplicationId());
			List<Application> appList = applicationMapper.selectByExample(applicationExample);
			if(appList != null &&  appList.size() > 0){
				Application app = appList.get(0);
				userId = app.getUserId();
			}
			ApplicationVo applicationVo = new ApplicationVo();
			applicationVo.setApplicationId(application.getApplicationId());
			applicationVo.setApplyDate(application.getApplyDate());
			applicationVo.setPdId(application.getPdId());
			applicationVo.setPlanId(application.getPlanId());
			applicationVo.setStatus(application.getStatus());
			applicationVo.setTitle(application.getTitle());
			applicationVo.setUserId(application.getUserId());
			applicationVo.setUsername(adminMapper.selectByPrimaryKey(userId).getUsername());
			TaskViewVo taskViewVo = new TaskViewVo(task, applicationVo);
			list.add(taskViewVo);
		}
		PageInfo<TaskViewVo> pageInfo = new PageInfo<>(list);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public Application getFlowOne(int id) {
		Application app = null;
		ApplicationExample applicationExample = new ApplicationExample();
		Criteria createCriteria = applicationExample.createCriteria();
		createCriteria.andApplicationIdEqualTo(id);
		List<Application> appList = applicationMapper.selectByExample(applicationExample);
		if(appList != null &&  appList.size() > 0){
			app = appList.get(0);
		}
		return app;
	}

	@Override
	public boolean processFlow(Approveinfo approveinfo, String taskId,Admin admin) {
		try{
			Approveinfo appInfo = new Approveinfo();
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			//先把我们的个关联的流程表的 流程详情表数据  插入到数据库中PS 主键自增长
			appInfo.setApplicationId(approveinfo.getApplicationId());
			appInfo.setApproval(approveinfo.getApproval());
			appInfo.setComment(approveinfo.getComment());
			appInfo.setApprovedate(new Date());
			appInfo.setAdminId(approveinfo.getAdminId());
			approveinfoMapper.insert(appInfo);
			//流程向下执行一步 我就改变这个assignee
			//获取流程实例ID
			String processInstanceId = task.getProcessInstanceId();
			Map<String, Object> variables = new HashMap<String, Object>();
			Application app = (Application) taskService.getVariable(taskId, "application");
			Integer identify = admin.getIdentify();
			int ident = identify.intValue()+1;
			app.setUserId(ident);
			variables.put("application",app);
			taskService.complete(taskId,variables);
			//查询当前流程实例是否存在！
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
			    .processInstanceId(processInstanceId).singleResult();
			if(appInfo.getApproval()){
				if(processInstance == null){
					Application application = null;
					Plan plan = null;
					int planId;
					ApplicationExample applicationExample = new ApplicationExample();
					Criteria createCriteria = applicationExample.createCriteria();
					createCriteria.andApplicationIdEqualTo(approveinfo.getApplicationId());
					List<Application> applicationList = applicationMapper.selectByExample(applicationExample);
					if(applicationList != null && applicationList.size() > 0){
						application = applicationList.get(0);
					}
					// 流程表 设置审批状态 审批列表中的0.未提交 1.审批中 2.未通过 3.通过
					application.setStatus(3);
					applicationMapper.updateByExample(application, applicationExample);
					// 方案表 
					planId = application.getPlanId();
					PlanExample planExample = new PlanExample();
					cn.edu.qqhru.train.pojo.PlanExample.Criteria planCriteria = planExample.createCriteria();
					planCriteria.andPlanIdEqualTo(planId);
					List<Plan> planList = planMapper.selectByExample(planExample);
					if(planList != null && planList.size() > 0){
						plan = planList.get(0);
					}
					//继续修改plan中的标记 0.未提交 1.审批中 2.未通过 3.通过
					plan.setSign(3);
					planMapper.updateByExample(plan, planExample);
				}
			}else{
				Application application = null;
				Plan plan = null;
				int planId;
				ApplicationExample applicationExample = new ApplicationExample();
				Criteria createCriteria = applicationExample.createCriteria();
				createCriteria.andApplicationIdEqualTo(approveinfo.getApplicationId());
				List<Application> applicationList = applicationMapper.selectByExample(applicationExample);
				if(applicationList != null && applicationList.size() > 0){
					application = applicationList.get(0);
				}
				// 流程表 设置审批状态 审批列表中的  0.未提交 1.审批中 2.未通过 3.通过
				application.setStatus(2);
				applicationMapper.updateByExample(application, applicationExample);
				// 方案表 
				planId = application.getPlanId();
				PlanExample planExample = new PlanExample();
				cn.edu.qqhru.train.pojo.PlanExample.Criteria planCriteria = planExample.createCriteria();
				planCriteria.andPlanIdEqualTo(planId);
				List<Plan> planList = planMapper.selectByExample(planExample);
				if(planList != null && planList.size() > 0){
					plan = planList.get(0);
				}
				//继续修改plan中的标记 0.未提交 1.审批中 2.未通过 3.通过
				plan.setSign(2);
				planMapper.updateByExample(plan, planExample);
				if(processInstance != null){
					runtimeService
					.deleteProcessInstance(processInstanceId, "未通过");
				}
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}

	/**
		根据applicationId查询任务
	 * <p>Title: findTaskByApplicationId</p>
	 * <p>Description: </p>
	 * @param applicationId
	 * @return
	 * @see cn.edu.qqhru.train.service.ProcessService#findTaskByApplicationId(java.lang.Integer)
	 */
	@Override
	public Task findTaskByApplicationId(Integer applicationId) {
		TaskQuery query = taskService.createTaskQuery();
		query.processVariableValueEquals("applicationId", applicationId);
		return query.singleResult();
	}

	@Override
	public PositionVo findCoordingByTask(Task task) {
			PositionVo positionVo = new PositionVo();
		    // 获得流程定义id
			String processDefinitionId = task.getProcessDefinitionId();
			// 获得流程实例id
			String processInstanceId = task.getProcessInstanceId();
			// 返回的流程定义对象中包含坐标信息
			ProcessDefinitionEntity pd = (ProcessDefinitionEntity) 
					repositoryService.getProcessDefinition(
							processDefinitionId);
			ProcessInstanceQuery query = runtimeService
					.createProcessInstanceQuery();
			// 根据流程实例id过滤
			query.processInstanceId(processInstanceId);
			ProcessInstance processInstance = query.singleResult();
			// 根据流程实例对象获得当前的获得节点
			String activityId = processInstance.getActivityId();// usertask2
			ActivityImpl activityImpl = pd.findActivity(activityId);
			int x = activityImpl.getX();
			int y = activityImpl.getY();
			int height = activityImpl.getHeight();
			int width = activityImpl.getWidth();
			positionVo.setX(x);
			positionVo.setY(y);
			positionVo.setHeight(height);
			positionVo.setWidth(width);
			return positionVo;
	}
	
	/**
	 * 根据任务查询流程定义
	 * <p>Title: findPDByTask</p>
	 * <p>Description: </p>
	 * @param task
	 * @return
	 * @see cn.edu.qqhru.train.service.ProcessService#findPDByTask(org.activiti.engine.task.Task)
	 */
	@Override
	public ProcessDefinition findPDByTask(Task task) {
		String processDefinitionId = task.getProcessDefinitionId();
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery();
		query.processDefinitionId(processDefinitionId);
		return query.singleResult();
	}

	/**
	 * 根据APPID 查询流程扭转
	 * <p>Title: findProcessInfoByAppId</p>
	 * <p>Description: </p>
	 * @param appId
	 * @return
	 * @see cn.edu.qqhru.train.service.ProcessService#findProcessInfoByAppId(int)
	 */
	@Override
	public PageBean<ApproveInfoVo> findProcessInfoByAppId(int appId) {
		PageBean<ApproveInfoVo> pageBean = new PageBean<>();
		ApproveinfoExample approveinfoExample = new ApproveinfoExample();
		cn.edu.qqhru.train.pojo.ApproveinfoExample.Criteria createCriteria = approveinfoExample.createCriteria();
		createCriteria.andApplicationIdEqualTo(appId);
		List<Approveinfo> approveList = approveinfoMapper.selectByExample(approveinfoExample);
		List<ApproveInfoVo> approveInfoVos = new CopyOnWriteArrayList<>();
		if(approveList != null && approveList.size() > 0){
			for (Approveinfo approveinfo : approveList) {
				ApproveInfoVo approveInfoVo = new ApproveInfoVo();
				approveinfo.setAdminId(approveinfo.getAdminId());
				approveInfoVo.setApproveinfoId(approveinfo.getApproveinfoId());
				approveInfoVo.setApplicationId(approveinfo.getApplicationId());
				approveInfoVo.setApproval(approveinfo.getApproval());
				approveInfoVo.setApprovedate(approveinfo.getApprovedate());
				approveInfoVo.setComment(approveinfo.getComment());
				String username = getUsernameById(approveinfo.getAdminId());
				approveInfoVo.setUsername(username);
				approveInfoVos.add(approveInfoVo);
			}
		}
		PageInfo<ApproveInfoVo> pageInfo = new PageInfo<>(approveInfoVos);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(approveInfoVos);
		return pageBean;
	}
	
	/**
	 * 通过ID查询用户名
	 * <p>Title: getUsernameById</p>
	 * <p>Description: </p>
	 * @param id
	 * @return
	 */
	private String getUsernameById(Integer id){
		AdminExample adminExample = new AdminExample();
		Admin admin = null;
		cn.edu.qqhru.train.pojo.AdminExample.Criteria adminCriteria = adminExample.createCriteria();
		adminCriteria.andAdminIdEqualTo(id);
		List<Admin> adminList = adminMapper.selectByExample(adminExample);
		if(adminList != null && adminList.size() > 0){
			 admin = adminList.get(0);
		}
		return admin.getUsername();
	}

	@Override
	public PageBean<ApplicationVo> getPlanByName(String processName, int page, int rows) {
		if(processName.equals("")){
			PageBean<ApplicationVo> pageBean = new PageBean<>();
			// 设置页数，和显示多少条记录
			PageHelper.startPage(page, rows);
			ApplicationExample applicationExample = new ApplicationExample();
			Plan plan = null;
			PlanExample planExample = new PlanExample();
			cn.edu.qqhru.train.pojo.PlanExample.Criteria planCriteria = planExample.createCriteria();
			planCriteria.andPnameEqualTo(processName);
			List<Plan> planList = planMapper.selectByExample(planExample);
			List<ApplicationVo> applicationVoList = new ArrayList<>(); 
			if(planList != null && planList.size() > 0){
				plan = planList.get(0);
			}
			cn.edu.qqhru.train.pojo.ApplicationExample.Criteria createCriteria = applicationExample.createCriteria();
			if(processName != null && plan != null){
				createCriteria.andPlanIdEqualTo(plan.getPlanId());
			}
			List<Application> list = applicationMapper.selectByExample(applicationExample);
			if(list != null && list.size() > 0){
				for (Application application : list) {
					ApplicationVo applicationVo = new ApplicationVo();
					String username = adminMapper.selectByPrimaryKey(application.getUserId()).getUsername();
					applicationVo.setApplicationId(application.getApplicationId());
					applicationVo.setApplyDate(application.getApplyDate());
					applicationVo.setStatus(application.getStatus());
					applicationVo.setPlanId(application.getPlanId());
					applicationVo.setTitle(application.getTitle());
					applicationVo.setUsername(username);
					applicationVo.setUserId(application.getUserId());
					applicationVo.setPdId(application.getPdId());
					applicationVoList.add(applicationVo);
				}	
			}
			PageInfo<ApplicationVo> pageInfo = new PageInfo<>(applicationVoList);
			long totalPage = pageInfo.getPages();
			int pageNum = pageInfo.getPageNum();
			pageBean.setPage(pageNum);
			pageBean.setTotalPage(totalPage);
			pageBean.setList(applicationVoList);
			return pageBean;
		}
		PageBean<ApplicationVo> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		ApplicationExample applicationExample = new ApplicationExample();
		Plan plan = null;
		PlanExample planExample = new PlanExample();
		cn.edu.qqhru.train.pojo.PlanExample.Criteria planCriteria = planExample.createCriteria();
		planCriteria.andPnameEqualTo(processName);
		List<Plan> planList = planMapper.selectByExample(planExample);
		List<ApplicationVo> applicationVoList = new ArrayList<>(); 
		if(planList != null && planList.size() > 0){
			plan = planList.get(0);
		}
		cn.edu.qqhru.train.pojo.ApplicationExample.Criteria createCriteria = applicationExample.createCriteria();
		if(processName != null && plan != null){
			createCriteria.andPlanIdEqualTo(plan.getPlanId());
		}
		if(plan == null){
			applicationVoList = new ArrayList<ApplicationVo>();;
			PageInfo<ApplicationVo> pageInfo = new PageInfo<>(applicationVoList);
			long totalPage = pageInfo.getPages();
			int pageNum = pageInfo.getPageNum();
			pageBean.setPage(pageNum);
			pageBean.setTotalPage(totalPage);
			pageBean.setList(applicationVoList);
			return pageBean;
		}
		
		List<Application> list = applicationMapper.selectByExample(applicationExample);
		if(list != null && list.size() > 0){
			for (Application application : list) {
				ApplicationVo applicationVo = new ApplicationVo();
				String username = adminMapper.selectByPrimaryKey(application.getUserId()).getUsername();
				applicationVo.setApplicationId(application.getApplicationId());
				applicationVo.setApplyDate(application.getApplyDate());
				applicationVo.setStatus(application.getStatus());
				applicationVo.setPlanId(application.getPlanId());
				applicationVo.setTitle(application.getTitle());
				applicationVo.setUsername(username);
				applicationVo.setUserId(application.getUserId());
				applicationVo.setPdId(application.getPdId());
				applicationVoList.add(applicationVo);
			}	
		}
		PageInfo<ApplicationVo> pageInfo = new PageInfo<>(applicationVoList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(applicationVoList);
		return pageBean;
	}
	
	@Override
	public PageBean<TaskViewVo> getProcessByName(String planName,Admin admin,int page, int rows) {
		PageBean<TaskViewVo> pageBean = new PageBean<>();
		PageHelper.startPage(page, rows);
		TaskQuery query = taskService.createTaskQuery();
		query.taskAssignee(admin.getAdminId()+"");
		query.orderByTaskCreateTime().desc();
		List<Task> taskList = query.list();
		List<TaskViewVo> list = new ArrayList<>();
		for (Task task : taskList) {
			Plan plan = null;
			Integer userId = 0;
			String taskId = task.getId();
			Application application = (Application) taskService.getVariable(taskId, "application");
			PlanExample planExample = new PlanExample();
			cn.edu.qqhru.train.pojo.PlanExample.Criteria planCriteria = planExample.createCriteria();
			planCriteria.andPnameEqualTo(planName);
			List<Plan> planList = planMapper.selectByExample(planExample);
			if(planList != null && planList.size() > 0){
				plan = planList.get(0);
			}
			ApplicationExample applicationExample = new ApplicationExample();
			Criteria createCriteria = applicationExample.createCriteria();
			if(application != null){
				createCriteria.andApplicationIdEqualTo(application.getApplicationId());
			}
			if(plan != null){
				createCriteria.andPlanIdEqualTo(plan.getPlanId());
			}
			List<Application> appList = applicationMapper.selectByExample(applicationExample);
			if(appList != null &&  appList.size() > 0){
				Application app = appList.get(0);
				userId = app.getUserId();
			}
			ApplicationVo applicationVo = new ApplicationVo();
			applicationVo.setApplicationId(application.getApplicationId());
			applicationVo.setApplyDate(application.getApplyDate());
			applicationVo.setPdId(application.getPdId());
			applicationVo.setPlanId(application.getPlanId());
			applicationVo.setStatus(application.getStatus());
			applicationVo.setTitle(application.getTitle());
			applicationVo.setUserId(application.getUserId());
			if(userId != null){
				applicationVo.setUsername(adminMapper.selectByPrimaryKey(userId).getUsername());
			}
			TaskViewVo taskViewVo = new TaskViewVo(task, applicationVo);
			list.add(taskViewVo);
		}
		PageInfo<TaskViewVo> pageInfo = new PageInfo<>(list);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		return pageBean;
	}
}












