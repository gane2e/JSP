<%@page import="util.CookieManager"%>
<%@page import="util.JSFunction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String user_id = request.getParameter("user_id");
	String user_pw = request.getParameter("user_pw");
	String save_check = request.getParameter("save_check");

	System.out.println(user_id + user_pw + save_check);
	
	if("must".equals(user_id) && "1234".equals(user_pw)) {
		if(save_check != null && save_check.equals("Y")) {
			//키값(loginId라는 쿠키를 생성)
			CookieManager.makeCookie(response, "loginId", user_id, 86400); // 하루동안 (60*60*24)
		}else {
			//체크해제시 쿠키삭제
			CookieManager.deleteCookie(response, "loginId");
		}
		
		JSFunction.alertLocation("로그인에 성공했습니다.", "IdSaveMain.jsp", out);
	}else {
		//로그인 실패
		JSFunction.alertBack("로그인에 실패했습니다.", out);
	}
	
%>