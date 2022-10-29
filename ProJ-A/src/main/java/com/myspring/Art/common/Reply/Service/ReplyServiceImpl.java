package com.myspring.Art.common.Reply.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.Art.Collectible.Controller.CollectibleControllerImpl;
import com.myspring.Art.common.Reply.DAO.ReplyDAO;
import com.myspring.Art.common.Reply.VO.ReplyVO;

@Service("replyService")
@Transactional(propagation = Propagation.REQUIRED)
public class ReplyServiceImpl implements ReplyService{

	
	@Autowired
	private ReplyDAO replyDAO;
	
	@Override
	public List<ReplyVO> readReply(int goods_id)throws Exception{
		return replyDAO.readReply(goods_id);
	}
	
	@Override
	public int writeReply(ReplyVO vo)throws Exception{
		return replyDAO.writeReply(vo);
	}
	
	@Override
	public void deleteReply(ReplyVO vo)throws Exception{
		replyDAO.deleteReply(vo);
	}
	
	//��ۼ���
	@Override
	public int updateReply(ReplyVO vo) {
		int result = replyDAO.updateReply(vo);
		return result;
	}
	//��� ������
	@Override
	public ReplyVO getUpdateReply(int rno) {
		return replyDAO.getUpdateReply(rno);
	}
}
