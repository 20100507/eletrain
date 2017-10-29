package cn.edu.qqhru.train.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.qqhru.train.mapper.AdminMapper;
import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.pojo.AdminExample;
import cn.edu.qqhru.train.service.AdminService;
import cn.edu.qqhru.train.utils.MD5Utils;
import cn.edu.qqhru.train.utils.PageBean;

@Service("adminService")
public class AdminServiceImpl implements AdminService{
	@Autowired
	private AdminMapper adminMapper;
	
	private AdminExample adminExample;
	
	@Override
	public List<Admin> getAdminList(){
		adminExample = new AdminExample();
		adminExample.createCriteria().andAdminIdIsNotNull();
		List<Admin> adminList = adminMapper.selectByExample(adminExample);
		return adminList;
	}
	
	@Override
	public PageBean<Admin> getAdminListPageBean(int page, int rows) {
		PageBean<Admin> adminListPageBean = new PageBean<Admin>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		adminExample = new AdminExample();
		adminExample.createCriteria().andAdminIdIsNotNull();
		List<Admin> adminList = adminMapper.selectByExample(adminExample);
		PageInfo<Admin> pageInfo = new PageInfo<Admin>(adminList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		adminListPageBean.setPage(pageNum);
		adminListPageBean.setTotalPage(totalPage);
		adminListPageBean.setList(adminList);
		return adminListPageBean;
	}
	
	@Override
	public PageBean<Admin> getAdminListByPageAndCondition(String condition, int page, int rows) {
		PageBean<Admin> adminListPageBean = new PageBean<Admin>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		adminExample = new AdminExample();
		adminExample.createCriteria().andAdminIdIsNotNull().andLoginnameEqualTo(condition);
		List<Admin> adminList = adminMapper.selectByExample(adminExample);
		PageInfo<Admin> pageInfo = new PageInfo<Admin>(adminList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		adminListPageBean.setPage(pageNum);
		adminListPageBean.setTotalPage(totalPage);
		adminListPageBean.setList(adminList);
		return adminListPageBean;
	}

	@Override
	public boolean addAdmin(Admin admin) {
		admin.setPassword(admin.getPassword());
		int num = adminMapper.insert(admin);
		if(num == 1){
			return true;
		}
		return false;
	}

	@Override
	public Admin getAdmin(Integer id) {
		Admin admin = adminMapper.selectByPrimaryKey(id);
		return admin;
	}

	@Override
	public boolean updateAdmin(Admin admin){
		int num = adminMapper.updateByPrimaryKeySelective(admin);
		if(num == 1){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteAdmin(Integer id){
		int num = adminMapper.deleteByPrimaryKey(id);
		if(num == 1){
			return true;
		}
		return false;
	}

	@Override
	public void deleteMultipleAdmins(Integer[] ids) {
		for(int i=0; i<ids.length; i++){
			adminMapper.deleteByPrimaryKey(ids[i]);
		}
	}

	@Override
	public boolean initializePassword(String id) {
		Admin admin = new Admin();
		admin.setAdminId(Integer.parseInt(id));
		admin.setPassword("123456");
		int num = adminMapper.updateByPrimaryKeySelective(admin);
		if(num == 1){
			return true;
		}
		return false;
	}

	@Override
	public boolean addMultipleAdmins(List<Admin> adminList){
		boolean flag = true;
		for(Admin admin : adminList){
			int num = adminMapper.insert(admin);
			if(num != 1){
				flag = false;
			}
			
		}
		
		return flag;
	}

	@Override
	public Admin getAdminByUsername(String condition) {
		adminExample = new AdminExample();
		adminExample.createCriteria().andUsernameEqualTo(condition);
		List<Admin> adminList = adminMapper.selectByExample(adminExample);
		if(adminList != null && adminList.size() > 0) {
			return adminList.get(0);
		}else {
			return null;
		}
	}
}
