package servlet;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import membership.MemberDAO;
import membership.MemberDTO;

import java.io.IOException;

public class MemberAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MemberDAO dao;
	
	@Override
	public void init() throws ServletException {
		
		// app 내장객체 얻기
		ServletContext app = this.getServletContext();
		
		// web.xml에서 DB 연결 정보 얻기
		String driver = app.getInitParameter("OracleDriver");
		String connectUrl = app.getInitParameter("OracleURL");
		String oId = app.getInitParameter("OracleId");
		String oPass = app.getInitParameter("OraclePwd");
		
		dao = new MemberDAO(driver, connectUrl, oId, oPass);
	}

	@Override
	public void destroy() {
		dao.close();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//서블릿 초기화 매개변수에서 관리자 ID 받기
		String admin_id = this.getInitParameter("admin_id");
		
		//인증을 요청한 ID/패스워드
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		
		//회원 테이블에서 인증 요청한 ID/패스워드에 해당하는 회원 찾기
		MemberDTO memberDTO = dao.getMemberDTO(id, pass);
		
		String memberName = memberDTO.getName();
		
		if(memberName != null) {
			request.setAttribute("authMessage", memberName + "회원님 반갑습니다.");
		} else {
			if(admin_id.equals(id) ) {
				request.setAttribute("authMessage", admin_id + "는 관리자 입니다.");
			} else {
				request.setAttribute("authMessage", "귀하는 비회원 입니다.");
			}
		}
		
		request.getRequestDispatcher("/12Servlet/MemberAuth.jsp").forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
