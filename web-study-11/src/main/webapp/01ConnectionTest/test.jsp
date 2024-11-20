<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="util.DBManager"%>
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
		
		Connection conn = null;
        Statement stmt = null;

        try {
            conn = DBManager.getConnenction();
            if (conn != null) {
                out.println("<p>커넥션 연결 성공!</p>");
            } else {
                out.println("<p>커넥션 연결 실패!</p>");
            }
        } catch (Exception e) {
            out.println("<p>오류: " + e.getMessage() + "</p>");
        } finally {
            DBManager.close(conn, stmt);
        }
	%>
</body>
</html>
