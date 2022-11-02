<%@page contentType="text/html; charset=UTF-8" %>

<%@include file="../layout/header.jsp" %>

<c:set var="basic" value="미분류" />
<c:set var="ALL" value="ALL" />

<table background="/images/kubrickbg.jpg" width="760" height="40" border="0" cellpadding="0" cellspacing="0">
	<tr><td height="10" colspan="10">&nbsp;</td></tr>
	<tr><td height="10" width="20">&nbsp;</td>
		<td width="530" valign="top">
			<!-- 메뉴 시작 --> 
			<a href="/blog/setting"><b>기본설정</b></a>&nbsp;&nbsp;
			<b>카테고리</b>&nbsp;&nbsp;
			<a href="/post/insert"><b>글작성</b></a>&nbsp;&nbsp; 
			<a href="javascript:popup();"><b>블로그삭제</b></a>&nbsp;&nbsp;
			<!-- 메뉴 끝 -->
		</td>
	</tr>
	<tr><td height="5">&nbsp;</td></tr>
	<tr><td height="10">&nbsp;</td><td>
		<!-- 작업 화면  시작 -->
		<table width="720" border="0" cellpadding="1" cellspacing="1">
			<tr bgcolor="#9DCFFF">
				<th width="50"><font color="white">번호</font></th>
				<th width="120"><font color="white">카테고리명</font></th>
				<th width="100"><font color="white">보이기 유형</font></th>
				<th width="350"><font color="white">설명</font></th>
				<th width="100"><font color="white">삭제</font></th>
			</tr>
			<c:if test="${!empty categoryList }">
				<c:forEach var="category" items="${categoryList.content }">
				<tr>
					<td align="center">${category.categoryId }</td>
					<c:if test="${category.categoryName == basic}">
						<td>${category.categoryName }</td>
					</c:if>
					<c:if test="${category.categoryName != basic}">
						<td><a href="/blog/setting/category/update/${category.categoryId }">${category.categoryName }</a></td>
					</c:if>
					<c:if test="${category.displayType == ALL }">
						<td align="center">제목 + 내용</td>
					</c:if>
					<c:if test="${category.displayType != ALL }">
						<td align="center">제목</td>
					</c:if>
					<td>${category.description }</td>
					<c:if test="${category.categoryName == basic  }">
							<td align="center">&nbsp;삭제불가</td>
					</c:if>
					<c:if test="${category.categoryName != basic  }">
							<td align="center">
								<button type="button" onclick="deleteCategory('${category.categoryId }')">
									<img height="9" src="/images/delete.jpg" border="0">
								</button>
							</td>
					</c:if>
				</tr>
				</c:forEach>
			</c:if>
		</table> 
			
			<!-- 카테고리 등록화면 시작 -->
			<c:if test="${empty category }">
			<form>
			<table width="720" border="0" cellpadding="1" cellspacing="1">
				<tr><td height="5">&nbsp;</td></tr>
				<tr><td height="5">&nbsp;</td></tr>
				<tr><td height="5"><b>카테고리 등록</b></td>	</tr>
				<tr><td height="5">&nbsp;</td></tr>
				<tr>
					<td width="150">카테고리명 :</td>
					<td><input type="text" size="40" name="categoryName" id="categoryName"></td>
				</tr>
				<tr>
					<td width="150">보이기 유형 :</td>
					<td><input type="radio"	name="displayType" value="TITLE" checked>제목&nbsp;&nbsp;
						<input type="radio" name="displayType" value="ALL">제목 + 내용&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td width="150">설명 :</td>
					<td><input type="text" size="50" name="description" id="description"></td>
				</tr>
				<tr>
					<td colspan="10" align="center">&nbsp;<button type="button" onclick="insertCategory()">카테고리 추가</button></td>
				</tr>
			</table>
			</form>
			</c:if>
			<!-- 카테고리 등록화면 종료 -->

		
			<!-- 카테고리 수정화면 시작 -->
			<c:if test="${!empty category }">
			<input type="text" id="categoryId" value="${category.categoryId }" style="display:none"/>
			<form>
			<table width="720" border="0" cellpadding="1" cellspacing="1">
				<tr><td height="5">&nbsp;</td></tr>
				<tr><td height="5">&nbsp;</td></tr>
				<tr><td height="5"><b>카테고리 수정</b></td></tr>
				<tr><td height="5">&nbsp;</td></tr>
				<tr>
					<td width="150">카테고리명 :</td>
					<td><input type="text" size="40" id="categoryName" value="${category.categoryName }"></td>
				</tr>
				<tr>
					<td width="150">보이기 유형 :</td>
						<c:if test="${category.displayType == ALL }">
						<td>
							<input type="radio"	name="displayType" value="TITLE">제목&nbsp;&nbsp;
							<input type="radio" name="displayType" value="ALL" checked>제목 + 내용&nbsp;&nbsp;
						</td>
						</c:if>
						<c:if test="${category.displayType != ALL }">
						<td>
							<input type="radio"	name="displayType" value="TITLE" checked>제목&nbsp;&nbsp;
							<input type="radio" name="displayType" value="ALL">제목 + 내용&nbsp;&nbsp;
						</td>
						</c:if>
				</tr>
				<tr>
					<td width="150">설명 :</td>
					<td><input type="text" size="50" id="description" value="${category.description }"></td>
				</tr>
				<tr>
					<td colspan="10" align="center">
						&nbsp;<button type="button" onclick="updateCategory()">카테고리 수정</button>
					</td>
				</tr>
			</table>
			</form>
			</c:if>
			<!-- 카테고리 수정화면 종료 -->

			
		</td>
	</tr>
	<tr><td height="10" colspan="10">&nbsp;</td></tr>
</table>

<script>
function insertCategory(){
	alert("카테고리 등록중입니다.")
	
	let category = {
		categoryName: $('#categoryName').val(),
		displayType: $("input:radio[name=displayType]:checked").val(),
		description: $('#description').val()
	}
	
	$.ajax({
		type: "POST",
		url: "/blog/setting/category/insert",
		data: JSON.stringify(category),
		contentType: "application/json; charset=utf-8",
		success: function(res){
			alert(res);
			location.href="/blog/setting/category"
		}
	});
}
</script>
<script>
function updateCategory(){
	alert("카테고리 수정중입니다.")
	
	let category = {
		categoryId: $('#categoryId').val(),
		categoryName: $('#categoryName').val(),
		displayType: $("input:radio[name=displayType]:checked").val(),
		description: $('#description').val()
	}
	
	$.ajax({
		type: "POST",
		url: "/blog/setting/category/update",
		data: JSON.stringify(category),
		contentType: "application/json; charset=utf-8",
		success: function(res){
			alert(res);
			location.href="/blog/setting/category"
		}
	});
}
</script>
<script>
function deleteCategory(id){
	alert("카테고리 삭제중입니다.")
	
	$.ajax({
		type: "DELETE",
		url: "/blog/setting/category/delete/" + id,
		success: function(res){
			alert(res);
			location.href="/blog/setting/category";
		}
	});
}
</script>
		
<%@include file="../layout/footer.jsp" %>