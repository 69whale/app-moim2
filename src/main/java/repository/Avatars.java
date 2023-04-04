package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import data.Avatar;


public class Avatars {
	
	static final String url = "jdbc:oracle:thin:@192.168.4.22:1521:xe";
	static final String user = "C##MOIM";
	static final String password = "1q2w3e4r";
	
	
	public static List<Avatar> findAll() {
	
		List<Avatar> list = new ArrayList<>();
		
		try {			
			//1. 연결
		  Connection conn = DriverManager.getConnection(url, user, password);
		   //2. 요청객체 준비
		  String sql = "SELECT * FROM AVATARS";
		
		  PreparedStatement ps = conn.prepareStatement(sql);
		   
		   //3. 요청후 응답처리
		  ResultSet rs = ps.executeQuery();
		  while(rs.next()) {
			 Avatar at = new Avatar();
			    at.setId(rs.getString("id"));
			    at.setUrl(rs.getString("url"));
				
			    list.add(at);
		  }
		  conn.close();
		  return list;
		  
		}catch (Exception e) {
			e.printStackTrace(); //에러원인 나옴
			return null;  
			
		}
		
	}

}
