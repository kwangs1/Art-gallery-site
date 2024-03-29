<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	 isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style>
*{
margin:0; 
padding:0;
}
.loginbox{
width:330px;
height:200px;
border:1px solid #ccc;
margin:0 auto;
padding:0 auto;
bax-sizing:border-box;
}
.loginbox h1{
width:100%;
height:40px;
font-size:28px;
color:skyblue;
border-bottom:1px solid skyblue;
padding-left:40px;
box-sizing:border-box;
}
#loginid,#loginpw{
width:200px;
height:25px;
border:1px solid #999;
box-sizing:border-box;
margin-bottom:10px;
margin-top:10px;
}
.labelid{font-size:14px; color:#666; margin-left:15px; margin-right:22px;}
.labelpw{font-size:14px; color:#666; margin-left:15px; margin-right:8px;}
.search{
width:100%;
height:40px;
border-bottom:1px dashed #999;
box-sizing:border-box;
line-height:40px;
text-align:center;
margin-bottom:20px;
}
.search span{font-size:11px; color:#999;}
.search a{
font-size:12px;
text-decoration:none;
background-color:#333;
color:#fff;
padding:3px 5px;
border-radius:3px;
margin-left:5px;
}
.btnwrap{width:100%; text-align:center;}
.btnwrap label{position:absolute; left:-999em;}
.btnwrap a{
width:129px;
height:35px;
background-color:#999;
text-decoration:none;
display:inline-block;
border-radius:3px;
color:#fff;
font-size:14px;
font-weight:bold;
line-height:35px;
}
#loginbtn{
width:129px;
height:35px;
border:none;
background-color:skyblue;
border-radius:3px;
color:#fff;
font-size:14px;
font-weight:bold;
position:relative;
top:1px;
}
.logo{

text-align:center;
}
.logo img{
width : 150px; 
height : 150px;
}
.login_warn{
    text-align: center;
    color : red;
    font-size : 10pt;
}
</style>
</head>
<body>
<div class="logo">
	<img src="${contextPath }/resources/image/logo.svg" >
</div>
	<div class="loginbox">
		<h1>Login</h1>
		<form action="${contextPath }/member/login.do" method="post">
			
			<label for="loginid" class="labelid">아이디</label>
      		<input type="text" name="member_id" id="loginid" ><br>
      		<label for="loginpw" class="labelpw">비밀번호</label>
      		<input type="password" name="member_pw" id="loginpw" >
			
		<c:if test="${result == 0 }">
			<div class = "login_warn">사용자 ID 또는 비밀번호를 잘못 입력하셨습니다.</div>
		</c:if>
			
		<div class="btnwrap">
			<a href="${contextPath}/member/memberForm.do">회원가입</a>
			<input type="submit" id="loginbtn" value="로그인"  style='cursor:pointer;'/>
			<label for="loginbtn">로그인 버튼</label>
		</div>
		</form>
	</div>
	

</body>
</html>