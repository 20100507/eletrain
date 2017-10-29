package cn.edu.qqhru.train.service;

import java.util.List;

import cn.edu.qqhru.train.vo.FunctionBean;

public interface FunctionService {
	
	public List<FunctionBean> getAuthFunctionByIdentify(String identify);
	
	public List<FunctionBean> getFunctionBeanById(String pid);
	
	public Object getIdentifyByUserAndPwd(String username,String password);
	
	public boolean modifyPwd(Object obj,String newPwd);
	
}
