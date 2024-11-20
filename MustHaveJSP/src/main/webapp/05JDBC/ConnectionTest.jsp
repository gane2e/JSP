<%@page import="common.DBConnPoll"%>
<%@page import="common.JDBConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>JDBC 테스트 2 </h2>
	<h2>커넥션 풀 테스트</h2>
	<%
		//JDBConnect jdbc1 = new JDBConnect(application);
		//jdbc1.close();
		
		DBConnPoll pool = new DBConnPoll();
		pool.close();
	%>
</body>
</html>