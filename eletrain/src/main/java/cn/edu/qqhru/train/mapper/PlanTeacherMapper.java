package cn.edu.qqhru.train.mapper;

import cn.edu.qqhru.train.pojo.PlanTeacher;
import cn.edu.qqhru.train.pojo.PlanTeacherExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlanTeacherMapper {
    int countByExample(PlanTeacherExample example);

    int deleteByExample(PlanTeacherExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PlanTeacher record);

    int insertSelective(PlanTeacher record);

    List<PlanTeacher> selectByExample(PlanTeacherExample example);

    PlanTeacher selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PlanTeacher record, @Param("example") PlanTeacherExample example);

    int updateByExample(@Param("record") PlanTeacher record, @Param("example") PlanTeacherExample example);

    int updateByPrimaryKeySelective(PlanTeacher record);

    int updateByPrimaryKey(PlanTeacher record);
}