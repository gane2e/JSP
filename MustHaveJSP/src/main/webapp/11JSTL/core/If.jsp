<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>JSTL - if</title></head>
<body>
	<c:set var="number" value="100" />
	<c:set var="string" value="JSP" />
	
	<h4>JSTL의 if 태그로 짝수/홀수 판단하기</h4>
	<c:if test="${ (number mod 2) eq 0 }" var="result">
		${number}는 짝수입니다. <br>
	</c:if>
	result : ${result} <br> 
	<hr>
	
	<h4>문자열 비교와 else 구문 흉내</h4>
	<c:if test="${string eq 'Java'}" var="result2">
		문자열은 Java입니다. <br>
	</c:if>
	<c:if test="${not result2}">
		'Java'가 아닙니다.
	</c:if>
	<hr>
	<c:if test="${10 > 0}" var="re">
		10은 0보다 큽니다. : ${re} 
	</c:if>
	<hr>
	
	<h4>조건식 주의사항</h4>
	<c:if test="100" var="result3">
		el이 아닌 정수를 지정하면 flase
	</c:if>
	result3 : ${result3}
	<hr>
	
	<c:if test="tRuE" var="result4">
		대소문자 구분 없이 "tRuE"인 경우 true <br>
	</c:if>
	result: ${result4 }
</body>
</html>