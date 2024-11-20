<%@page import="model1.board.BoardDAO"%>
<%@page import="model1.board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./IsLoggedIn.jsp" %>

<%
//폼값 받기
String title = 	request.getParameter("title");
String content =  request.getParameter("content");

//폼값을 DTO객체에 저장
BoardDTO boardDTO = new BoardDTO();
boardDTO.setTitle(title);
boardDTO.setContent(content);
boardDTO.setId(session.getAttribute("UserId").toString());

//DAO 객체를 통해 DB에 DTO 저장
BoardDAO dao = new BoardDAO(application);
int iResult = dao.insertWrite(boardDTO);

if(iResult == 1) {
	//성공 ->PRG의해서 redirect 한다. (하지 않으면 도배)
	response.sendRedirect("List.jsp");
} else {
	JSFunction.alertBack("글쓰기에 실패하였습니다.", out);
}

dao.close();
%>