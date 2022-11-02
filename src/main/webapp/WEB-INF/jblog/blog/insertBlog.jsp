<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>JBlog 생성</title>
	<script src="/webjars/jquery/3.6.0/dist/jquery.min.js"></script>
</head>
<body>
	<form>
	<table width="100%" height=320 border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td height=40 colspan="10">&nbsp;</td>
		</tr>
		<tr>
			<td width="100%" height="120" colspan="10" align="center">
			<a href="/"><img src="/images/logo.jpg" border="0"></a>
			</td>
		</tr>
		<tr>
			<td height="20" colspan="10" align="center" class="tdcontent">블로그 제목 : 
				<input type="text" name="title" id="title" size="40">&nbsp;&nbsp;
				<button type="button" id="btn-insert">블로그 등록</button>
			</td>
		</tr>
		<tr><td colspan="10">&nbsp;</td></tr>
	</table>
	</form>
	
<script src="/js/blog.js"></script>

</body>
</html>
