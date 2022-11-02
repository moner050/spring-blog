<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog 로그인</title>
<link href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
<script src="/webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
<script src="/webjars/jquery/3.6.0/dist/jquery.min.js"></script>
</head>
<body>
	<form action="/auth/login" method="post">
		<br><br>
		<div width="100%" height="120" colspan="10" align="center">
			<a href="/"><img src="/images/logo.jpg" border="0"></a>
		</div>
		<div class="mx-5 px-5">
			<label for="username" class="form-label">아이디:</label> 
			<input type="text" class="form-control" id="username" placeholder="아이디를 입력하세요" name="username" value="aaa">
		</div>
		<div class="mx-5 my-4 px-5">
			<label for="password" class="form-label">패스워드:</label> 
			<input type="password" class="form-control" id="password" placeholder="비밀번호를 입력하세요" name="password" value="aaa">
		</div>
		<div class="container row" style="float: none; margin:0 auto;">
			<input class="btn btn-success" type="submit" value="로그인">
		</div>
	</form>
</body>
</html>
