package cn.edu.qqhru.train.service;

import cn.edu.qqhru.train.pojo.Notice;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.NoticeVo;

public interface NoticeService {

	PageBean<NoticeVo> getNoticeListPageBean(int identity, int page, int rows);

	NoticeVo getNoticeById(Integer id);

	PageBean<NoticeVo> getNoticeListByPageAndCondition(int condition, int identity, int page, int rows);

	boolean deleteNoticeById(Integer id);

	void deleteMultipleNotices(Integer[] ids);

	boolean addNotice(Notice notice);

	boolean updateNotice(Notice notice);
	
	int getCount(int identity);

}
