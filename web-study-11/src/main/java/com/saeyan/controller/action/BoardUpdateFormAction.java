package com.saeyan.controller.action;

import java.io.IOException;

import com.saeyan.dao.BoardDAO;
import com.saeyan.dto.BoardVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BoardUpdateFormAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "/board/boardUpdate.jsp";
		String num = request.getParameter("num");
		BoardDAO bDao = BoardDAO.getInstance();
		bDao.updateReadCount(num);
		
		BoardVO bVo = bDao.selectOneBoardByNum(num);
		
		request.setAttribute("board", bVo);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
