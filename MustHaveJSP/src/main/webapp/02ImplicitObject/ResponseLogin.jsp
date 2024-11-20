<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	
		//리퀘스트 요청해서 변수로 저장함
		String id = request.getParameter("user_id");
		String pwd = request.getParameter("user_pwd");
		
		
		// 로그인 조건
		if(id.equals("must") && pwd.equals("1234")) {
			
			// 로그인 성공 --> ResponseMain.jsp에 성공페이지를 실행하라는 요청
			response.sendRedirect("ResponseWelcome.jsp");
		
		} else {
			
			// 로그인 실패
			RequestDispatcher dis = 
			request.getRequestDispatcher("ResponseMain.jsp?loginErr=1");
			
				dis.forward(request, response);
		}
	%>
</body>
</html>