package com.myspring.Art.Admin.notice.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.Art.Admin.notice.DAO.NoticeDAO;
import com.myspring.Art.Admin.notice.VO.Criteria;
import com.myspring.Art.Admin.notice.VO.NoticeVO;

@Service("noticeService")
@Transactional(propagation = Propagation.REQUIRED)
public class NoticeServiceImpl implements NoticeService{
	@Autowired
	private NoticeDAO noticeDAO;

	@Override
	public List<Map<String,Object>> NoticeList(Criteria cri) throws Exception{
		return noticeDAO.selectAllNoticeList(cri);
	}	

	@Override
	public int countNoticeListTotal() {
	    return noticeDAO.countNoticeList();
	}


	@Override
	public int addNewNotice(NoticeVO notice)throws Exception{
		return noticeDAO.insertNoticeList(notice);
	}	
	
	@Override
	public NoticeVO NoticeDetail(int bno)throws Exception{
		NoticeVO noticeVO = noticeDAO.selectNoticeDetail(bno);
		noticeDAO.boardHit(bno);
		return noticeVO;
	}
	
	@Override
	public int removeNotice(int bno)throws Exception{
		return noticeDAO.deleteNoticeList(bno);
	}

	@Override
	public int modifyNotice(NoticeVO vo) throws Exception {
		return noticeDAO.modifyNotice(vo);
	}
	@Override
	public List<String> keywordSearch(String keyword) throws Exception {
		List<String> list=noticeDAO.selectKeywordSearch(keyword);
		return list;
	}
	@Override
	public List<NoticeVO> searchNotice(String searchWord,Criteria cri) throws Exception{
		List noticeList=noticeDAO.selectNoticeBySearchWord(searchWord,cri);
		return noticeList;
	}
}
