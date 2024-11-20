<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>1. 쿠키값 확인하기(쿠키가 생성된 이후의 페이지 이동)</h2>

	<%
		Cookie[] cookies = request.getCookies(); //요청 헤더의 모든 쿠키 얻기
		
		if(cookies !=null) {
			for(Cookie c : cookies) { //쿠키 각각의..
				String cookieName = c.getName(); //쿠키 이름 얻기
				String cookieValue = c.getValue(); //쿠키 값 얻기
				
				// 화면에 출력
				out.println(String.format("쿠키명 : %s - 쿠키값 : %s <br>", cookieName, cookieValue));
			}
		}
	%>
</body>
</html>