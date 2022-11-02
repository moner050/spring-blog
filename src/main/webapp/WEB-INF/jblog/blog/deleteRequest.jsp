<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog 블로그 삭제요청</title>
<script src="/webjars/jquery/3.6.0/dist/jquery.min.js"></script>
<script>
function deleteAgree(id){
	$.ajax({
		type: "POST",
		url: "/blog/delete/" + id,
		success: function() {
			opener.location.href = '/auth/logout';
			window.close();
		}
	});
}
</script>
</head>
<body>
<center>
	<form>
	<table width="450" height="100" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="50" rowspan="3" align="center"><img src="/images/worning.jpg" border="0"></td>
			<td width="350"><b><font color="red">블로그 삭제를 관리자에게 요청하시겠습니까 ?</font></b><br></td>
		</tr>
		<tr>
			<td width="350"><br>
			<br> 동의하시면 관리자 확인후 삭제 처리 됩니다. 
			<br>그리고 블로그 내의 모든 데이터는 <font color="red">삭제</font>됩니다.</td>
		</tr>
		<tr>
			<td colspan="2" align="center"><br> 
			<button type="button" onclick="deleteAgree('${blog.blogId}')" >동의</button> 
			<input type="button" value="취소" onclick="window.close();" />
			</td>
		</tr>
	</table>
	</form>
</center>
</body>
</html>


