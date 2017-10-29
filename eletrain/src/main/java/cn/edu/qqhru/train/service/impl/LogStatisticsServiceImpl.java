package cn.edu.qqhru.train.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.qqhru.train.mapper.LogStatisticsMapper;
import cn.edu.qqhru.train.service.LogStatisticsService;
import cn.edu.qqhru.train.vo.LogStatisticsVo;

/**
 * <p>Title: LogStatisticsServiceImpl</p>
 * <p>Description: </p>
 * <p>School: qiqihar university</p> 
 * @author	BQ
 * @date	2017年9月14日上午9:24:14
 * @version 1.0
 */
@Service
public class LogStatisticsServiceImpl implements LogStatisticsService {

	@Autowired
	private LogStatisticsMapper logStatisticsMapper;
	
	@Override
	public List<Integer> getLogStatisticsByHour() {
		List<LogStatisticsVo> logStatisticsList = logStatisticsMapper.getLogStatisticsByHour();
		List<Integer> loginDisplay = new ArrayList<>();
		for (LogStatisticsVo logStatisticsVo : logStatisticsList) {
			int count = logStatisticsVo.getLoginCount();
			loginDisplay.add(count);
		}
		return loginDisplay;
	}

}
