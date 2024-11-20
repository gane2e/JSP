package model2.mvcboard;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.JSFunction;

import java.io.IOException;

import fileupload.FileUtil;

public class WriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/14MVCBoard/Write.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String saveDirectory = request.getServletContext().getRealPath("/Uploads");

		System.out.println("WriteController - saveDirectory : " + saveDirectory);
		
		//파일 업로드
		String originalFileName = "";
		try {
			originalFileName = FileUtil.uploadFile(request, saveDirectory);
		} catch (Exception e) {
			//파일 업로드 실패
			JSFunction.alertLocation(response, "파일 업로드 오류입니다.", "../mvcboard/write.do");
			return;
		}
		
		// 2. 파일 업로드 외 처리
		// 폼 값을 DTO에 저장
		MVCBoardDTO dto = new MVCBoardDTO();
		dto.setName(request.getParameter("name"));
		dto.setTitle(request.getParameter("title"));
		dto.setContent(request.getParameter("content"));
		dto.setPass(request.getParameter("pass"));
		
		//원본 파일명과 저장된 파일 이름 설정
		//파일이 첨부되있으면 실행되는 블럭
		if(originalFileName != "") {
			// 첨부 파일이 있을 경우 파일 이름 설정
			String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);
			
			dto.setOfile(originalFileName); //원래 파일 이름
			dto.setSfile(savedFileName); //서버에 저장된 파일 이름
		}
		
		//DAO를 통해 DB에 게시 내용 저장
		MVCBoardDAO dao = new MVCBoardDAO();
		int result = dao.insertWrite(dto);
		dao.close();
		
		//성공 or 실패?
		if(result == 1) {
			response.sendRedirect("../mvcboard/list.do");
		} else {
			JSFunction.alertLocation(response, "글쓰기에 실패했습니다.", "../mvcboard/write.do");
		}
	}

}
