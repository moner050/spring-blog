<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!-- 브라우저가 인증에 성공했는지 확인한다 -->
<sec:authorize access="isAuthenticated()">
	<!-- JSP 파일에서 사용할 인증과 관련한 변수를 초기화한다. -->
	<sec:authentication property="principal" var="principal" />
</sec:authorize>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:set var="ROLE" value="ADMIN" />
</sec:authorize>

<sec:authorize access="hasRole('ROLE_USER')">
	<c:set var="ROLE" value="USER" />
</sec:authorize>

<c:set var="delRequest" value="삭제요청" />
<c:set var="manage" value="운영" />
<c:set var="ADMIN" value="ADMIN" />
<c:set var="USER" value="USER" />
<c:set var="TITLE" value="TITLE" />
<c:set var="TAG" value="TAG" />

<c:set var="chk" value="0" />
<c:set var="blogCnt" value="0" />

<c:forEach var="blog" items="${blogList.content }">
	<c:set var="blogCnt" value="${blogCnt + 1 }" />
	<c:if test="${principal.userId == blog.blogId}">
		<c:set var="chk" value="${chk + 1 }" />
	</c:if>
</c:forEach>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>JBlog</title>
	
<link href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
<script src="/webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
<script src="/webjars/jquery/3.6.0/dist/jquery.min.js"></script>

</head>
<body>
<center>
<!-- 검색 화면 시작 -->
<form>
	<table width="720" height=200 border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="100%" colspan="10" align="center">
				<a href="/reset"><img src="/images/logo.jpg" border="0"></a>
			</td>
		</tr>
		<tr>
		<c:if test="${searchKeyword eq TITLE || searchKeyword eq null}">
			<td class="form-check-inline">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="searchCondition" class="form-check-input" value="TITLE" checked="checked" >블로그 제목
			</td>
			<td class="form-check-inline">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="searchCondition" class="form-check-input" value="TAG" >태그
			</td>
		</c:if>
		<c:if test="${searchKeyword eq TAG }">
			<td class="form-check-inline">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="searchCondition" class="form-check-input" value="TITLE" >블로그 제목
			</td>
			<td class="form-check-inline">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="searchCondition" class="form-check-input" value="TAG" checked="checked" >태그
			</td>
		</c:if>

		</tr>				
		<tr>
			<td width="70%" colspan="2" align="center">
				<c:if test="${principal == null }">
					<a href="/auth/login" class="btn btn-success">로그인</a>&nbsp;&nbsp;
				</c:if>
				<c:if test="${principal != null }">
					<c:if test="${chk == 0}">
						<a href="/blog/insert" class="btn btn-primary">블로그등록</a>&nbsp;&nbsp;
					</c:if>
					<c:if test="${chk != 0}">
						<a href="/blog" class="btn btn-primary">블로그바로가기</a>&nbsp;&nbsp;
					</c:if>
					<a href="/auth/logout" class="btn btn-secondary">로그아웃</a>&nbsp;&nbsp;
				</c:if>
				<input type="text" id="searchKeyword" size="50" value="${searchKeyword }">
				<button type="button" onclick="searchBlog()">검색</button>
			</td>
		</tr>
	</table>
</form>
<!-- 검색 화면 종료 -->

<!-- 블로그 목록 시작 -->
<br><br>
<div id="blogList">
	<table width="550" border="0" cellpadding="1" cellspacing="1">
		<thead align="center">
			<tr bgcolor="#9DCFFF">
				<th height="30"><font color="white">블로그 제목</font></th>
				<th width="100"><font color="white">상태</font></th>
				<!-- 권한이 ADMIN인지 확인 -->
				<c:if test="${ROLE == ADMIN }">
					<th width="100"><font color="white">삭제</font></th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:if test="${!empty blogList.content}">
			<c:forEach var="blog" items="${blogList.content }">
			<tr>
				<td align="left"><a href="/blog/${blog.blogId }">${blog.title }</a></td>
				<td align="center">${blog.status }</td>
				<c:if test="${ROLE == ADMIN }">
					<c:if test="${blog.status == delRequest }">
						<td align="center"><button onclick="deleteBlog('${blog.blogId }')"><img height="9" src="images/delete.jpg" border="0"></button></td>
					</c:if>
					<c:if test="${blog.status == manage }">
						<td align="center">-</td>
					</c:if>
				</c:if>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty blogList.content}">
				<tr>
					<td align="center">블로그가 존재하지 않습니다.</td>
					<td align="center"></td>
					<td align="center"></td>
				</tr>
			</c:if>
		</tbody>
	</table>
</div>

<br><br>
<ul class="pagination justify-content-center">
	  <li class="page-item <c:if test="${blogList.first }">disabled</c:if> "><a class="page-link" href="?page=${blogList.number - 1 }">이전 페이지</a></li>
	  <li class="page-item <c:if test="${blogList.last }">disabled</c:if>"><a class="page-link" href="?page=${blogList.number + 1 }">다음 페이지</a></li>
</ul>
<!-- 블로그 목록 종료 -->
</center>

<script>
function searchBlog(){
	alert("검색요청 되었습니다.")
	
	let blog = {
		searchCondition: $("input:radio[name=searchCondition]:checked").val(),
		searchKeyword: $("#searchKeyword").val()
	}
	
	$.ajax({
		type: "GET",
		url: "/search",
		data: blog
	}).done(function(res){
		alert(res);
		$('#blogList').load(location.href + ' #blogList');
	});
}

</script>
<script>
function deleteBlog(id){
	alert("블로그 삭제중입니다...");
	
	$.ajax({
		type: "DELETE",
		url: "/delete/" + id,
		success: function(res){
			alert(res)
			location.reload();
		}
	})
}

</script>
