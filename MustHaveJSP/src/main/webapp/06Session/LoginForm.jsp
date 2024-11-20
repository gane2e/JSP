<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<jsp:include page="../Common/Link.jsp"></jsp:include>

	<H2>로그인 페이지</H2>
	<span style = "color: red; font-size: 1.2em;">
		<%= request.getAttribute("LoginErrMsg") == null ? 
				"" : request.getAttribute("LoginErrMsg")
		%>
	</span>
	<%
		//session 영역에 사용자 아이디가 저장되어있는지 확인
		if (session.getAttribute("UserId") == null) {
	%>
		<script type="text/javascript">
			function validateForm(form) {
				if(!form.user_id.value) {
					alert("아이디를 입력하세요.");
					return false;
				}
				if(form.user_pw.value == "") {
					alert("비밀번호를 입력하세요.");
					return false;
				}
				
			}
		</script>
		
		<form action="LoginProcess.jsp" method="post" name="loginFrm" onsubmit="return validateForm(this)">
			아이디 : <input type="text" name="user_id"> <br>
			패스워드 : <input type="password" name="user_pw"> <br>
			<input type="submit" value="로그인하기">
		</form>
	<%
		} else {
	%>
		<%= session.getAttribute("UserName")%> 회원님, 로그인하셨습니다. <br>
		아이디는 <%= session.getAttribute("UserId")%> 입니다. <br>
		<a href="Logout.jsp">[로그아웃]</a>
	<%
		}
	%>
	
</body>
</html>