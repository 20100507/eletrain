package cn.edu.qqhru.train.service;

import java.util.List;

import cn.edu.qqhru.train.pojo.Admin;
import cn.edu.qqhru.train.utils.PageBean;

public interface AdminService {

	List<Admin> getAdminList();

	boolean addAdmin(Admin admin);

	Admin getAdmin(Integer id);

	boolean updateAdmin(Admin admin);

	boolean deleteAdmin(Integer id);

	void deleteMultipleAdmins(Integer[] ids);

	PageBean<Admin> getAdminListPageBean(int page, int rows);

	PageBean<Admin> getAdminListByPageAndCondition(String id, int page, int rows);

	boolean initializePassword(String id);

	boolean addMultipleAdmins(List<Admin> adminList);

	Admin getAdminByUsername(String condition);

}
