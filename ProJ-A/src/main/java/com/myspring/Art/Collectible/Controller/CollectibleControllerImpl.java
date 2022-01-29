package com.myspring.Art.Collectible.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.Art.Collectible.Service.CollectibleService;
import com.myspring.Art.Collectible.VO.CollectibleVO;
import com.myspring.Art.common.Reply.Service.ReplyService;
import com.myspring.Art.common.Reply.VO.ReplyVO;
import com.myspring.Art.common.base.BaseController;

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
	
	@Override
	@RequestMapping(value="/collectibleList.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView collectibleList(@RequestParam Map<String, String> dateMap,
			                           HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		
		String section = dateMap.get("section");
		String pageNum = dateMap.get("pageNum");
		
		Map<String,Object> condMap=new HashMap<String,Object>();
		if(section== null) {
			section = "1";
		}
		condMap.put("section",section);
		if(pageNum== null) {
			pageNum = "1";
		}
		condMap.put("pageNum",pageNum);
		List<CollectibleVO> collectibleList=collectibleService.collectibleList(condMap);
		mav.addObject("collectible", collectibleList);
		
		mav.addObject("section", section);
		mav.addObject("pageNum", pageNum);

		return mav;
	}
	
	@Override
	@RequestMapping(value="/collectibleDetail.do", method=RequestMethod.GET)
	public ModelAndView collectibleDetail(@RequestParam("goods_id") int goods_id,
				HttpServletRequest request, HttpServletResponse response)throws Exception{
		String viewName=(String)request.getAttribute("viewName");
		collectibleVO = collectibleService.collectibleDetail(goods_id);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("collectible",collectibleVO);
		
		//��۱��
		List<ReplyVO> replyList = replyService.readReply(collectibleVO.getGoods_id());
		mav.addObject("replyList",replyList);
		
		return mav;
	}
	
	//����ۼ�
	@Override
	@RequestMapping(value="/replyWrite.do", method=RequestMethod.POST)
	public ModelAndView replyWrite(@ModelAttribute("reply")ReplyVO vo,  RedirectAttributes rttr,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("reply Write");
		replyService.writeReply(vo);
		rttr.addAttribute("goods_id", vo.getGoods_id());
		ModelAndView mav = new ModelAndView("redirect:/collectible/collectibleDetail.do");
		return mav;
	}
	//��� ������
	@RequestMapping(value="/modifyReplyForm.do", method = RequestMethod.GET)
	public ModelAndView modifyReplyForm(@ModelAttribute("reply")ReplyVO vo, @RequestParam("goods_id") int goods_id,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		String viewName = (String)request.getAttribute("viewName");
		collectibleVO = collectibleService.collectibleDetail(goods_id);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("collectible",collectibleVO);
		mav.addObject("reply",replyService.selectReply(vo.getRno()));
		
		return mav;	
	}
	//��� ���� �����ͺκ�
	@Override
	@RequestMapping(value="/modifyReply.do", method = RequestMethod.POST)
	public ModelAndView modifyReply(@ModelAttribute("reply")ReplyVO vo, RedirectAttributes rttr,
			HttpServletRequest request, HttpServletResponse response)throws Exception{

		replyService.updateReply(vo);		
		ModelAndView mav = new ModelAndView("redirect:/collectible/collectibleDetail.do");

		rttr.addAttribute("goods_id", vo.getGoods_id());
		return mav;
	}
	
	//��ۻ���
	@Override
	@RequestMapping(value="/removeReply.do", method = RequestMethod.POST)
	public ModelAndView removeReply(@ModelAttribute("reply")ReplyVO vo, RedirectAttributes rttr,
				HttpServletRequest request, HttpServletResponse response)throws Exception{
		logger.info("reply delete");
		
		replyService.deleteReply(vo);
		
		rttr.addAttribute("goods_id", vo.getGoods_id());
		ModelAndView mav = new ModelAndView("redirect:/collectible/collectibleDetail.do");

		return mav;
	}
	
	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String viewName = uri.substring(begin, end);
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
		}
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
		}
		return viewName;
	}
}
