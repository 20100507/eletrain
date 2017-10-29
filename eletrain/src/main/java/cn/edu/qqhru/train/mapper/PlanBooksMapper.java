package cn.edu.qqhru.train.mapper;

import cn.edu.qqhru.train.pojo.PlanBooks;
import cn.edu.qqhru.train.pojo.PlanBooksExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlanBooksMapper {
    int countByExample(PlanBooksExample example);

    int deleteByExample(PlanBooksExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PlanBooks record);

    int insertSelective(PlanBooks record);

    List<PlanBooks> selectByExample(PlanBooksExample example);

    PlanBooks selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PlanBooks record, @Param("example") PlanBooksExample example);

    int updateByExample(@Param("record") PlanBooks record, @Param("example") PlanBooksExample example);

    int updateByPrimaryKeySelective(PlanBooks record);

    int updateByPrimaryKey(PlanBooks record);
}