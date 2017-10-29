package cn.edu.qqhru.train.service;

import java.util.List;

import cn.edu.qqhru.train.pojo.Score;
import cn.edu.qqhru.train.pojo.Student;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.ScoreVo;

public interface ScoreService {

	PageBean<ScoreVo> getScoreListPageBean(int page, int rows, int teacherId, List<Integer> lectureTeacherClassesIdList ,int identity);

	PageBean<ScoreVo> getScoreListByPageAndCondition(Student student, Integer classesId, int teacherId, List<Integer> lectureTeacherClassesIdList ,int identity, int page, int rows);

	List<Score> getScoreListByStudentId(Integer studentId);

	boolean addScore(Score score);

	List<Score> getScoreList();

	ScoreVo getScoreListByScoreId(Integer scoreId);

	boolean updateScore(Score score);

	List<Score> getScoreListByClassesId(Integer classesId);

}
