<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!-- 브라우저가 인증에 성공했는지 확인한다 -->
<sec:authorize access="isAuthenticated()">
	<!-- JSP 파일에서 사용할 인증과 관련한 변수를 초기화한다. -->
	<sec:authentication property="principal" var="principal" />
</sec:authorize>

<c:set var="ALL" value="ALL" />

<%@include file="../layout/header.jsp" %>

<table background="/images/kubrickbg.jpg" width="760" height="300" border="0" cellpadding="0" cellspacing="0">
	<tr valign="top"><td height="10">&nbsp;</td></tr>
	<tr valign="top"><td width="20">&nbsp;</td>
		<td width="530">
		
		<!-- 포스트 목록 시작 -->
		<c:if test="${!empty postList}">
			<c:forEach var="post" items="${postList.content }">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="420">
						<h3><font color="green">${post.title }</font></h3>
							<c:if test="${post.category.displayType == ALL}">
								${post.content }
							</c:if>
							<br>
						</td>
						<td align="right"><a href="/post/update/${post.postId }">수정</a> / <a href="/blog" onclick="deletePost('${post.postId }')">삭제</a></td>
					</tr>
					<tr>
						<td colspan="2" align="right">
						<font color="gray">${post.modifiedDate }</font><br>					
						</td>
					</tr>
				</table><br>
				<br>
			</c:forEach>
		</c:if>
		
		<!-- 포스트 목록 끝-->
		<ul class="pagination justify-content-start">
		  <li class="page-item <c:if test="${postList.first }">disabled</c:if> "><a class="page-link" href="?page=${postList.number - 1 }">이전 페이지</a></li>
		  <li class="page-item <c:if test="${postList.last }">disabled</c:if>"><a class="page-link" href="?page=${postList.number + 1 }">다음 페이지</a></li>
		</ul>
		</td>
		<td width="20">&nbsp;</td>
		<td width="190" valign="top">
		<!-- 로그인, 관리자 메뉴, 로고, 카테고리 시작 -->
		<table cellpadding="0" cellspacing="0">
			<tr><td height="5">&nbsp;</td></tr>
			<tr><td><img height="80" src="/images/j2eelogo.jpg" border="0"></td></tr>
			<tr><td height="5">&nbsp;</td></tr>
			<tr><td><b>카테고리</b></td></tr>
			<c:if test="${!empty categoryList }">
				<c:forEach var="category" items="${categoryList }">
				<tr>
					<td><a href="/blog/${blog.blogId }/search/${category.categoryId }"><b>${category.categoryName }</b></a></td>
				</tr>
				</c:forEach>
			</c:if>
			<tr><td height="5">&nbsp;</td></tr>
			<tr><td align="center"><a href="/"><img width="80" src="/images/logo.jpg" border="0"></a></td></tr>
		</table> 
		<!-- 로그인, 관리자 메뉴, 로고, 카테고리 끝 -->
		</td>
	</tr>
</table>
<script>
function deletePost(id){
	$.ajax({
		type: "DELETE",
		url: "/post/delete/" + id,
	}).done(function(res){
		alert(res);
		location.reload();
	});
}
</script>

<%@include file="../layout/footer.jsp" %>
