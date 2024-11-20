package model2.mvcboard;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import fileupload.FileUtil;

@WebServlet("/mvcboard/download.do")
public class DownloadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-------downloadcount------");
		
		//매개변수 받기
		String ofile = request.getParameter("ofile");
		String sfile = request.getParameter("sfile");
		String idx = request.getParameter("idx");
		
		//파일 다운로드
		FileUtil.download(request, response, "/Uploads", sfile, ofile);
		
		
		//다운로드 횟수 증가 처리
		MVCBoardDAO dao = new MVCBoardDAO();
		
		dao.downCountPlus(idx);
		dao.close();
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
