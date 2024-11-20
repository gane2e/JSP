package com.saeyan.controller.action;

import java.io.IOException;

import com.saeyan.dao.BoardDAO;
import com.saeyan.dto.BoardVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BoardViewAction implements Action{

	//게시글 View 컨트롤러
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "/board/boardView.jsp";
		String num = request.getParameter("num"); //게시글 고유키로 지정된 num값을 읽어 DB접근 후 VO객체로 값을 반환하여 전달해줌
		
		BoardDAO bDao = BoardDAO.getInstance();
		bDao.updateReadCount(num);
		
		BoardVO bVo = bDao.selectOneBoardByNum(num);
		
		request.setAttribute("board", bVo);
		
		// /board/boardView.jsp 경로로 포워딩함
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
	
}
