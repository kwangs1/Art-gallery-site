package com.myspring.Art.Member.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.Art.Member.Service.MemberService;
import com.myspring.Art.Member.VO.MemberVO;
import com.myspring.Art.common.base.BaseController;

@Controller("memberController")
@RequestMapping(value ="/member")
public class MemberControllerImpl extends BaseController implements MemberController{
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	@Autowired
	private MemberService memberService;

	
	//�α���
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute MemberVO memberVO,RedirectAttributes rAttr,
			HttpServletRequest req, RedirectAttributes rttr) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = req.getSession();
		MemberVO vo = memberService.login(memberVO);		
		
		if(vo != null) {
			session.setAttribute("memberInfo", vo);
			mav.setViewName("redirect:/main/main.do");
		}else {
			rttr.addFlashAttribute("result",0); 
			mav.setViewName("redirect:/member/loginForm.do");
		}
		return mav;
	}
	
	//�α׾ƿ�
	@Override
	@RequestMapping(value = "/logout.do", method =  RequestMethod.GET)
	public String logout(HttpSession session) throws Exception {
		session.invalidate();
		logger.info("bye logout success");

		return "redirect:/main/main.do";
	}
	
	//ȸ������
	@Override
	@RequestMapping(value="/addMember.do" ,method = RequestMethod.POST)
	public ResponseEntity  addMember(@ModelAttribute("memberVO") MemberVO _memberVO,//ȸ�� ����â���� ���۵� ȸ�� ������ _memberVO�� ����
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
		    memberService.addMember(_memberVO);//ȸ�������� sql������ ����
		    message  = "<script>";	
		    message +=" alert('ȸ�� ������ ���ƽ��ϴ�.�α���â���� �̵��մϴ�.');";
		    message += " location.href='"+request.getContextPath()+"/member/loginForm.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('�۾� �� ������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');";
		    message += " location.href='"+request.getContextPath()+"/member/memberForm.do';";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	

	//id �ߺ��˻�
	@RequestMapping(value="IdCheck.do" , method = RequestMethod.POST)
	@ResponseBody
	public String memberIdChk(String id)throws Exception{
		/* logger.info("����"); */
		
		int result = memberService.idCheck(id); // memberService.idCheck�� ����� int�� ���� result�� ����
		if(result != 0) { //id�� �����ϸ� '1' , �������� ������ '0' ��ȯ
			return "fail";
		}else {
			return "success";
		}
	}
	
	//ȸ������ ������
	@RequestMapping(value="/memberInfo.do" ,method = RequestMethod.GET)
	public ModelAndView membefInfo(@RequestParam String member_id,
			HttpServletRequest request, HttpServletResponse response)throws Exception{	
		
		String viewName = (String)request.getAttribute("viewName");		
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		
		memberService.selectMemberInfo(member_id);
		
		return mav;
	}
	
	//����
	@RequestMapping(value="/MemberModify.do" , method = RequestMethod.POST)
	public ModelAndView MemberModify(@RequestParam String member_id,@ModelAttribute MemberVO memberVO,
			HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/memberInfo.do?member_id=" + memberVO.getMember_id());
		
		session.setAttribute("memberInfo", memberVO);		
		
		memberService.MemberModify(memberVO);
		
		return mav;
	}
	
	//modify(��� post)
	@RequestMapping(value="/MemberModify_Pw.do" , method = RequestMethod.POST)
	public ModelAndView MemberModify_Pw(@RequestParam String member_id,
			@ModelAttribute MemberVO vo, HttpSession session)throws Exception{	
	
		memberService.MemberModify_Pw(vo);
		session.setAttribute("memberInfo", vo);
		session.invalidate();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/loginForm.do");
		
		return mav;
	}
}
