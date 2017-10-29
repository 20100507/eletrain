package cn.edu.qqhru.train.service;

import cn.edu.qqhru.train.pojo.Log;
import cn.edu.qqhru.train.utils.PageBean;

public interface LogService {

	public void recordLog(Object obj,String ip);

	public PageBean<Log> getLogList(int page,int rows);

	public PageBean<Log> getLog(String dname, int page, int rows);
	
	public void delLog(int[] logId);
}
