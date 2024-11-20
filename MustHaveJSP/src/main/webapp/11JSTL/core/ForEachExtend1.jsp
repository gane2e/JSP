<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>JSTL - forEach2</title></head>
<body>
	<h4>향상된 for문 형태의 forEach 테크 ?</h4>
	<%
	String [] rgba = {"Red", "Green", "Blue", "Black"};
	%>
	
	<c:forEach items="rgba" var="color">
		<span style="color: ${color}">${color}></span>
	</c:forEach>
	<hr>
	
	<h4>varStatus 속성 살펴보기</h4>
	<table border="1">
		<c:forEach items="<%= rgba %>" var="color" varStatus="loop">
		<tr>
		</tr>
		</c:forEach>
	</table>
	
</body>
</html>
