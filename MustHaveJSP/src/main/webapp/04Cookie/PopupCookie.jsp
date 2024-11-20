<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	System.out.println("----------PopupCookie.jsp-----------");
	String chkVal = request.getParameter("inactiveToday");

	if(chkVal != null & chkVal.equals("1")){
		Cookie cookie = new Cookie("PopupClose", "off");
		cookie.setPath(request.getContextPath());
		cookie.setMaxAge(60*60*24); //(60초*60분*24)
		response.addCookie(cookie); //쿠키를 저장
		out.println("쿠키 : 하루 동안 열지 않음");
		
	}
%>