package com.saeyan.controller;

import com.saeyan.controller.action.Action;
import com.saeyan.controller.action.BoardCheckPassAction;
import com.saeyan.controller.action.BoardListAction;
import com.saeyan.controller.action.BoardUpdateAction;
import com.saeyan.controller.action.BoardUpdateFormAction;
import com.saeyan.controller.action.BoardViewAction;
import com.saeyan.controller.action.BoardWriteAction;
import com.saeyan.controller.action.BoardWriteFormAction;
import com.saeyan.controller.action.BoardCheckPassFormAction;
import com.saeyan.controller.action.BoardDeleteAction;

public class ActionFactory {
	
	private static ActionFactory instance = new ActionFactory();
	
	private ActionFactory() {
		super();
	}
	
	public static ActionFactory getInstance() {
		return instance;
	}
	
	//command를 받아서 그에맞는 액션을 리턴
	public Action getAction(String command) {
		Action action = null;
		System.out.println("ActionFactory : "+ command);
		
		if(command.equals("board_list")) {
			action = new BoardListAction();
		} else if(command.equals("board_write")) {
			action = new BoardWriteAction();
		} else if(command.equals("board_write_form")) {
			action = new BoardWriteFormAction();
		} else if(command.equals("board_view")) {
			action = new BoardViewAction();
			
		} else if(command.equals("board_check_pass_form")) { //boardView.jsp에서 "수정" 클릭 시 요청함
			action = new BoardCheckPassFormAction();
			
		} else if(command.equals("board_check_pass")) { //boardCheckPass.jsp에서 "확인" 클릭 시 요청함
			action = new BoardCheckPassAction();
			
		} else if(command.equals("board_update_form")) { //checkSuccess.jsp에서 "update"성공시 요청함
			action = new BoardUpdateFormAction();
		} else if(command.equals("board_update")) { //boardUpdate.jsp에서 "등록" 클릭 시 요청함
			action = new BoardUpdateAction();
		} else if(command.equals("board_delete")) //checkSuccess.jsp에서 "delete"성공시 요청함
			action = new BoardDeleteAction();
		return action;
	}
}
