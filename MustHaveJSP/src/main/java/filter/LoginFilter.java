package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import membership.MemberDAO;
import membership.MemberDTO;
import util.JSFunction;

@WebFilter(filterName="LoginFilter", urlPatterns="/15FilterListener/LoginFilter.jsp")
public class LoginFilter implements Filter{
	
	//회원정보를 얻어오기 위해 필요한 데이터베이스 접속정보
	String oracleDriver, oracleURL, oracleId, oraclePwd;


	// /15FilterListener/LoginFilter.jsp 에서 요청 시 !! 선처리
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		ServletContext application = filterConfig.getServletContext();
		
		oracleDriver = application.getInitParameter("OracleDriver");
		oracleURL = application.getInitParameter("OracleURL");
		oracleId = application.getInitParameter("OracleId");
		oraclePwd = application.getInitParameter("OraclePwd");
	}
	
	// /15FilterListener/LoginFilter.jsp 에서 요청 시 !! 선처리 2순위
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		HttpSession session = request.getSession();
		String method = request.getMethod();
		
		
		// /15FilterListener/LoginFilter.jsp 에서 POST 요청시 : 
		if(method.equals("POST")) { //로그인 처리
			//로그인 정보와 일치하는 회원 확인
			String user_id = request.getParameter("user_id");
			String user_pw = request.getParameter("user_pw");
			
			//DB연결
			MemberDAO dao = new MemberDAO(oracleDriver, oracleURL, oracleId, oraclePwd);
			
			//유저id,pw값을 dto로 반환
			MemberDTO memberDTO = dao.getMemberDTO(user_id, user_pw);
			dao.close();
			
			if(memberDTO.getId() != null) { //일치하는 회원 존재
				
				//세션에 로그인 정보 저장
				session.setAttribute("UserId", memberDTO.getId());
				session.setAttribute("UserName", memberDTO.getName());
				
				//다음 페이지로 이동
				String backUrl = request.getParameter("backUrl");
				
				System.out.println("backUrl : " + backUrl);
				
				if(backUrl != null && !backUrl.equals("")) {
					JSFunction.alertLocation(response, "로그인 전 요청한 페이지로 이동합니다.", backUrl);
					return;
				} else {
					response.sendRedirect("../15FilterListener/LoginFilter.jsp");
				}
			} 
			else { // 일치하는 회원 없음
				request.setAttribute("LoginErrMSg", "로그인에 실패했습니다.");
				request.getRequestDispatcher("../15FilterListener/LoginFilter.jsp")
					.forward(request, response);
			}

		} 
		else if(method.equals("GET")) { //로그아웃 처리
			String mode = request.getParameter("mode");
			if(mode!=null && mode.equals("logout")) {
				session.invalidate(); // 세션 저장한 정보 삭제
			}
		}
		chain.doFilter(request, response);
	}
}
