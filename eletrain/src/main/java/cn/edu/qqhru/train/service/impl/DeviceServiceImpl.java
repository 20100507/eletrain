package cn.edu.qqhru.train.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.qqhru.train.mapper.DeviceMapper;
import cn.edu.qqhru.train.mapper.PlanDevicesMapper;
import cn.edu.qqhru.train.pojo.Device;
import cn.edu.qqhru.train.pojo.DeviceExample;
import cn.edu.qqhru.train.pojo.DeviceExample.Criteria;
import cn.edu.qqhru.train.pojo.PlanDevicesExample;
import cn.edu.qqhru.train.service.DevicesService;
import cn.edu.qqhru.train.utils.PageBean;
@Service
public class DeviceServiceImpl implements DevicesService {
	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private PlanDevicesMapper planDevicesMapper;

	@Override
	public PageBean<Device> getAllDev(int page, int rows) {
		PageBean<Device> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows,"device_id desc");
		DeviceExample example = new DeviceExample();
		Criteria criteria = example.createCriteria();
		criteria.andDeviceIdIsNotNull();
		List<Device> list = deviceMapper.selectByExample(example);
		PageInfo<Device> pageInfo = new PageInfo<>(list);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		return pageBean;
	}
	/**
	 * 删除设备
	 */
	@Override
	public void delDev(int[] ids) {
		DeviceExample example = new DeviceExample();
		List<Integer> id = new ArrayList<>();
		for (int i : ids) {
			id.add(i);
		}
		Criteria criteria = example.createCriteria();
		criteria.andDeviceIdIn(id);
		deviceMapper.deleteByExample(example);	
		PlanDevicesExample example2 = new PlanDevicesExample();
		example2.createCriteria().andDeviceIdIn(id);
		planDevicesMapper.deleteByExample(example2);
	}
	/**
	 * 增加设备
	 */
	@Override
	public void addDev(Device dev) {
		deviceMapper.insert(dev);
	}
	@Override
	public Device getDevOne(int id) {
		Device device = deviceMapper.selectByPrimaryKey(id);
		return device;
	}
	@Override
	public void editDev(Device dev) {
		deviceMapper.updateByPrimaryKey(dev);
	}
	/**
	 * 获取设备
	 */
	@Override
	public PageBean<Device> getDev(String dname, int page, int rows) {
		PageBean<Device> pageBean = new PageBean<>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows,"device_id desc");
		DeviceExample example = new DeviceExample();
		Criteria criteria = example.createCriteria();
		criteria.andDnameLike("%" + dname + "%");
		List<Device> list = deviceMapper.selectByExample(example);
		PageInfo<Device> pageInfo = new PageInfo<>(list);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		pageBean.setPage(pageNum);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		return pageBean;
	}
	@Override
	public void importDev(List<Device> devList) {
		for (Device device : devList) {
			deviceMapper.insert(device);
		}		
	}
	@Override
	public List<Device> getExportDev() {
		DeviceExample example = new DeviceExample();
		Criteria criteria = example.createCriteria();
		criteria.andDeviceIdIsNotNull();
		List<Device> list = deviceMapper.selectByExample(example);
		return list;
	}

}
