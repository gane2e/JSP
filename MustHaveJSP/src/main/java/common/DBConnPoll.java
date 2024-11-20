package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnPoll {
	public Connection conn;
	public Statement stmt;
	public PreparedStatement pstmt;
	public ResultSet  rs;
	
	public DBConnPoll(){
		try {
			
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("dbcp_myoracle");
			conn = ds.getConnection();
			
			
			System.out.println("conn : " + conn);
			System.out.println("DB 커넥션 풀 연결 성공");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("에러 - DB 커넥션 풀 연결 불가");
		}
	}
	
	//자원 반납
	public void close() {
		try {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();
			
			System.out.println("JDBC 자원 해제");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
