package com.myspring.Art.Member.Controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.Art.Member.VO.MemberVO;

public interface MemberController {

	ResponseEntity   addMember(MemberVO _memberVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	String logout(HttpSession session) throws Exception;


}
