<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	pageContext.setAttribute("name", "page man");
	request.setAttribute("name", "request man");
	session.setAttribute("name", "session man");
	application.setAttribute("name", "application man");

	
	///////
	System.out.println("01_firstPage.jsp");
	
	System.out.println("하나의 페이지 속성 : " + pageContext.getAttribute("name"));
	
	//하나의 요청에 의해 호출된 페이지와 포워드된 페이지까지 공유됨
	System.out.println("하나의 요청 속성 : " + request.getAttribute("name"));
	
	//클라이언트가 접속 한 후 브라우저를 닫을 때까지 유지됨
	System.out.println("하나의 세션 속성 : " + session.getAttribute("name"));
	
	//웹 애플리케이션이 종료될 때 까지 유지됨
	System.out.println("하나의 애플리케이션 속성 : " + application.getAttribute("name"));
	
	request.getRequestDispatcher("02_secondPage.jsp").forward(request, response);
%>