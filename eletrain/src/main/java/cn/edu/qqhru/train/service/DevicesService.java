package cn.edu.qqhru.train.service;

import java.util.List;

import cn.edu.qqhru.train.pojo.Device;
import cn.edu.qqhru.train.utils.PageBean;

public interface DevicesService {
	// 获取所有设备信息,分页
	PageBean<Device> getAllDev(int page, int rows);
	// 删除设备信息
	void delDev(int [] ids);
	// 添加一条设备信息
	void addDev(Device dev);
	// 根据id获取设备信息
	Device getDevOne(int id);
	// 修改设备信息
	void editDev(Device dev);
	// 查询指定设备
	PageBean<Device> getDev(String dname,int page,int rows);
	// 批量导入设备信息
	void importDev(List<Device> devList);
	// 获取所有设备信息，导出
	List<Device> getExportDev();
}
