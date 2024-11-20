<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>JSTL - forEach 1</title></head>
<body>
	<h4>일반 for문 형태의 forEach 태그</h4>
	<c:forEach begin="1" end="10" step="1" var="i">
		<p>반복 ${i} 입니다.</p>
	</c:forEach>
	<hr>
	
	<h3>varStatus 속성 살펴보기</h3>
	

	<!-- step : 증가값 -->
	<c:forEach begin="1" end="5" var="i" step="2" varStatus="loop">
	 <table border="1">
		<tr>
			<td>count : ${loop.count}</td>
			<td>index: ${loop.index}</td>
			<td>current : ${loop.current}</td><!-- var에 지정한 현재 루프의 변숫값 반환 -->
			<td>first : ${loop.first}</td>
			<td>last : ${loop.last}</td>
		</tr>
	</table>
	</c:forEach>
	
	
	<h4>1에서 100까지 정수 중 홀수의 합</h4>
	<c:forEach begin="1" end="100" var="j">
		<c:if test="${ j mod 2 ne 0 }">
			<c:set var="sum" value="${ sum + j }" />
		</c:if>
	</c:forEach>
	1~100 사이의 정수 중 홀수의 합은? ${ sum }
	
	
</body>
</html>