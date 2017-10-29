package cn.edu.qqhru.train.service;

import java.io.InputStream;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.Application;
import cn.edu.qqhru.train.pojo.Approveinfo;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.ApplicationVo;
import cn.edu.qqhru.train.vo.ApproveInfoVo;
import cn.edu.qqhru.train.vo.PositionVo;
import cn.edu.qqhru.train.vo.TaskViewVo;

public interface ProcessService {
	
	public PageBean<ApplicationVo> list(int page, int rows,Admin admin);
	
	public InputStream showPng(String pdId);
	
	public PageBean<TaskViewVo> getProcessing(int page,int rows,Admin admin);

	public Application getFlowOne(int id);

	public boolean processFlow(Approveinfo approveinfo, String taskId,Admin admin);
	
	public Task findTaskByApplicationId(Integer applicationId);
	
	public PositionVo findCoordingByTask(Task task);
	
	public ProcessDefinition findPDByTask(Task task);

	public PageBean<ApproveInfoVo> findProcessInfoByAppId(int appId);

	public PageBean<ApplicationVo> getPlanByName(String processName, int page, int rows);
	
	public PageBean<TaskViewVo> getProcessByName(String planName,Admin admin,int page, int rows);
}
