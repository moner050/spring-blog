<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@include file="../layout/header.jsp" %>

<table background="/images/kubrickbg.jpg" width="760" height="40" border="0" cellpadding="0" cellspacing="0">
	<tr><td height="10" colspan="10">&nbsp;</td></tr>
	<tr>
		<td height="10" width="20">&nbsp;</td>
		<td width="530" valign="top">
		<!-- 메뉴 시작 --> 
		<a href="/blog/setting"><b>기본설정</b></a>&nbsp;&nbsp;
		<a href="/blog/setting/category"><b>카테고리</b></a>&nbsp;&nbsp;
		<b>글작성</b>&nbsp;&nbsp; 
		<a href="javascript:popup();"><b>블로그삭제</b></a>&nbsp;&nbsp;
		<!-- 메뉴 끝 -->
		</td>
	</tr>
	<tr><td height="5">&nbsp;</td></tr>
	<tr><td height="10">&nbsp;</td>
		<td>
		
			<!-- 포스트 등록화면 시작 -->
			<form>
				<table width="720" border="0" cellpadding="1" cellspacing="1">
					<tr>
						<td>제목 :</td>
						<td>
							<input type="text" size="50" name="title" id="title" />
							<select id="categoryName">
								<c:forEach var="category" items="${categoryList.content }">
									<option>${category.categoryName }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>내용 :</td>
						<td colspan="10"><textarea name="content" id="content" rows="10" cols="80"></textarea></td>
					</tr>
					<tr><td height="5">&nbsp;</td></tr>
					<tr><td colspan="10" align="center">&nbsp;<button type="button" onclick="insertPost()">등록하기</button></td></tr>
				</table>
			</form> 
			<!-- 포스트 등록화면 종료 -->
	
		</td>
	</tr>
	<tr><td height="10" colspan="10">&nbsp;</td></tr>
</table>

<script>
function insertPost(){
	alert("블로그 등록중입니다...");
	
	let post = {
		title: $("#title").val(),
		categoryName: $("#categoryName").val(),
		content: $("#content").val()
	}
	
	$.ajax({
		type: "POST",
		url: "/post/insert",
		data: JSON.stringify(post),
		contentType: "application/json; charset=utf-8",
		success: function(res){
			alert(res);
			location.href="/blog";
		}
	});
}

</script>

<%@include file="../layout/footer.jsp" %>
