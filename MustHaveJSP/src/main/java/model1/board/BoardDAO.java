package model1.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.JDBConnect;
import jakarta.servlet.ServletContext;

public class BoardDAO extends JDBConnect{
	
	//DB연결
	public BoardDAO(ServletContext application) {
		super(application);
	}
	
	//검색 조건에 맞는 게시물의 개수를 반환
	public int selectCount(Map<String, Object> map) {
		int totalCount=0; // 결과 (게시물 수)를 담을 변수
		
		String sql = "SELECT COUNT(*) FROM board";
		
		if(map.get("searchWord") != null) {
			sql += "WHERE" + map.get("searchField") + " "
					+ "LIKE '%" + map.get("searchWord") + "%'";
		}

		try {
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql);
						
			if(rs.next()) {
				totalCount = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("게시물 수를 구하는 중 예외 발생");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	//검색 조건에 맞는 게시물 목록을 반환합니다. (페이징 기능 지원X)
	public List<BoardDTO> selectList(Map<String, Object> map){
		List<BoardDTO> bbs = new ArrayList<>();
		
		String query = "select * from board";
		
		//검색조건
		if(map.get("searchWord") != null) {
			query += "WHERE" + map.get("searchField") + " "
					+ "LIKE '%"+  map.get("searchWord") + "%' " ;
			query += "ORDER by num DESC";
		}
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
						
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				bbs.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		return bbs;
	}
	
	
	
	
	
	//검색 조건에 맞는 게시물 목록을 반환합니다. (페이징 기능 지원)
	public List<BoardDTO> selectListPage(Map<String, Object> map) {
		
		List<BoardDTO> bbs = new ArrayList<BoardDTO>(); //결과(게시물 목록)담을 변수
		
		//쿼리문 템플릿
		
		/*
		 * SELECT * from( 
		 * 		SELECT Tb.*, rownum rNum FROM (
		 * 			 SELECT * FROM board ORDER BY num DESC 
		 * 		)Tb 
		 * ) 
		 * WHERE rNum BETWEEN 1 AND 10;
		 */

		
		String query = "SELECT * FROM ("
				+ " SELECT Tb.*, rownum rNum FROM ("
				+ " SELECT * FROM board ";
		//검색조건 추가
		if(map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField")
					+ " LIKE '%" + map.get("searchWord") + "%' "; 
		}
		query += "		ORDER BY num DESC "
				+ "		) Tb "
				+ " ) "
				+ " WHERE rNum BETWEEN ? AND ?";
		try {
			//쿼리문 완성
			pstmt = conn.prepareStatement(query);
			
			//맵핑
			pstmt.setString(1, map.get("start").toString());
			pstmt.setString(2, map.get("end").toString());
			
			//쿼리문 실행
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//한 행(게시물 하나)의 데이터를 dto에 저장
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				bbs.add(dto);
			}
		} catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		return bbs;
	}
	
	
	
	
	//저장
	public int insertWrite(BoardDTO dto) {
		
		int result = 0;
		String query = "insert into board(num, title, content, id, visitcount)" + "values(seq_board_num.nextval, ?, ?, ?, 0)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getId());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("게시물 입력 중 예외 발생!");
			e.printStackTrace();
		}
		return result;
	}
	
	public BoardDTO selectView(String num) {
		BoardDTO dto = new BoardDTO();
		
		String query = "select b.*, m.name from board b join member m on b.id=m.id"
			+ " where num = ?" ;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				dto.setName(rs.getString("name"));
				
				
			}
		
		} catch (Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
			
		} return dto;
	} 
	
	//조회수 증가
	public void updateVisitCount(String num) {
		String query = "update board set " + "visitcount = visitcount + 1"
				+ " where num = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, num);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	//게시물 수정
	public int updateEdit(BoardDTO dto) {
		
		int result = 0;
		
		try {
			String query = "UPDATE board SET " 
						+ " title=?, content=? "
						+ " WHERE num=?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getNum());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("게시물 수정 중 예외 발생");
			e.printStackTrace();
		} 
		return result;
	}
	
	//게시물 삭제
	
	public int deletePost(String num) {
		int result=0;
		
		String query = "DELETE FROM board WHERE num=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, num);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("게시글 삭제 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	
}
