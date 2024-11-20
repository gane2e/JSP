package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBManager {

	public static Connection getConnenction() {
		
		Connection conn = null;
		
		try {
			
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			
			//dbcp_myoracle 이란 객체를 찾아서 Datasource가 받는다.
			DataSource ds = (DataSource)envContext.lookup("dbcp_myoracle");
			
			//ds가 생성되었으므로 Connection을 구합니다.
			conn = ds.getConnection();
			
			System.out.println("conn : " + conn);
			System.out.println("커넥션 연결 성공");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("커넥션 연결 실패");
		}
		return conn;
	}
	
	
	// select를 수행한 후 리소스 해제를 위한 메소드
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//DML(insert, update, delete)을 수행한 후 리소스 해제를 위한 메소드
	public static void close(Connection conn, Statement stmt) {
		try {
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
