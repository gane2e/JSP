package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


// @WebServlet("* ~ ") 모든 요청을 받아줌
@WebServlet("*.one")
public class ProntController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		int lastSlash = uri.lastIndexOf("/");
		
		String commandStr = uri.substring(lastSlash);
		
		if(commandStr.equals("/regist.one")) {
			System.out.println("회원 등록");
		} else if (commandStr.equals("/login.one")){
			System.out.println("회원 가입");
		} else if (commandStr.equals("/freeboard.one")) {
			System.out.println("게시판 보여주기");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
