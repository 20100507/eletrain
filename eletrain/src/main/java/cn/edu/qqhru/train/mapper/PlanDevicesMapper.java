package cn.edu.qqhru.train.mapper;

import cn.edu.qqhru.train.pojo.PlanDevices;
import cn.edu.qqhru.train.pojo.PlanDevicesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlanDevicesMapper {
    int countByExample(PlanDevicesExample example);

    int deleteByExample(PlanDevicesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PlanDevices record);

    int insertSelective(PlanDevices record);

    List<PlanDevices> selectByExample(PlanDevicesExample example);

    PlanDevices selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PlanDevices record, @Param("example") PlanDevicesExample example);

    int updateByExample(@Param("record") PlanDevices record, @Param("example") PlanDevicesExample example);

    int updateByPrimaryKeySelective(PlanDevices record);

    int updateByPrimaryKey(PlanDevices record);
}