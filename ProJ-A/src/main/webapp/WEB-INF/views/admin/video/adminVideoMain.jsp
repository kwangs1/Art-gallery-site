<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<html>
<head>

<meta charset="utf-8">
<style>
* {
	margin: 0 auto;
	padding: 0 auto;
}

.list_view {
	width: 40%;
	color: rgb(51, 51, 51);
	font-size: 0.8em;
	margin-top: 20px;
	border-top-color: rgb(51, 51, 51);
	border-top-width: 2px;
	border-top-style: solid;
	border-collapse: collapse;
}

.list_view tr {
	margin: 0px;
	padding: 10px 0px;
	border-bottom-color: rgb(153, 153, 153);
	border-bottom-width: 1px;
	border-bottom-style: dotted;
}

.list_view td {
	margin: 0px;
	padding: 10px 0px;
	border-bottom-color: rgb(153, 153, 153);
	border-bottom-width: 1px;
	border-bottom-style: dotted;
	border: 1px solid #444444;
}

#search {
	width: 100px;
	margin: auto;
	display: block;
}

.list_view td a:hover {
	text-decoration-line: underline;
}

#search a:hover {
	text-decoration-line: underline;
}

a {
	text-decoration-line: none;
	color:black;
}

a:hover {
	color: silver;
}
.page_wrap {
	text-align:center;
	font-size:0;
 }
.page_nation {
	display:inline-block;
}
.page_nation .none {
	display:none;
}
.page_nation a {
	display:block;
	margin:0 3px;
	float:left;
	border:1px solid #e6e6e6;
	width:28px;
	height:28px;
	line-height:28px;
	text-align:center;
	background-color:#fff;
	font-size:13px;
	color:#999999;
	text-decoration:none;
}

</style>
</head>
<body>
<form method="get">
	<h1></h1>
	<div class="search" align="center">
    <select name="searchType">
      <option value="n"<c:out value="${scri.searchType == null ? 'selected' : ''}"/>>-----</option>
      <option value="t"<c:out value="${scri.searchType eq 't' ? 'selected' : ''}"/>>제목</option>
      <option value="c"<c:out value="${scri.searchType eq 'c' ? 'selected' : ''}"/>>내용</option>
      <option value="tc"<c:out value="${scri.searchType eq 'tc' ? 'selected' : ''}"/>>제목+내용</option>
    </select>

    <input type="text" name="keyword" id="keywordInput" value="${scri.keyword}"/>

    <button id="searchBtn" type="button">검색</button>

  </div>
	<DIV class="clear"></DIV>
	<TABLE class="list_view">
		<TBODY align=center>
			<tr>
				<td style="width:40%;">제목</td>
				<td style=" background-color:#D6E6F0; width:60%;">내용</td>
				<td></td>
			</tr>
			<c:choose>
				<c:when test="${empty list }">
					<TR>
						<TD colspan=8 class="fixed"><strong>조회된 상품이 없습니다.</strong></TD>
					</TR>
				</c:when>
				<c:otherwise>
					<c:forEach var="item" items="${list }">
						<TR>
							<TD><strong>${item.v_title} </strong></TD>
							<TD><a
								href="${contextPath}/admin/video/adminVideoDetail.do?vno=${item.vno}"
								style='cursor: pointer; font-size: 1em; font-weight: 700; color:#A3CCA3;'>
									<strong>${item.v_note}</strong>
							</a></TD>
							<td style="width: 40px;">	
							<input type=button value="삭제" style='cursor: pointer; border:none;'
								onClick="fn_remove_video('${contextPath}/admin/video/removeVideo.do', ${item.vno})">
								&#9;
							<a style='cursor:pointer;' 
   								href='<c:url value="/admin/video/modifyVideoForm.do?vno=${item.vno }
   											&page=${scri.page }&perPageNum=${scri.perPageNum }"/>'> 수정</a>
							</td>
						</TR>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</TBODY>

	</TABLE>
			<div class="page_wrap">
  				<div class="page_nation">
				<c:if test="${pageMaker.prev }">
					<a  href='<c:url value="/admin/video/adminVideoMain.do${pageMaker.makeSearch(pageMaker.startPage - 1) }"/>'>
					<i class="fa fa-chevron-left"></i>◀</a>
				</c:if>
				<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="pageNum">
					<a href='<c:url value="/admin/video/adminVideoMain.do${pageMaker.makeSearch(pageNum)}"/>'><i
							class="fa">${pageNum }</i></a>
				</c:forEach>
				<c:if test="${pageMaker.next && pageMaker.endPage >0 }">
					<a href='<c:url value="/admin/video/adminVideoMain.do${pageMaker.makeSearch(pageMaker.endPage + 1)}"/>'>
					<i class="fa fa-chevron-right">▶</i></a>
				</c:if>
			</div>
			</div>
	<DIV id="search">
		<a href="${contextPath}/admin/video/addNewVideoForm.do"
			style='cursor: pointer; font-size: 1.5em; font-weight: 700; color: #000;'>등록하기</a>
	</DIV>
</form>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
function fn_remove_video(url,vno){
	var DelConfirm = confirm('삭제 하시겠습니까?');
	
	
	var paramData = {"vno" : vno};

	if(DelConfirm){	
		alert("삭제 되었습니다.");
	}else{
		alert("삭제가 취소 되었습니다.");
		return;
	}
	 var form = document.createElement("form");
	 form.setAttribute("method", "get");
	 form.setAttribute("action", url);
    var VideoInput = document.createElement("input");
    VideoInput.setAttribute("type","hidden");
    VideoInput.setAttribute("name","vno");
    VideoInput.setAttribute("value", vno);
	 
    form.appendChild(VideoInput);
    document.body.appendChild(form);
    form.submit();

}

$(function(){
    $('#searchBtn').click(function() {
      self.location = "adminVideoMain.do" + '${pageMaker.makeQuery(1)}' + "&searchType=" + $("select option:selected").val() + "&keyword=" + encodeURIComponent($('#keywordInput').val());
    });
  }); 
</script>

</body>
</html>