package cn.edu.qqhru.train.mapper;

import cn.edu.qqhru.train.pojo.Syllabus;
import cn.edu.qqhru.train.pojo.SyllabusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SyllabusMapper {
    int countByExample(SyllabusExample example);

    int deleteByExample(SyllabusExample example);

    int deleteByPrimaryKey(Integer syllabusId);

    int insert(Syllabus record);

    int insertSelective(Syllabus record);

    List<Syllabus> selectByExample(SyllabusExample example);

    Syllabus selectByPrimaryKey(Integer syllabusId);

    int updateByExampleSelective(@Param("record") Syllabus record, @Param("example") SyllabusExample example);

    int updateByExample(@Param("record") Syllabus record, @Param("example") SyllabusExample example);

    int updateByPrimaryKeySelective(Syllabus record);

    int updateByPrimaryKey(Syllabus record);
}