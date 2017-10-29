package cn.edu.qqhru.train.service;

import java.util.List;

import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.ClassesTeacherKey;
import cn.edu.qqhru.train.pojo.Course;
import cn.edu.qqhru.train.pojo.Student;
import cn.edu.qqhru.train.pojo.Syllabus;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.CourseDateVo;

public interface ClassesService {
	
	PageBean<Classes> getAllClasses(int page, int rows,int type);

	List<Classes> getAllClasses();
	
	void addClasses(Classes classes,Integer[] teacherIds, String startTime, String endTime);

	void deleteClasses(Integer classesId);

	Classes getClassesBYid(Integer classesId);

	void updateClasses(Classes classes, Integer[] teacherIds, String startTime, String endTime);

	void delClassesByIds(Integer[] ids);

	void addOrDelStudentToClasses(Integer classesId, Integer[] ids,Integer type);

	List<Teacher> getTeacherByCid(Integer classesId);

	List<Student> getStudentByCid(Integer classesId);

	Integer getCountByCid(Integer classesId);
	//移动到CourseService中
	Course getCourseByCourseId(Integer courseId);

	List<Syllabus> getSyllabusByClassesId(Integer classesId);

	PageBean<Classes> getClassesByClassesNoAndZhangTai(int page,int rows,String index,Integer zhuangtai);

	PageBean<Classes> getClassesByClassesNameAndZhangTai(int page,int rows,String index,Integer zhuangtai);

	Classes getClassesByClassesIdAndZhangTai(Integer classesId, Integer zhuangtai);
	
	List<ClassesTeacherKey> getClassesTeacherListByTeacherId(int teacherId);

	List<Classes> getClassesByTeacherId(Integer teacherId);
	
	List<CourseDateVo> getPackageSyllabusByclassesId(Integer calssesId);

	PageBean<Classes> getClassesByClassesNo(int page, int rows, String index);

	PageBean<Classes> getClassesByClassesName(int page, int rows, String index);

	List<Classes> getClassesByclassesIds(List<Integer> classesIds);

	void updClassesByIds(Integer[] classesIds);

	void updClasses(Integer classesId);

	PageBean<Classes> getClassesByLectureTeacherId(Integer teacherId, int page, int rows);
	
	List<Classes> getClassesByLectureTeacherIdNoPage(Integer teacherId);
	
	List<Classes> getClassesByClassesTeacherId(Integer teacherId);

	PageBean<Student> getAllExecuteStu(int page, int rows, int rows2);

	PageBean<Student> getAllStuByClassesId(Integer classesId, int page, int rows);

	List<Classes> getAllClassesByClassesTeacher(Integer teacherId);

	void deleteStudent(List<Integer> classesIds);

	void updStudent(List<Integer> classesIds);

	PageBean<Classes> getClassesByClassesTeacherId(int page, int rows, String index,Integer searchWay, Integer teacherId,Integer type);

	PageBean<Syllabus> getSyllabusByClassesIds(List<Integer> classesIds, int page, int rows);

	List<CourseDateVo> getSyllabusByClassesIds(List<Integer> classesIds);

	List<Classes> getClassesByClassesName(String index);

}
