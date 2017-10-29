package cn.edu.qqhru.train.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.qqhru.train.mapper.LogMapper;
import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.Device;
import cn.edu.qqhru.train.pojo.DeviceExample;
import cn.edu.qqhru.train.pojo.Log;
import cn.edu.qqhru.train.pojo.LogExample;
import cn.edu.qqhru.train.pojo.PlanDevicesExample;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.pojo.DeviceExample.Criteria;
import cn.edu.qqhru.train.service.LogService;
import cn.edu.qqhru.train.utils.PageBean;

/**
 * 登录日志记录
 * <p>Title: LogServiceImpl</p>
 * <p>Description: </p>
 * <p>School: qiqihar university</p> 
 * @author	BQ
 * @date	2017年9月13日下午1:07:51
 * @version 1.0
 */
@Service
public class LogServiceImpl implements LogService {
	
	@Autowired
	private LogMapper logMapper;
	
	@Override
	public void recordLog(Object obj,String ip) {
		Admin admin = null;
		Teacher teacher = null;
		if(obj instanceof Admin){
			admin = (Admin) obj;
			Log log = new Log();
			log.setUsername(admin.getUsername());
			log.setIp(ip);
			log.setLogintime(new Date());
			logMapper.insert(log);
		}	
		if(obj instanceof Teacher){
			teacher = (Teacher) obj;
			Log log = new Log();
			log.setUsername(teacher.getUsername());
			log.setIp(ip);
			log.setLogintime(new Date());
			logMapper.insert(log);
		}
	}
   
	/**
	     分页记录   登录记录
	 * <p>Title: getLogList</p>
	 * <p>Description: </p>
	 * @return
	 * @see cn.edu.qqhru.train.service.LogService#getLogList()
	 */
	@Override
	public PageBean<Log> getLogList(int page,int rows) {
		PageBean<Log> pageBean = new PageBean<>();
		PageHelper.startPage(page, rows,"log_id desc");
		LogExample example = new LogExample();
		List<Log> logList = logMapper.selectByExample(example);
		PageInfo<Log> pageInfo = new PageInfo<>(logList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(logList);
		return pageBean;
	}


	
	@Override
	public void delLog(int[] logId) {
		LogExample example = new LogExample();
		List<Integer> id = new ArrayList<>();
		for (int i : logId) {
			id.add(i);
		}
		cn.edu.qqhru.train.pojo.LogExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andLogIdIn(id);
		logMapper.deleteByExample(example);	
	}

	@Override
	public PageBean<Log> getLog(String username, int page, int rows) {
		PageBean<Log> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows,"log_id desc");
		LogExample example = new LogExample();
		cn.edu.qqhru.train.pojo.LogExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andUsernameLike("%" + username + "%");
		List<Log> logList = logMapper.selectByExample(example);
		PageInfo<Log> pageInfo = new PageInfo<>(logList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(logList);
		return pageBean;
	}
	
}











