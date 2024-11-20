package model2.mvcboard;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/*
 * --모델2 방식의 파일첨부형 게시판 테이블 생성
	create table mvcboard (
	   idx number primary key, 
	   name varchar2(50) not null, 
	   title varchar2(200) not null, 
	   content varchar2(2000) not null, 
	   postdate date default sysdate not null, 
	   ofile varchar2(200), 
	   sfile varchar2(30), 
	   downcount number(5) default 0 not null, 
	   pass varchar2(50) not null, 
	   visitcount number default 0 not null
	);
 */

@Getter
@Setter
@ToString
public class MVCBoardDTO {

		private String idx;
		private String name;
		private String title;
		private String content;
		private Date postdate;
		private String ofile; 
		private String sfile;
		private int downcount;
		private String pass;
		private int visitcount;
	}
	
