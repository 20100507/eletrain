package cn.edu.qqhru.train.service;

import java.util.ArrayList;
import java.util.List;

import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.utils.PageBean;

public interface TeacherService {
	
	PageBean<Teacher> getAllTeacher(int page, int rows,int type);

	void addTeacher(Teacher teacher);

	void deleteTeacher(Integer teacherId);

	Teacher getTeacherBYid(Integer teacherId);

	void updateTeacher(Teacher teacher);

	void delTeacherByIds(Integer[] ids);

	void init(Integer teacherId);

	Teacher lookInfo(Integer index);

//	void importStu(List<Teacher> teacherList);
	
	List<Teacher> getAllTeacher();
	
	Teacher getOneTeacherByCname(String username);
	
	@Deprecated
	Teacher searchTeracher(String index);

	List<Teacher> getTeacherByClassesId(Integer classesId);

	List<Teacher> getTeacherByCardIdAndState(String index, Integer state);

	List<Teacher> getTeacherByUsernameAndState(String index, Integer state);

	PageBean<Teacher> getAllWorkTeacher(int page, int rows);

	PageBean<Teacher> getTeacherByUsername(String index, int page, int rows);

	PageBean<Teacher> getTeacherByCardId(String index, int page, int rows);

	PageBean<Teacher> getTeacherByLoginname(String index, int page, int rows);

	void updTeacherByIds(Integer[] teacherIds);

	void importreSignTeacher(List<Teacher> teacherList);

	void importWorkTeacher(List<Teacher> teacherList);

	List<Teacher> getAllWorkTeacher(String type);

	List<Teacher> getClassesTeacher();

	List<Teacher> getTeacherByteacherIds(List<Integer> teacherIds);

	List<Teacher> getTeacherByUsername(String index);

	boolean checkLoginName(String replace);

}
