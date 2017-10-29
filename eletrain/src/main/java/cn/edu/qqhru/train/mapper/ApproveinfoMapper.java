package cn.edu.qqhru.train.mapper;

import cn.edu.qqhru.train.pojo.Approveinfo;
import cn.edu.qqhru.train.pojo.ApproveinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApproveinfoMapper {
    int countByExample(ApproveinfoExample example);

    int deleteByExample(ApproveinfoExample example);

    int deleteByPrimaryKey(Integer approveinfoId);

    int insert(Approveinfo record);

    int insertSelective(Approveinfo record);

    List<Approveinfo> selectByExample(ApproveinfoExample example);

    Approveinfo selectByPrimaryKey(Integer approveinfoId);

    int updateByExampleSelective(@Param("record") Approveinfo record, @Param("example") ApproveinfoExample example);

    int updateByExample(@Param("record") Approveinfo record, @Param("example") ApproveinfoExample example);

    int updateByPrimaryKeySelective(Approveinfo record);

    int updateByPrimaryKey(Approveinfo record);
}