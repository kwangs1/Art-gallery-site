package com.myspring.Art.Collectible.Controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.Art.Collectible.Service.CollectibleService;
import com.myspring.Art.Collectible.VO.CollectibleVO;
import com.myspring.Art.common.Like.Service.LikeService;
import com.myspring.Art.common.Like.VO.LikeVO;
import com.myspring.Art.common.Reply.Service.ReplyService;
import com.myspring.Art.common.Reply.VO.ReplyVO;
import com.myspring.Art.common.base.BaseController;
import com.myspring.Art.common.domain.PageMaker;
import com.myspring.Art.common.domain.SearchCriteria;

@Controller("collectibleController")
@RequestMapping(value="/collectible")
public class CollectibleControllerImpl extends BaseController implements CollectibleController{
	private static final Logger logger = LoggerFactory.getLogger(CollectibleControllerImpl.class);
	@Autowired
	private CollectibleService collectibleService;
	@Autowired
	private CollectibleVO collectibleVO;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private LikeService likeService;


	//이미지 게시판 목록
	@Override
	@RequestMapping(value="/collectibleList.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView collectibleList(@ModelAttribute("scri") SearchCriteria scri,
			                           HttpServletRequest request, HttpServletResponse response)  throws Exception {
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(collectibleService.countListTotal(scri));

		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);

		List<CollectibleVO> collectibleList=collectibleService.collectibleList(scri);
		mav.addObject("collectible", collectibleList);
		mav.addObject("pageMaker",pageMaker);
	
		return mav;
	}
	
	//글 상세보기
	@Override
	@RequestMapping(value="/collectibleDetail.do", method=RequestMethod.GET)
	public ModelAndView collectibleDetail(@RequestParam("goods_id") int goods_id,String member_id,
			@ModelAttribute("reply")ReplyVO reply,
			HttpServletRequest request)throws Exception{
		
		String viewName=(String)request.getAttribute("viewName");
		
		collectibleVO = collectibleService.collectibleDetail(goods_id);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("collectible",collectibleVO); 

		//댓글 목록
		List<ReplyVO> replyList = replyService.readReply(collectibleVO.getGoods_id());
		mav.addObject("replyList",replyList);
		
		//좋아요
		LikeVO like = new LikeVO();
		
		like.setGoods_id(goods_id);
		like.setMember_id(member_id);
		like.setLike_type(1);
		
		//좋아요 체크 확인
		mav.addObject("findLike",likeService.findLike(goods_id,member_id));
		//좋아요 갯수
		mav.addObject("getLike",likeService.getLike(goods_id,1));
		
		//게시판 list에 좋아요 갯수 출력
		collectibleService.UpdateLikeCount(goods_id);
		//게시판 liset 댓글 갯수 출력
		collectibleService.UpdateReplyCount(goods_id);
		return mav;
	}
	
	//댓글 수정팝업창
	@RequestMapping(value="/getUpdateReply.do", method=RequestMethod.GET)
	public ModelAndView getUpdateReply(@ModelAttribute ReplyVO reply, HttpServletRequest request) throws Exception {
		
		String viewName=(String)request.getAttribute("viewName");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		
		collectibleVO = collectibleService.collectibleDetail(reply.getGoods_id());
		mav.addObject("collectible",collectibleVO); 
		
		mav.addObject("reply" , replyService.getUpdateReply(reply.getRno()));
		return mav;
	}
}
