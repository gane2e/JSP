package com.saeyan.controller.action;

import java.io.IOException;

import com.saeyan.dao.BoardDAO;
import com.saeyan.dto.BoardVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BoardCheckPassAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = null;
		String num = request.getParameter("num");
		String pass = request.getParameter("pass");
		
		BoardDAO bDao = BoardDAO.getInstance();
		BoardVO bVo = bDao.selectOneBoardByNum(num);
		
		if(bVo.getPass().equals(pass)) {
			url = "/board/checkSuccess.jsp";
		} else {
			url = "/board/boardCheckPass.jsp";
			request.setAttribute("message","비밀번호가 틀렸습니다.");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}
	
}
