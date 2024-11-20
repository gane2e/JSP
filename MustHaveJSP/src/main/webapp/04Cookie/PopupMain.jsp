<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String popupMode = "on";

	Cookie[] cookies = request.getCookies();
	
	if(cookies != null) {
		for(Cookie c : cookies) {
			
			String cookieName = c.getName();
			String cookieValue = c.getValue();
			
			if(cookieName.equals("PopupClose")) {
				popupMode = cookieValue;
			}
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	div#popup{
		position: absolute; top:100px; left:50px; color:yellow;
		width:270px; height:100px; background-color:gray;
	}	
	div#popup > div {
		position: relative; background-color:#fff; top:0px;
		border: 1px solid gray; padding:10px; color:blcak;
	}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">

	$(function(){
		$("#closeBtn").click(function(){ //닫기 버튼을 누르면 실행되는 함수
			$("#popup").hide();
			
			// 체크박스가 체크되어있다면 id="inactiveToday" 체크박스의 value(1)값이 반환됨
			let chkVal = $("input:checkbox[id=inactiveToday]:checked").val(); //체크여부 확인
			console.log("chkVal : " + chkVal);
			if(chkVal == 1) {
				$.ajax({
					url : './PopupCookie.jsp',
					type : 'get',
					data : {inactiveToday : chkVal},
					dataType : 'text',
					success : function(resData){ // 요청 성공 시 호출되는 함수
						if(resData != '')location.reload();
					}
				})
			}
		})
	});
</script>
</head>

<body>

	<h2>팝업 메인 페이지(ver1.0)</h2>
	<%
		for(int i=1; i<=10; i++) {
			out.print("현재 팝업창은" + popupMode + "상태입니다. <br>"); 
		}
	
	// String popupMode가 on이면 ..
		if(popupMode.equals("on")) {
	%>		
				<div id="popup">
					<h2 align="center">공지사항 팝업입니다.</h2>
					<div align="right">
						<form name="fopFrm">
							<input type="checkbox" id="inactiveToday" value="1" />
							하루 동안 열지 않음 
							<input type="button" id="closeBtn" value="닫기" />
						</form>
					</div>
				</div>
	<%
		}
	%>
</body>
</html>