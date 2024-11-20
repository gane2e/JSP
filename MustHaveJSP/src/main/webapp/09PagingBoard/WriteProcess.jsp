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
BoardDTO dto = new BoardDTO();
dto.setTitle(title);
dto.setContent(content);
dto.setId(session.getAttribute("UserId").toString());

//DAO 객체를 통해 DB에 DTO 저장
BoardDAO dao = new BoardDAO(application);
int iResult = dao.insertWrite(dto);

if(iResult == 1) {
	//성공 ->PRG의해서 redirect 한다. (하지 않으면 도배)
	response.sendRedirect("List.jsp");
} else {
	JSFunction.alertBack("글쓰기에 실패하였습니다.", out);
} 

/* int iResult=0;
for(int i=0; i<=100; i++){
	dto.setTitle(title + "_" + i);
	dto.setContent(content + "_" + i);
	dao.insertWrite(dto);
}
 */
dao.close();
%>