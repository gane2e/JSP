package com.saeyan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.saeyan.dto.BoardVO;

import util.DBManager;

public class BoardDAO {

	private BoardDAO() {
	}
	
	//싱글톤 패턴
	//정의: 클래스의 인스턴스가 오직 하나만 생성되도록 보장하며, 그 인스턴스에 대한 전역 접근을 제공합니다.
	private static BoardDAO instance = new BoardDAO();
	
	public static BoardDAO getInstance() {
		return instance;
	}
	
	public List<BoardVO> selectAllBoards(){
		
		String sql = "select * from board2 order by num desc";
		
		List<BoardVO> list = new ArrayList<BoardVO>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBManager.getConnenction();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				BoardVO bVo = new BoardVO();
				
				bVo.setNum(rs.getInt("num"));
				bVo.setName(rs.getString("name"));
				bVo.setEmail(rs.getString("email"));
				bVo.setPass(rs.getString("pass"));
				bVo.setTitle(rs.getString("title"));
				bVo.setContent(rs.getString("content"));
				bVo.setReadcount(rs.getInt("readcount"));
				bVo.setWritedate(rs.getTimestamp("writedate"));
				
				list.add(bVo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, stmt, rs);
		}
		return list;
	}
	
	
	
	public void insertBoard(BoardVO bVo) {
		String sql = "insert into board2("
				+ "num, name, email, pass, title, content)"
				+ "values(board_seq.nextval, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBManager.getConnenction();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bVo.getName());
			pstmt.setString(2, bVo.getEmail());
			pstmt.setString(3, bVo.getPass());
			pstmt.setString(4, bVo.getTitle());
			pstmt.setString(5, bVo.getContent());
			
			pstmt.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("게시물 insert 오류");
		} finally {
			DBManager.close(conn, pstmt);
		}
	}
	
	
	//조회수 증가
	public void updateReadCount(String num) {
		String sql = "update board2 set readcount = readcount+1 where num=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBManager.getConnenction();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("조회수 증가 오류");
		} finally {
			DBManager.close(conn, pstmt);
		}
	}
	
	
	//게시판 글 상세 내용 보기 : 글번호로 찾아온다.  : 실패  null
	public BoardVO selectOneBoardByNum(String num) {
		String sql = "select * from board2 where num=?";
		
		BoardVO bVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			 conn = DBManager.getConnenction();
			 pstmt = conn.prepareStatement(sql);
			 
			 //인파라미터값 전달
			 pstmt.setString(1, num);
			 
			 //쿼리돌려서 rs에 결과값 반환 (num에 해당하는 행 찾아 컬럼+속성반환함)
			 rs = pstmt.executeQuery();
			 //bVo에 결과값 set입력 진행
			 if(rs.next()) {
				 bVo = new BoardVO();
				 
				 bVo.setNum(rs.getInt("num"));
				 bVo.setName(rs.getString("name"));
				 bVo.setPass(rs.getString("pass"));
				 bVo.setEmail(rs.getString("email"));
				 bVo.setTitle(rs.getString("title"));
				 bVo.setContent(rs.getString("content"));
				 bVo.setWritedate(rs.getTimestamp("writedate"));
				 bVo.setReadcount(rs.getInt("readcount"));
			 }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("게시판 글 상세내용 보기 오류");
		} finally {
			DBManager.close(conn, pstmt);
		}
		return bVo;
	}
	
	
	
	
	//게시글 수정
	public void updateBoard(BoardVO bVo) {
		String sql = "update board2 set name=?, email=?, pass=?, "
				+"title=?, content=?  where num=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBManager.getConnenction();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bVo.getName());
			pstmt.setString(2, bVo.getEmail());
			pstmt.setString(3, bVo.getPass());
			pstmt.setString(4, bVo.getTitle());
			pstmt.setString(5, bVo.getContent());
			pstmt.setInt(6, bVo.getNum());

			pstmt.executeQuery();
		} catch (Exception e) {
			System.out.println("게시글 수정중 예외발생");
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}
	
	
	//비밀번호 체크
	public BoardVO checkPassWord(String pass, String num) {
		String sql = "select * from board2 where pass=? and num=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO bVo = null;
		
		try {
			conn = DBManager.getConnenction();
			pstmt = conn.prepareStatement(sql);
			
			//인파라미터값 전달
			pstmt.setString(1, pass);
			pstmt.setString(2, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bVo = new BoardVO();
				
				bVo.setNum(rs.getInt("num"));
				bVo.setName(rs.getString("name"));
				bVo.setEmail(rs.getString("email"));
				bVo.setPass(rs.getString("pass"));
				bVo.setTitle(rs.getString("title"));
				bVo.setContent(rs.getString("content"));
				bVo.setReadcount(rs.getInt("readcount"));
				bVo.setWritedate(rs.getTimestamp("writedate"));
			}
		} catch (Exception e) {
			System.out.println("checkPassWord 예외발생");
			e.printStackTrace();
		}
		return bVo;
	}
	
	
	
	//게시글 삭제
	public void deleteBoard(String num) {
		String sql = "delete board2 where num=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBManager.getConnenction();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			pstmt.executeQuery();
		} catch (Exception e) {
			System.out.println("게시물 삭제중 예외발생");
			e.printStackTrace();
		}
	}
	
	
}
