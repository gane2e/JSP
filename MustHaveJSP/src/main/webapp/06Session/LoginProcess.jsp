<%@page import="membership.MemberDTO"%>
<%@page import="membership.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String userId = request.getParameter("user_id");
    String userPwd = request.getParameter("user_pw");
    
    //DB연결정보
    String oracleDriver = application.getInitParameter("OracleDriver");
    String oracleURL = application.getInitParameter("OracleURL");
    String oracleId = application.getInitParameter("OracleId");
    String oraclePwd = application.getInitParameter("OraclePwd");
    
    // MemberDAO 객체를 생성해 db연결
    MemberDAO dao = new MemberDAO(oracleDriver, oracleURL, oracleId, oraclePwd);
    
    //id,pass 전달해서 회원정보 획득
    MemberDTO memberDTO = dao.getMemberDTO(userId, userPwd);
    dao.close(); //DB 연결 종료
    
    
    //로그인 결과처리
    if(memberDTO.getId() != null) {
    	//로그인 성공
    	session.setAttribute("UserId", memberDTO.getId());
    	session.setAttribute("UserName", memberDTO.getName());
    	
    	//브라우저가 재요청
    	response.sendRedirect("LoginForm.jsp");
    	
    	
    } else {
    	
    	//로그인 실패
    	request.setAttribute("LoginErrMsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
    	
    	//포워드
    	request.getRequestDispatcher("LoginForm.jsp").forward(request, response);
    }
    %>
