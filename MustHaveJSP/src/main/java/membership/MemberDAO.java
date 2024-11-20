package membership;

import java.util.ArrayList;
import java.util.List;

import common.JDBConnect;

//DB하고 연관된 일을 함(select, update, insert, delete 담당)
public class MemberDAO extends JDBConnect{

	// 명시한 데이터베이스로의 연결이 완료된 MemberDAO객체를 생성
	public MemberDAO(String drv, String url, String id, String pw) {
		super(drv, url, id, pw);
	}
	
	// id, pass 받아서 DB조회해서 일치하는 회원이 있는지 여부 판단
	public MemberDTO getMemberDTO(String uid, String upass) {
		
		MemberDTO dto = new MemberDTO();
		
		String sql = "SELECT * FROM member WHERE id=? AND pass=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
			pstmt.setString(2, upass);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String id = rs.getString("id");
				String pass = rs.getString("pass");
				String name = rs.getString("name");
				String regidate = rs.getString("regidate");
				
				dto.setId(id);
				dto.setPass(pass);
				dto.setName(name);
				dto.setRegidate(regidate);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	
	//회원 전체 목록 조회
	public List<MemberDTO> getAllList() {
		
		List<MemberDTO> list = new ArrayList<>();
		
		String sql = "SELECT * FROM member";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				MemberDTO dto = new MemberDTO();
				
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
				dto.setName(rs.getString("name"));
				dto.setRegidate(rs.getString("regidate"));
				
				list.add(dto);
			
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		
		return list;
	}
}
