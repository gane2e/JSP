package com.saeyan.controller.action;

import java.io.IOException;
import java.util.List;

import com.saeyan.dao.BoardDAO;
import com.saeyan.dto.BoardVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class BoardListAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "/board/boardList.jsp";
		
		BoardDAO bDAO = BoardDAO.getInstance();
		
		List<BoardVO> boardList = bDAO.selectAllBoards();
		
		request.setAttribute("boardList", boardList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	
}
