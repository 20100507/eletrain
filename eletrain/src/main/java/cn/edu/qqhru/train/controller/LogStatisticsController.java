package cn.edu.qqhru.train.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.qqhru.train.service.LogStatisticsService;

/**
 * 报表
 * <p>Title: LogStatisticsController</p>
 * <p>Description: </p>
 * <p>School: qiqihar university</p> 
 * @author	BQ
 * @date	2017年9月14日上午9:52:19
 * @version 1.0
 */
@Controller
@RequestMapping("/logStatistics")
public class LogStatisticsController {
	
	@Autowired
	private LogStatisticsService logStatisticsSerivce;
	
	/**
	 * 统计登录人数报表
	 * <p>Title: getLogStatistics</p>
	 * <p>Description: </p>
	 * @return
	 */
	@RequestMapping("/getLogStatistics")
	@ResponseBody
	public List<Integer> getLogStatistics(){
		List<Integer> count = logStatisticsSerivce.getLogStatisticsByHour();
		return count;
	}
	
}
