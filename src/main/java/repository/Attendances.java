package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import data.Attendance;

public class Attendances {
	
	static final String url = "jdbc:oracle:thin:@192.168.4.22:1521:xe";
	static final String user = "C##MOIM";
	static final String password = "1q2w3e4r";
	
	
	public static List<Attendance> findByMoimId(String moimId) {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			
			String sql = "select * from attendances where moim_id = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, moimId);
			ResultSet rs = ps.executeQuery();
			List<Attendance> list = new ArrayList<>();

			while(rs.next()) {
				Attendance a = new Attendance();
				a.setId(rs.getInt("ID"));
				a.setMoimId(rs.getString("MOIM_Id"));
				a.setUserId(rs.getString("USER_Id"));
				a.setStatus(rs.getInt("STATUS"));
				
				
				
				list.add(a);
			}
			conn.close();

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static int createAtt(String moim_id, String user_id, int status) {
		try {
			// 1. 연결
			Connection conn = DriverManager.getConnection(url, user, password);

			// 2. 요청객체 준비
			String sql = "INSERT INTO ATTENDANCES VALUES (ATTENDANCES_SEQ.NEXTVAL, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, moim_id);
			ps.setString(2, user_id);
			ps.setInt(3, status);
			
			int r = ps.executeUpdate();
			conn.close();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	
	public static int findUserStatusInMoim(String moimId, String userId) {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "SELECT STATUS FROM ATTENDANCES WHERE MOIM_ID=? AND USER_ID=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, moimId);
			ps.setString(2, userId);

			ResultSet rs = ps.executeQuery();
			int status;

			if (rs.next()) {
				status = rs.getInt("status");
			}else {
				status = 0;
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return -9;
		}
	}
	
	
	

}
