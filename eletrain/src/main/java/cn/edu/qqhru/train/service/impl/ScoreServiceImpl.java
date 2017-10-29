package cn.edu.qqhru.train.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.qqhru.train.mapper.ClassesTeacherMapper;
import cn.edu.qqhru.train.mapper.ScoreMapper;
import cn.edu.qqhru.train.mapper.TeacherMapper;
import cn.edu.qqhru.train.pojo.Classes;
import cn.edu.qqhru.train.pojo.ClassesTeacherExample;
import cn.edu.qqhru.train.pojo.ClassesTeacherKey;
import cn.edu.qqhru.train.pojo.Notice;
import cn.edu.qqhru.train.pojo.NoticeExample;
import cn.edu.qqhru.train.pojo.Score;
import cn.edu.qqhru.train.pojo.ScoreExample;
import cn.edu.qqhru.train.pojo.Student;
import cn.edu.qqhru.train.pojo.Teacher;
import cn.edu.qqhru.train.pojo.TeacherExample;
import cn.edu.qqhru.train.service.ScoreService;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.NoticeVo;
import cn.edu.qqhru.train.vo.ScoreVo;

@Service("scoreService")
public class ScoreServiceImpl implements ScoreService{
	@Autowired
	private ScoreMapper scoreMapper;
	
	@Autowired
	private TeacherMapper teacherMapper;
	
	@Autowired
	private ClassesTeacherMapper classesTeacherMapper ;
	
	private ScoreExample scoreExample;
	
	private TeacherExample teacherExample;
	
	private ClassesTeacherExample classesTeacherExample;

	@Override
	public PageBean<ScoreVo> getScoreListPageBean(int page, int rows, int TeacherId, List<Integer> lectureTeacherClassesIdList ,int identity){
		PageBean<ScoreVo> scoreListPageBean = new PageBean<ScoreVo>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		scoreExample = new ScoreExample();
		List<Integer> classessIdList = new ArrayList<Integer>();
		if(identity == 1){
			scoreExample.createCriteria().andScoreIdIsNotNull();
		}else if(identity == 6){
			classesTeacherExample = new ClassesTeacherExample();
			classesTeacherExample.createCriteria().andTeacherIdEqualTo(TeacherId);
			List<ClassesTeacherKey> classesTeacherKeyList = classesTeacherMapper.selectByExample(classesTeacherExample);
			for (ClassesTeacherKey classesTeacherKey : classesTeacherKeyList) {
				classessIdList.add(classesTeacherKey.getClassesId());
			}
			if(classessIdList.size() > 0){
				scoreExample.createCriteria().andScoreIdIsNotNull().andClassesIdIn(classessIdList);
			}else{
				scoreExample.createCriteria().andScoreIdIsNotNull().andClassesIdIsNull();
			}
		}else{
			if(lectureTeacherClassesIdList != null && lectureTeacherClassesIdList.size() > 0){
				scoreExample.createCriteria().andClassesIdIn(lectureTeacherClassesIdList);
			}else{
				scoreExample.createCriteria().andClassesIdIsNull();
			}
		}
		
		List<ScoreVo> scoreVoList = new ArrayList<ScoreVo>();
		List<Score> scoreList = scoreMapper.selectByExample(scoreExample);
		for(Score score : scoreList){
			ScoreVo scoreVo = new ScoreVo();
			scoreVo.setScoreId(score.getScoreId());
			scoreVo.setClassesId(score.getClassesId());
			scoreVo.setStudentId(score.getStudentId());
			scoreVo.setCourseId(score.getCourseId());
			scoreVo.setTheoryscore(score.getTheoryscore());
			scoreVo.setPracticescore(score.getPracticescore());
			scoreVo.setTotal(score.getTotal());
			scoreVo.setCommon(score.getCommon());
			scoreVo.setTeacherId(score.getTeacherId());
			scoreVoList.add(scoreVo);
		}
		PageInfo<Score> pageInfo = new PageInfo<Score>(scoreList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		scoreListPageBean.setPage(pageNum);
		scoreListPageBean.setTotalPage(totalPage);
		scoreListPageBean.setList(scoreVoList);
		return scoreListPageBean;
	}

	@Override
	public PageBean<ScoreVo> getScoreListByPageAndCondition(Student student, Integer classesId, int teacherId, List<Integer> lectureTeacherClassesIdList ,int identity, int page, int rows) {
		PageBean<ScoreVo> scoreVoListPageBean = new PageBean<ScoreVo>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		
		List<ScoreVo> scoreVoList = new ArrayList<ScoreVo>();
		List<Score> scoreList = new ArrayList<Score>();
			if(identity == 1){
				scoreExample = new ScoreExample();
				if(student != null && classesId == -100 && teacherId == -100){
					scoreExample.createCriteria().andScoreIdIsNotNull().andStudentIdEqualTo(student.getStudentId());
				}else if(student == null && classesId != -100 && teacherId == -100){
					scoreExample.createCriteria().andScoreIdIsNotNull().andClassesIdEqualTo(classesId);
				}else if(student == null && classesId == -100 && teacherId != -100){
					Teacher teacher = teacherMapper.selectByPrimaryKey(teacherId);
					if(teacher != null && teacher.getIdentify() == 6){
						List<Integer> classesIdList = getClassessIdListByHeadTeacherId(teacherId);
						scoreExample.createCriteria().andScoreIdIsNotNull().andClassesIdIn(classesIdList);
					}else{
						scoreExample.createCriteria().andScoreIdIsNotNull().andTeacherIdEqualTo(teacherId);
					}
				}else if(student != null && classesId != -100 && teacherId == -100){
					scoreExample.createCriteria().andScoreIdIsNotNull().andStudentIdEqualTo(student.getStudentId()).andClassesIdEqualTo(classesId);
				}else if(student != null && classesId == -100 && teacherId != -100){
					Teacher teacher = getTeacher(teacherId);
					if(teacher != null && teacher.getIdentify() == 6){
						List<Integer> classesIdList = getClassessIdListByHeadTeacherId(teacherId);
						scoreExample.createCriteria().andScoreIdIsNotNull().andStudentIdEqualTo(student.getStudentId()).andClassesIdIn(classesIdList);
					}else{
						scoreExample.createCriteria().andScoreIdIsNotNull().andStudentIdEqualTo(student.getStudentId()).andTeacherIdEqualTo(teacherId);
					}
				}else if(student == null && classesId != -100 && teacherId != -100){
					Teacher teacher = getTeacher(teacherId);
					if(teacher != null && teacher.getIdentify() == 6){
						List<Integer> classesIdList = getClassessIdListByHeadTeacherId(teacherId);
						List<Integer> teacherIdList = getTeacherIdListByClassesIdList(classesIdList);
						scoreExample.createCriteria().andScoreIdIsNotNull().andClassesIdEqualTo(classesId).andTeacherIdIn(teacherIdList);
					}else{
						scoreExample.createCriteria().andScoreIdIsNotNull().andClassesIdEqualTo(classesId).andTeacherIdEqualTo(teacherId);
					}
				}else if(student != null && classesId != -100 && teacherId != -100){
					Teacher teacher = getTeacher(teacherId);
					if(teacher != null && teacher.getIdentify() == 6){
						List<Integer> classesIdList = getClassessIdListByHeadTeacherId(teacherId);
						List<Integer> teacherIdList = getTeacherIdListByClassesIdList(classesIdList);
						scoreExample.createCriteria().andScoreIdIsNotNull().andStudentIdEqualTo(student.getStudentId()).andClassesIdEqualTo(classesId).andTeacherIdIn(teacherIdList);
					}else{
						scoreExample.createCriteria().andScoreIdIsNotNull().andStudentIdEqualTo(student.getStudentId()).andClassesIdEqualTo(classesId).andTeacherIdEqualTo(teacherId);
					}
				}else{
					scoreExample.createCriteria().andScoreIdIsNull();
				}
				scoreList = scoreMapper.selectByExample(scoreExample);
			}else if(identity == 6){
				List<Integer> classessIdList = new ArrayList<Integer>();
				classesTeacherExample = new ClassesTeacherExample();
				scoreExample = new ScoreExample();
				if(student != null && classesId == -100){
					classesTeacherExample.createCriteria().andTeacherIdEqualTo(teacherId);
					List<ClassesTeacherKey> classesTeacherKeyList = classesTeacherMapper.selectByExample(classesTeacherExample);
					for (ClassesTeacherKey classesTeacherKey : classesTeacherKeyList){
						classessIdList.add(classesTeacherKey.getClassesId());
					}
					scoreExample.createCriteria().andScoreIdIsNotNull().andStudentIdEqualTo(student.getStudentId()).andClassesIdIn(classessIdList);
				}else if(student == null && classesId != -100){
					scoreExample.createCriteria().andScoreIdIsNotNull().andClassesIdEqualTo(classesId);
				}else if(student != null && classesId != -100){
					scoreExample.createCriteria().andScoreIdIsNotNull().andStudentIdEqualTo(student.getStudentId()).andClassesIdEqualTo(classesId);
				}else{
					scoreExample.createCriteria().andScoreIdIsNull();
				}
				scoreList = scoreMapper.selectByExample(scoreExample);
			}else{
				scoreExample = new ScoreExample();
				if(student != null && classesId == -100 && lectureTeacherClassesIdList != null && lectureTeacherClassesIdList.size() > 0){
					scoreExample.createCriteria().andStudentIdEqualTo(student.getStudentId()).andClassesIdIn(lectureTeacherClassesIdList);
				}else if(student == null && classesId != -100 && lectureTeacherClassesIdList != null && lectureTeacherClassesIdList.size() > 0 && lectureTeacherClassesIdList.contains(classesId)){
					scoreExample.createCriteria().andClassesIdEqualTo(classesId);
				}else if(student != null && classesId != -100 && lectureTeacherClassesIdList != null && lectureTeacherClassesIdList.size() > 0 && lectureTeacherClassesIdList.contains(classesId)){
					scoreExample.createCriteria().andStudentIdEqualTo(student.getStudentId()).andClassesIdEqualTo(classesId);
				}else{
					scoreExample.createCriteria().andScoreIdIsNull();
				}
				scoreList = scoreMapper.selectByExample(scoreExample);
			}
		
		for(Score score : scoreList){
			ScoreVo scoreVo = new ScoreVo();
			scoreVo.setScoreId(score.getScoreId());
			scoreVo.setClassesId(score.getClassesId());
			scoreVo.setStudentId(score.getStudentId());
			scoreVo.setTheoryscore(score.getTheoryscore());
			scoreVo.setPracticescore(score.getPracticescore());
			scoreVo.setTotal(score.getTotal());
			scoreVo.setCommon(score.getCommon());
			scoreVo.setTeacherId(score.getTeacherId());
			scoreVoList.add(scoreVo);
		}
		PageInfo<Score> pageInfo = new PageInfo<Score>(scoreList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		scoreVoListPageBean.setPage(pageNum);
		scoreVoListPageBean.setTotalPage(totalPage);
		scoreVoListPageBean.setList(scoreVoList);
		return scoreVoListPageBean;
	}

	@Override
	public List<Score> getScoreListByStudentId(Integer studentId){
		scoreExample = new ScoreExample();
		scoreExample.createCriteria().andScoreIdIsNotNull().andStudentIdEqualTo(studentId);
		List<Score> scoreList = scoreMapper.selectByExample(scoreExample);
		if(scoreList != null){
			return scoreList;
		}
		return null;
	}

	@Override
	public boolean addScore(Score score) {
		int num = scoreMapper.insert(score);
		if(num == 1){
			return true;
		}
		return false;
	}

	@Override
	public List<Score> getScoreList() {
		scoreExample = new ScoreExample();
		scoreExample.createCriteria().andScoreIdIsNotNull();
		List<Score> scoreList = scoreMapper.selectByExample(scoreExample);
		if(scoreList.size() > 0){
			return scoreList;
		}
		return null;
	}

	@Override
	public ScoreVo getScoreListByScoreId(Integer scoreId) {
		Score score = scoreMapper.selectByPrimaryKey(scoreId);
		if(score != null){
			ScoreVo scoreVo = new ScoreVo();
			scoreVo.setScoreId(score.getScoreId());
			scoreVo.setClassesId(score.getClassesId());
			scoreVo.setStudentId(score.getStudentId());
			scoreVo.setTheoryscore(score.getTheoryscore());
			scoreVo.setPracticescore(score.getPracticescore());
			scoreVo.setTotal(score.getTotal());
			scoreVo.setCommon(score.getCommon());
			scoreVo.setTeacherId(score.getTeacherId());
			return scoreVo;
		}
		return null;
	}

	@Override
	public boolean updateScore(Score score) {
		int num = scoreMapper.updateByPrimaryKeySelective(score);
		if(num == 1){
			return true;
		}
		return false;
	}

	@Override
	public List<Score> getScoreListByClassesId(Integer classesId) {
		ScoreExample scoreExample = new ScoreExample();
		scoreExample.createCriteria().andScoreIdIsNotNull().andClassesIdEqualTo(classesId);
		List<Score> scoreList = scoreMapper.selectByExample(scoreExample);
		return scoreList;
	}
	
	//根据班主任ID获取该班主任的多有班级ID
	public List<Integer> getClassessIdListByHeadTeacherId(Integer teacherId){
		List<Integer> classessIdList = new ArrayList<Integer>();
		classesTeacherExample = new ClassesTeacherExample();
		classesTeacherExample.createCriteria().andTeacherIdEqualTo(teacherId);
		List<ClassesTeacherKey> classesTeacherKeyList = classesTeacherMapper.selectByExample(classesTeacherExample);
		for (ClassesTeacherKey classesTeacherKey : classesTeacherKeyList){
			classessIdList.add(classesTeacherKey.getClassesId());
		}
		return classessIdList;
	}
	
	//根据教师ID获取教师
	public Teacher getTeacher(Integer teacherId){
		if(teacherId != null && teacherId != -100){
			Teacher teacher = teacherMapper.selectByPrimaryKey(teacherId);
			if(teacher != null){
				return teacher;
			}
		}
		return null;
	}
	
	public List<Integer> getTeacherIdListByClassesIdList(List<Integer> classesIdList){
		List<Integer> teacherIdList = new ArrayList<Integer>();
		for (Integer classesId : classesIdList) {
			classesTeacherExample = new ClassesTeacherExample();
			classesTeacherExample.createCriteria().andClassesIdEqualTo(classesId);
			List<ClassesTeacherKey> classesTeacherKeyList = classesTeacherMapper.selectByExample(classesTeacherExample);
			for (ClassesTeacherKey classesTeacherKey : classesTeacherKeyList){
				teacherIdList.add(classesTeacherKey.getTeacherId());
			}
		}
		return teacherIdList;
	}
}
