package cn.edu.qqhru.train.mapper;

import cn.edu.qqhru.train.pojo.ClassesPlan;
import cn.edu.qqhru.train.pojo.ClassesPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClassesPlanMapper {
    int countByExample(ClassesPlanExample example);

    int deleteByExample(ClassesPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClassesPlan record);

    int insertSelective(ClassesPlan record);

    List<ClassesPlan> selectByExample(ClassesPlanExample example);

    ClassesPlan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClassesPlan record, @Param("example") ClassesPlanExample example);

    int updateByExample(@Param("record") ClassesPlan record, @Param("example") ClassesPlanExample example);

    int updateByPrimaryKeySelective(ClassesPlan record);

    int updateByPrimaryKey(ClassesPlan record);
}