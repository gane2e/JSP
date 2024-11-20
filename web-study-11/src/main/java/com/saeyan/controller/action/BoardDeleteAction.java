package com.saeyan.controller.action;

import java.io.IOException;

import com.saeyan.dao.BoardDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BoardDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("num");
		
		BoardDAO bDao = BoardDAO.getInstance();
		bDao.deleteBoard(num);
		
		new BoardListAction().execute(request, response);
		
	}

}
