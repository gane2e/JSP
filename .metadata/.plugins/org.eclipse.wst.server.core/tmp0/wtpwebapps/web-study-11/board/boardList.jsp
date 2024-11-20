<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/shopping.css">
</head>
<body>
<div id="wrap" align="center">
<h1>게시글 리스트</h1>
<h1><a href="http://localhost:8080/web-study-11/BoardServlet?command=board_list">실행경로</a></h1>
<table class="list">
	<tr>
		<td colspan="5" style="border:white; text-align: right">
			<a href="BoardServlet?command=board_write_form">게시글 등록</a>
		</td>
	</tr>
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>작성일</th>
		<th>조회수</th>
	</tr>
	
	<!-- boardList NULL일시 -->
	<c:if test="${empty boardList}">
    <tr>
        <td colspan="5">게시물이 없습니다.</td>
    </tr>
	</c:if>
	
	<!-- boardList 값이 있을 시 -->
	<c:forEach var="board" items="${ boardList }">
	<tr class="record">
		<td>${ board.num }</td>
		<td>
			<a href="BoardServlet?command=board_view&num=${ board.num }">
				${ board.title }
			</a>
		</td>
		<td>${ board.name }</td>
		<td><fmt:formatDate value="${board.writedate}" /></td>
		<td>${ board.readcount }</td>
	</tr>
	</c:forEach>
</table>
</div>
</body>
</html>