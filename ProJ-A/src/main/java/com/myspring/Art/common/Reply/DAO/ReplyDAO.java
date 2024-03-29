package com.myspring.Art.common.Reply.DAO;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.myspring.Art.common.Reply.VO.ReplyVO;

public interface ReplyDAO {

	List<ReplyVO> readReply(int goods_id) throws DataAccessException;

	int writeReply(ReplyVO vo) throws DataAccessException;

	int updateReply(ReplyVO vo);

	ReplyVO getUpdateReply(int rno);

	void deleteReply(ReplyVO vo) throws DataAccessException;


}
