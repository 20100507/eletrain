package cn.edu.qqhru.train.vo;

import org.activiti.engine.task.Task;

import cn.edu.qqhru.train.pojo.Application;

/**
 * 包装任务信息和申请信息
 * <p>Title: TaskViewVo</p>
 * <p>Description: </p>
 * <p>School: qiqihar university</p> 
 * @author	BQ
 * @date	2017年9月11日下午11:48:03
 * @version 1.0
 */
public class TaskViewVo {
	
	private Task task;
	private ApplicationVo applicationVo;
	
	public TaskViewVo() {}
	
	public TaskViewVo(Task task, ApplicationVo applicationVo) {
		this.task = task;
		this.applicationVo = applicationVo;
	}

	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}

	public ApplicationVo getApplicationVo() {
		return applicationVo;
	}

	public void setApplicationVo(ApplicationVo applicationVo) {
		this.applicationVo = applicationVo;
	}
}
