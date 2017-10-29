package cn.edu.qqhru.train.mapper;

import cn.edu.qqhru.train.pojo.ClassesTeacherExample;
import cn.edu.qqhru.train.pojo.ClassesTeacherKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClassesTeacherMapper {
    int countByExample(ClassesTeacherExample example);

    int deleteByExample(ClassesTeacherExample example);

    int deleteByPrimaryKey(ClassesTeacherKey key);

    int insert(ClassesTeacherKey record);

    int insertSelective(ClassesTeacherKey record);

    List<ClassesTeacherKey> selectByExample(ClassesTeacherExample example);

    int updateByExampleSelective(@Param("record") ClassesTeacherKey record, @Param("example") ClassesTeacherExample example);

    int updateByExample(@Param("record") ClassesTeacherKey record, @Param("example") ClassesTeacherExample example);
}