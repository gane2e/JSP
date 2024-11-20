<%@page import="model1.board.BoardDAO"%>
<%@page import="model1.board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./IsLoggedIn.jsp" %>
<%
	String num = request.getParameter("num");
	
	BoardDAO dao = new BoardDAO(application);
	
	BoardDTO dto = dao.selectView(num);
	
	String sessionId = session.getAttribute("UserId").toString();
	
	if(sessionId.equals(dto.getId())) {
		int iResult = dao.deletePost(dto.getNum());
		
		//성공/실패처리
		if(iResult == 1) {
			JSFunction.alertLocation("삭제되었습니다.", "List.jsp", out);
			
		} else {
			JSFunction.alertBack("삭제 실패하였습니다.", out);
		} 
	} else {
		JSFunction.alertBack("본인만 삭제할 수 있습니다.", out);
	}
	
	
%>