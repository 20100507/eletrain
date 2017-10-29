package cn.edu.qqhru.train.service;

import java.util.List;

import cn.edu.qqhru.train.pojo.Course;
import cn.edu.qqhru.train.pojo.Syllabus;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.CourseDateVo;

public interface SyllabusService {
	
	PageBean<CourseDateVo> getAllSyllabus(int page, int rows);

	void addSyllabus(Syllabus syllabus);

	Syllabus getSyllabusIdBYid(Integer syllabusId);

	void delSyllabusByIds(Integer[] ids);

	void updateSyllabus(Syllabus syllabus);

	List<CourseDateVo> getAllsyllabus();

	List<Syllabus> getSyllabusByClassesId(Integer classesId);

	List<Syllabus> getSyllabusByDate(String week);

	List<Course> getCourseByNotCourseIds(List<Integer> courseIds);

	PageBean<CourseDateVo> getSyllabusByTeacher(Integer teacherId, int page, int rows);

	List<CourseDateVo> getSyllabusByTeacher(Integer teacherId);

	PageBean<CourseDateVo> getPackagedSyllabusByDate(String index, int page, int rows);

	PageBean<CourseDateVo> getPackagedSyllabusByClasses(String index, int page, int rows);

	PageBean<CourseDateVo> getPackagedSyllabusByTeacherUsername(String index, int page, int rows);

	PageBean<CourseDateVo> getPackagedSyllabusByClassesTeacher(List<Integer> classesIds, String week,
			List<Integer> asList, int page, int rows);

	PageBean<CourseDateVo> getPackagedSyllabusByDate(List<Integer> classesIds, String index, int page, int rows);

	PageBean<CourseDateVo> getPackagedSyllabusByClassesName(List<Integer> classesIds2, int page, int rows);

}
