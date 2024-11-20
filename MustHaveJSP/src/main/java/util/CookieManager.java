package util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 쿠키 관리자
public class CookieManager {
	
	//명시한 이름, 값, 유지 기간 조건으로 새로운 쿠키를 생성합니다.
	public static void makeCookie(HttpServletResponse reponse, String cName, String cValue, int CTime) {
		
		Cookie cookie = new Cookie(cName, cValue) ; //쿠키 생성
		
		cookie.setPath("/"); // 경로 설정
		cookie.setMaxAge(CTime); // 유지 기간 설정
		reponse.addCookie(cookie); // 응답 객체에 추가
	}
	
	//쿠키 -> key값으로 value 찾기 (이름으로 값 찾기)
	public static String readCookie(HttpServletRequest request, String cName) {
		String cookieValue= "";
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie c : cookies) {
				if(c.getName().equals(cName)) {
					cookieValue = c.getValue();
				}
			}
		}
		return cookieValue;
	}
	
	
	
	// 쿠키 삭제
	public static void deleteCookie(HttpServletResponse response, String cName) {
		makeCookie(response, cName, "", 0); //쿠키 삭제시에는 시간을 0값 주면 삭제가 된다.
	}

}
