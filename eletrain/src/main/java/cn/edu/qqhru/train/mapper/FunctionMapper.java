package cn.edu.qqhru.train.mapper;

import java.util.List;

import cn.edu.qqhru.train.vo.FunctionBean;

public interface FunctionMapper {
	
	public List<FunctionBean> getParFunctionBeanByCode(String code);
	
	public List<FunctionBean> getFunctionBeanById(String id);
}
