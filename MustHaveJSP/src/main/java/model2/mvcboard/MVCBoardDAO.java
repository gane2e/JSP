package model2.mvcboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.DBConnPoll;
import lombok.NoArgsConstructor;
import model1.board.BoardDTO;


public class MVCBoardDAO extends DBConnPoll{
	
	public MVCBoardDAO() {
		super();
	}

	
	//검색 조건에 맞는 게시물의 개수를 반환
	public int selectCount(Map<String, Object> map) {
		
		int totalCount=0; // 결과 (게시물 수)를 담을 변수
		
		String sql = "SELECT COUNT(*) FROM mvcboard";
		
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
	
	
	
	
	//검색 조건에 맞는 게시물 목록을 반환합니다. (페이징 기능 지원)
	public List<MVCBoardDTO> selectListPage(Map<String, Object> map) {
		
		List<MVCBoardDTO> board = new ArrayList<>(); //결과(게시물 목록)담을 변수
		
		String query = "SELECT * FROM ("
				+ " SELECT Tb.*, rownum rNum FROM ("
				+ " SELECT * FROM mvcboard ";
		//검색조건 추가
		if(map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField")
					+ " LIKE '%" + map.get("searchWord") + "%' "; 
		}
		query += "		ORDER BY idx DESC "
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
				MVCBoardDTO dto = new MVCBoardDTO();
				
				dto.setIdx(rs.getString("idx"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setOfile(rs.getString("ofile"));
				dto.setSfile(rs.getString("sfile"));
				dto.setDowncount(rs.getInt("downcount"));
				dto.setPass(rs.getString("pass"));
				dto.setVisitcount(rs.getInt("visitcount"));
				
				board.add(dto);
			}
		} catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		return board; //목록 반환
	}
	
	//게시글 데이터를 받아 DB에 추가합니다 (파일 업로드 지원)
	public int insertWrite(MVCBoardDTO dto) {
		int result = 0;
		try {
			String query = "INSERT INTO mvcboard ( "
					+ " idx, name, title, content, ofile, sfile, pass) "
					+ " VALUES ( "
					+ " seq_board_num.NEXTVAL,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(query);
			
			System.out.println("dto.getName() : " + dto.getName());
			System.out.println("dto.getTitle() : " +  dto.getTitle());
			System.out.println("dto.getContent() : " + dto.getContent());
			System.out.println("dto.getOfile() : " + dto.getOfile());
			System.out.println("dto.getSfile() : " + dto.getSfile());
			System.out.println("dto.getPass() : " + dto.getPass());
			
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getOfile());
			pstmt.setString(5, dto.getSfile());
			pstmt.setString(6, dto.getPass());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("게시물 입력 중 예외 발생");
			e.printStackTrace();
		} 
		return result;
	}
	
	//주어진 일련번호에 해당하는 게시물을 DTO에 반환한다.
	public MVCBoardDTO selectView(String idx) {
		
		MVCBoardDTO dto = new MVCBoardDTO();
		
		try {
			
			String query = "SELECT * FROM mvcboard WHERE idx=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, idx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setIdx(rs.getString("idx"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setOfile(rs.getString("ofile"));
				dto.setSfile(rs.getString("sfile"));
				dto.setDowncount(rs.getInt("downcount"));
				dto.setPass(rs.getString("pass"));
				dto.setVisitcount(rs.getInt("visitcount"));
			}
			
		} 
		catch (Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		
		return dto;
	}
	
	//주어진 일련번호에 해당하는 게시물의 조회수를 1 증가시킴
	public void updateVisitCount(String idx) {
		String query = "UPDATE mvcboard SET "
					 + " visitcount=visitcount+1 "
					 + " WHERE idx=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, idx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	
	// 다운로드 횟수를 1 증가
	public void downCountPlus(String idx) {
		
		String query = "UPDATE mvcboard SET "
				 + " downcount=downcount+1 "
				 + " WHERE idx=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, idx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시물 다운로드 증가 중 예외 발생");
			e.printStackTrace();
		}
		
	}
}
