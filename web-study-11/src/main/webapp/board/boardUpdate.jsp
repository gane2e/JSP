<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<script type="text/javascript" src="script/board.js"></script>
</head>
<body>
	<div id="wrap" align="center">
		<h1>게시글 수정</h1>
		
		<form name="frm" method="post" action="BoardServlet"> <!-- "sumbit" 요청 시 BoardServlet 에 command 값 들고 찾아가서 action 요청함 -->
		
		<input type="hidden" name="command" value="board_update"> <!-- command : board_update  / POST 전송 -->
		<input type="hidden" name="num" value="${ board.num }"> <!-- num : board.num  / POST 전송--> 
		
		<!-- ${board.num} : BoardViewAction.java에서 num값 찾아 DB접근후 VO객체로 행 받아서 "board" 속성에 넣어줌 -->
		
		<table>
		<tr>
			<th>작성자</th>
			<td><input type="text" size="12" name="name" value="${ board.name }"> * 필수</td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="password" size="12" name="pass"> * 필수(게시물 수정 삭제시 필요합니다.)</td>
		</tr>
		<tr>
			<th>이메일</th>
			<td><input type="text" size="40" maxlength="50" name="email" value="${ board.email }"></td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input type="text" size="70" name="title" value="${ board.title }"></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea cols="70" rows="15" name="content">${ board.content }</textarea></td>
		</tr>
		</table>
		<br><br>
		<input type="submit" value="등록" onclick="return boardCheck()">
		<input type="reset" value="다시 작성"">
		<input type="button" value="목록"
				onclick="location.href='BoardServlet?command=board_list'">
		</form>
	</div>
</body>
</html>