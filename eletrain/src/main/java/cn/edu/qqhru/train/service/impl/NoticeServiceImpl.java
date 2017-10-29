package cn.edu.qqhru.train.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.qqhru.train.mapper.NoticeMapper;
import cn.edu.qqhru.train.pojo.Notice;
import cn.edu.qqhru.train.pojo.NoticeExample;
import cn.edu.qqhru.train.service.NoticeService;
import cn.edu.qqhru.train.utils.MD5Utils;
import cn.edu.qqhru.train.utils.PageBean;
import cn.edu.qqhru.train.vo.NoticeVo;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	@Autowired 
	private NoticeMapper noticeMapper;
	
	private NoticeExample noticeExample;
	
	@Override
	public PageBean<NoticeVo> getNoticeListPageBean(int identity, int page, int rows) {
		PageBean<NoticeVo> noticeListPageBean = new PageBean<NoticeVo>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows,"createtime desc");
		noticeExample = new NoticeExample();
		if(identity == 1){
			noticeExample.createCriteria().andNoticeIdIsNotNull();
		}else{
			noticeExample.createCriteria().andNoticeIdIsNotNull().andIsReleasedEqualTo(1);
		}
		List<NoticeVo> noticeVoList = new ArrayList<NoticeVo>();
		List<Notice> noticeList = noticeMapper.selectByExample(noticeExample);
		for(Notice notice : noticeList){
			NoticeVo noticeVo = new NoticeVo();
			noticeVo.setNoticeId(notice.getNoticeId());
			noticeVo.setTitle(notice.getTitle());
			noticeVo.setAdminId(notice.getAdminId());
			noticeVo.setCreatetime(notice.getCreatetime());
			noticeVo.setContent(notice.getContent());
			noticeVo.setIsReleased(notice.getIsReleased());
			noticeVoList.add(noticeVo);
		}
		PageInfo<Notice> pageInfo = new PageInfo<Notice>(noticeList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		noticeListPageBean.setPage(pageNum);
		noticeListPageBean.setTotalPage(totalPage);
		noticeListPageBean.setList(noticeVoList);
		return noticeListPageBean;
	}

	@Override
	public NoticeVo getNoticeById(Integer id){
		NoticeVo noticeVo = new NoticeVo();
		Notice notice = noticeMapper.selectByPrimaryKey(id);
		noticeVo.setNoticeId(notice.getNoticeId());
		noticeVo.setAdminId(notice.getAdminId());
		noticeVo.setTitle(notice.getTitle());
		noticeVo.setContent(notice.getContent());
		noticeVo.setCreatetime(notice.getCreatetime());
		noticeVo.setIsReleased(notice.getIsReleased());
		return noticeVo;
	}

	@Override
	public PageBean<NoticeVo> getNoticeListByPageAndCondition(int condition, int identity, int page, int rows) {
		PageBean<NoticeVo> noticeListPageBean = new PageBean<NoticeVo>();
		// 设置页数，和显示多少条记录
		PageHelper.startPage(page, rows);
		noticeExample = new NoticeExample();
		if(identity == 1){
			noticeExample.createCriteria().andNoticeIdIsNotNull().andAdminIdEqualTo(condition);
		}else{
			noticeExample.createCriteria().andNoticeIdIsNotNull().andAdminIdEqualTo(condition).andIsReleasedEqualTo(1);
		}
		List<NoticeVo> noticeVoList = new ArrayList<NoticeVo>();
		List<Notice> noticeList = noticeMapper.selectByExample(noticeExample);
		for(Notice notice : noticeList){
			NoticeVo noticeVo = new NoticeVo();
			noticeVo.setNoticeId(notice.getNoticeId());
			noticeVo.setTitle(notice.getTitle());
			noticeVo.setAdminId(notice.getAdminId());
			noticeVo.setCreatetime(notice.getCreatetime());
			noticeVo.setContent(notice.getContent());
			noticeVo.setIsReleased(notice.getIsReleased());
			noticeVoList.add(noticeVo);
		}
		PageInfo<Notice> pageInfo = new PageInfo<Notice>(noticeList);
		long totalPage = pageInfo.getPages();
		int pageNum = pageInfo.getPageNum();
		noticeListPageBean.setPage(pageNum);
		noticeListPageBean.setTotalPage(totalPage);
		noticeListPageBean.setList(noticeVoList);
		return noticeListPageBean;
	}

	@Override
	public boolean deleteNoticeById(Integer id) {
		int num = noticeMapper.deleteByPrimaryKey(id);
		if(num == 1){
			return true;
		}
		return false;
	}

	@Override
	public void deleteMultipleNotices(Integer[] ids) {
		for(int i=0; i<ids.length; i++){
			noticeMapper.deleteByPrimaryKey(ids[i]);
		}
	}

	@Override
	public boolean addNotice(Notice notice) {
		int num = noticeMapper.insert(notice);
		if(num == 1){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateNotice(Notice notice) {
		int num = noticeMapper.updateByPrimaryKeySelective(notice);
		if(num == 1){
			return true;
		}
		return false;
	}

	@Override
	public int getCount(int identity) {
		if(identity == 1){
			noticeExample.createCriteria().andNoticeIdIsNotNull();
		}else{
			noticeExample.createCriteria().andNoticeIdIsNotNull().andIsReleasedEqualTo(1);
		}
		List<Notice> list = noticeMapper.selectByExample(noticeExample);
		int size = list.size();
		return size;
	}

}
