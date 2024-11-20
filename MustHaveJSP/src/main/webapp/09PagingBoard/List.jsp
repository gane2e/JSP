<%@page import="util.BoardPage"%>
<%@page import="model1.board.BoardDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="model1.board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//DB를 생성해 DB에 연결
	BoardDAO dao = new BoardDAO(application);
	
	//사용자가 입력한 검색조건을 MAP에 저장
	Map<String, Object> param = new HashMap<String, Object>();
	
	String searchField = request.getParameter("searchField");
	String searchWord = request.getParameter("searchWord");
	
	if(searchWord != null){
		param.put("searchField ", searchField );
		param.put("searchWord", searchWord );
	}
	
	int totalCount = dao.selectCount(param);
	
	//페이징 처리
	int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE")); //10
	int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK")); //15
	int totalPage = (int)Math.ceil(totalCount / (double)pageSize);   //전체 페이지 수 반올림 23
	
	
	//현재 페이지 확인
	int pageNum = 1;
	
	String pageTemp = request.getParameter("pageNum"); //NULL
	
	if(pageTemp != null && !pageTemp.equals("")) {
		pageNum = Integer.parseInt(pageTemp);
	}
	
	//각 page 출력될 범위
	int start = (pageNum -1)*pageSize +1; //1
	int end = pageNum* pageSize; //1*10=10
	
	param.put("start", start);
	param.put("end", end);
	
	List<BoardDTO> boardLists = dao.selectListPage(param);
	dao.close();
	
	System.out.println("request.getRequestURI() : " + request.getRequestURI());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원제 게시판</title>
</head>
<body>
	<jsp:include page="../Common/Link.jsp" />
	<h2>목록 보기(List) - 현재 페이지 : <%= pageNum %> (전체 : <%= totalPage %>)</h2>
	
	<!-- 검색폼 -->
	<form method="get">
	<table border="1" width="90%">
	<tr>
		<td align="center">
			<select name="searchField">
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="text" name="searchWord"/>
			<input type="submit" value="검색하기"/>
		</td>
	</tr>
	</table>
	</form>
	
	
	<!-- 게시물 목록 테이블 -->
	<table border="1" width="90%">
		<tr >
			<th width="10%">번호</th>
			<th width="50%">제목</th>
			<th width="15%">작성자</th>
			<th width="10%">조회수</th>
			<th width="15%">작성일</th>
		</tr>
		
		<%
		if(boardLists.isEmpty()){
			//게시글 없을 때
		%>
		<tr>
			<td colspan="5" align="center">
				등록된 게시물이 없습니다. ^^*
			</td>
		</tr>
		<%
		} else {
			//게시글 있을 때
			int virtualNum = 0;
			int countNum = 0;
			
			for(BoardDTO dto : boardLists){
				virtualNum = totalCount-(((pageNum -1)* pageSize) + countNum++);
		%>
			<tr align="center">
				<td><%= virtualNum %></td>
				<td align="left"><a href="View.jsp?num=<%= dto.getNum() %>"><%= dto.getTitle() %></td>
				<td><%= dto.getId() %></td>
				<td><%= dto.getVisitcount() %></td>
				<td><%= dto.getPostdate() %></td>
			</tr>
			
		<%
			}
		}
	%>
	</table>
	
	<!-- 목록 하단의 [글쓰기] 버튼  -->
	<table border = "1" width="90%">
		<tr align="center">
			<td>
				<%= BoardPage.pagingStr(totalCount, pageSize, 
						blockPage, pageNum, request.getRequestURI()) %>
				
			</td>
			<td>
				<button type="button" onclick="location.href='Write.jsp'">글쓰기</button>
			</td>
		</tr>
	</table>
</body>
</html>