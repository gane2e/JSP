package fileupload;

import java.util.ArrayList;
import java.util.List;

import common.DBConnPoll;

public class MyFileDAO extends DBConnPoll{

	//새로운 게시물을 입력합니다.
	public int insertFile(MyFileDTO dto) {
		int applyResult = 0;
		
		try {
			
			String query = "insert into myfile(idx, title, cate, ofile, sfile)"
					+ " values(seq_board_num.nextval, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getCate());
			pstmt.setString(3, dto.getOfile());
			pstmt.setString(4, dto.getSfile());
			
			applyResult = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(" 파일 Insert 중 예외 발생");
			e.printStackTrace();
		}
		return applyResult;
	}
	
	//파일 목록 반환
	public List<MyFileDTO> myFileList(){
		List<MyFileDTO> fileList = new ArrayList<>();
		
		String query = "select * from myfile order by idx desc";
		
		try {
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MyFileDTO dto = new MyFileDTO();
				
				dto.setIdx(rs.getString("idx"));
				dto.setTitle(rs.getString("title"));
				dto.setCate(rs.getString("cate"));
				dto.setOfile(rs.getString("ofile"));
				dto.setSfile(rs.getString("sfile"));
				dto.setPostdate(rs.getString("postdate"));
				
				fileList.add(dto);
				
			}
		} catch (Exception e) {
			System.out.println("파일 목록 반환 중 예외 발생");
			e.printStackTrace();
		}
		return fileList;
	}

	
	
}
