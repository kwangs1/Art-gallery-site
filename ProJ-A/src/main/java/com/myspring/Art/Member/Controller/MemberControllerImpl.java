package com.myspring.Art.Member.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.Art.Member.Service.MemberService;
import com.myspring.Art.Member.VO.MemberVO;
import com.myspring.Art.common.base.BaseController;

@Controller("memberController")
@RequestMapping(value ="/member")
public class MemberControllerImpl extends BaseController implements MemberController{
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberVO memberVO;
	@Autowired
	private JavaMailSender mailSender;
	
	//�α���
	@Override
	@RequestMapping(value="/login.do" ,method = RequestMethod.POST)
	public ModelAndView login(@RequestParam Map<String, String> loginMap, //id, pw �� map�� �����մϴ�
							HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView mav = new ModelAndView();
		memberVO = memberService.login(loginMap);//sql������ ����
		if(memberVO !=null && memberVO.getMember_id() != null) {
			//��ȸ�� ȸ�� ������ ������ isLogOn �Ӽ��� true �����ϰ� memberInfo �Ӽ����� ȸ�� ������ ����
			HttpSession session = request.getSession();
			session = request.getSession();
			session.setAttribute("isLogOn", true);
			session.setAttribute("memberInfo", memberVO);
			
			String action=(String)session.getAttribute("action");
			if(action!=null && action.equals("/")) {
				mav.setViewName("redirect:" + action);
			}else {
				mav.setViewName("redirect:/main/main.do");
			}
		}else {
			String message = "���̵� ��й�ȣ�� Ʋ���ϴ�. �ٽ� �α��� ���ּ���";
			mav.addObject("message",message);
			mav.setViewName("/member/loginForm");
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
	
	//id�ߺ�üũ
	@Override
	@RequestMapping(value= "/overlapped.do", method = RequestMethod.POST)
	public ResponseEntity overlapped(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseEntity resEntity = null;
		String result = memberService.overlapped(id); //id�ߺ��˻�
		resEntity = new ResponseEntity(result, HttpStatus.OK);
		return resEntity;
	}
	
	//ȸ������ ������
	@RequestMapping(value="/memberInfo.do" ,method = RequestMethod.GET)
	public ModelAndView membefInfo(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		memberVO = memberService.memberInfo();
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("memberInfo",memberVO);
		return mav;
	}
	
	//ȸ������ ����
	@Override
	@RequestMapping(value="/modifyMyInfo.do", method= RequestMethod.POST)
	public ResponseEntity modifyMyInfo(@RequestParam("attribute")String attribute, @RequestParam("value")String value,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String,String> memberMap = new HashMap<String,String>();
		String val[]=  null;
		HttpSession session = request.getSession();
		memberVO = (MemberVO)session.getAttribute("memberInfo");
		String member_id = memberVO.getMember_id();
		if(attribute.equals("member_birth")) {
			val = value.split(",");
			memberMap.put("member_birth_y", val[0]);
			memberMap.put("member_birth_y", val[1]);
			memberMap.put("member_birth_y", val[2]);
			memberMap.put("member_birth_gn", val[3]);
		}else if(attribute.equals("tel")) {
			val = value.split(",");
			memberMap.put("tel1", val[0]);
			memberMap.put("tel2", val[1]);
			memberMap.put("tel3", val[2]);
		}else if(attribute.equals("hp")) {
			val=value.split(",");
			memberMap.put("hp1",val[0]);
			memberMap.put("hp2",val[1]);
			memberMap.put("hp3",val[2]);
			memberMap.put("smssts_yn", val[3]);
		}else if(attribute.equals("email")) {
			val=value.split(",");
			memberMap.put("email1",val[0]);
			memberMap.put("email2",val[1]);
			memberMap.put("emailsts_yn", val[2]);
		}else if(attribute.equals("address")) {
			val=value.split(",");
			memberMap.put("zipcode",val[0]);
			memberMap.put("roadAddress",val[1]);
			memberMap.put("jibunAddress", val[2]);
			memberMap.put("namujiAddress", val[3]);
		}else {
			memberMap.put(attribute, value);
		}
		memberMap.put("member_id",member_id);
		
		memberVO = (MemberVO)memberService.modifyMyInfo(memberMap);
		session.removeAttribute("memberInfo");
		session.setAttribute("memberInfo", memberVO);
		
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		message = "mod_success";
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
//	@RequestMapping(value="/memberSearchForm.do" ,method = RequestMethod.GET)
//	public ModelAndView memberSerachForm(HttpServletRequest request, HttpServletResponse response)throws Exception{
//		String viewName = (String)request.getAttribute("viewName");
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName(viewName);
//		return mav;
//	}
//	
//	@RequestMapping(value="/memberSearch.do" ,method = RequestMethod.POST)
//	public ModelAndView memberSerach(@ModelAttribute("member")MemberVO vo,
//			HttpServletRequest request, HttpServletResponse response)throws Exception{
//		String viewName = (String)request.getAttribute("viewName");
//		memberService.memberSearch(vo);
//		ModelAndView mav = new ModelAndView();
//		return mav;
//	}
//	
//	@RequestMapping(value="/mailCheck.do", method = RequestMethod.GET)
//	@ResponseBody
//	public void mailCheck(String email1,String email2)throws Exception{
//		
//		logger.info("������ ���� Ȯ��");
//		logger.info("�̸���:"+email1+email2);
//		
//		Random random = new Random();
//		int checkNum = random.nextInt(888888)+111111;
//		logger.info("������ȣ:"+checkNum);
//		
//		String setFrom = "cckwang2345@naver.com";
//		String toMail = email1+email2;
//		String title = "ȸ�� ID/PW ã�� ������ȣ�Դϴ�";
//		String content = 
//				"ȸ�� ID/PW ã�� ������ȣ �Դϴ�"+
//					"<br><br>"+
//					"������ȣ��"+checkNum+"�Դϴ�"+
//					"<br>"+
//					"�ش� ������ȣ�� �����Ͽ� �ּ���.";	
//		try {
//			MimeMessage message = mailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
//			helper.setFrom(setFrom);
//			helper.setTo(toMail);
//			helper.setSubject(title);
//			helper.setText(content,true);
//			mailSender.send(message);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	

}
