package cn.edu.qqhru.train.mapper;

import java.util.List;

import cn.edu.qqhru.train.vo.LogStatisticsVo;

public interface LogStatisticsMapper {
	
	List<LogStatisticsVo> getLogStatisticsByHour();
}	
