package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import data.Attendance;
import data.Avatar;
import data.MOIM;
import data.User;

public class MOIMS {
	
	static final String url = "jdbc:oracle:thin:@192.168.4.22:1521:xe";
	static final String user = "C##MOIM";
	static final String password = "1q2w3e4r";
	
	
	// 데이터 등록 시 사용할 function(메소드)
		public static int create(String id, String managerId, String event, String type, String cate, String description, int maxPerson, String beginDate, String endDate) {
			try {
				// 1. 연결
				Connection conn = DriverManager.getConnection(url, user, password);

				// 2. 요청객체 준비
				String sql = "INSERT INTO MOIMS VALUES (?, ?, ?, ?, ?, ?, ?, 1, TO_DATE(?, 'YYYY-MM-DD HH24:MI'), TO_DATE(?, 'YYYY-MM-DD HH24:MI'), null)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, managerId);
				ps.setString(3, event);
				ps.setString(4, type);
				ps.setString(5, cate);
				ps.setString(6,  description);
				ps.setInt(7,  maxPerson);
				ps.setString(8, beginDate);    //date로 전달받으면?
				ps.setString(9,  endDate);

				int r = ps.executeUpdate();
				conn.close();
				return r;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}
	
	
		public static List<MOIM> findLatest() {
			try {
				Connection conn = DriverManager.getConnection(url, user, password);
				
				String sql = "select * from (select * from moims where type = 'public' and begin_date > sysdate order by begin_date - sysdate) where rownum <= 3";
				PreparedStatement ps = conn.prepareStatement(sql);			
				ResultSet rs = ps.executeQuery();
				List<MOIM> moimList = new LinkedList<>();

				while(rs.next()) {
					MOIM moim = new MOIM();
					moim.setId(rs.getString("ID"));
					moim.setManagerId(rs.getString("manager_id"));
					moim.setEvent(rs.getString("event"));
					moim.setCate(rs.getString("cate"));
					moim.setType(rs.getString("type"));
					moim.setDescription(rs.getString("description"));
					moim.setMaxPerson(rs.getInt("Max_Person"));
					moim.setCurrentPerson(rs.getInt("current_Person"));
					moim.setBeginDate(rs.getDate("Begin_Date"));
					moim.setEndDate(rs.getDate("End_Date"));
					moim.setFinalCost(rs.getInt("final_cost"));
					
					moimList.add(moim);
				}
				conn.close();

				return moimList;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		
		
		public static List<MOIM> findByCate(String[] cates) {
			try {
				Connection conn = DriverManager.getConnection(url, user, password);

				String sql = "SELECT * FROM MOIMS WHERE BEGIN_DATE > SYSDATE";
				if (cates == null) {
					sql += " ORDER BY BEGIN_DATE";
				} else {
					switch (cates.length) {
					case 1 -> sql += " AND CATE IN (?)";
					case 2 -> sql += " AND CATE IN (?, ?)";
					case 3 -> sql += " AND CATE IN (?, ?, ?)";
					case 4 -> sql += " AND CATE IN (?, ?, ?, ?)";
					case 5 -> sql += " AND CATE IN (?, ?, ?, ?, ?)";
					case 6 -> sql += " AND CATE IN (?, ?, ?, ?, ?, ?)";
					case 7 -> sql += " AND CATE IN (?, ?, ?, ?, ?, ?, ?)";
					}
					sql += " ORDER BY BEGIN_DATE";
				}
				PreparedStatement ps = conn.prepareStatement(sql);
				if (cates != null) {
					for (int i = 0; i < cates.length; i++) {
						ps.setString(i + 1, cates[i]);
					}
				}
				
				ResultSet rs = ps.executeQuery();
				List<MOIM> moimList = new LinkedList<>();
				while (rs.next()) {
					MOIM moim = new MOIM();
					moim.setId(rs.getString("ID"));
					moim.setManagerId(rs.getString("manager_id"));
					moim.setEvent(rs.getString("event"));
					moim.setCate(rs.getString("cate"));
					moim.setType(rs.getString("type"));
					moim.setDescription(rs.getString("description"));
					moim.setMaxPerson(rs.getInt("Max_Person"));
					moim.setCurrentPerson(rs.getInt("current_Person"));
					moim.setBeginDate(rs.getDate("Begin_Date"));
					moim.setEndDate(rs.getDate("End_Date"));
					moim.setFinalCost(rs.getInt("final_cost"));

					moimList.add(moim);
				}
				conn.close();
				return moimList;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		
		
		/*
		 * 
		 * if (cates != null) { switch (cates.length) { case 7 : ps.setString(7,
		 * cates[6]); case 6 : ps.setString(6, cates[5]); case 5 : ps.setString(5,
		 * cates[4]); case 4 : ps.setString(4, cates[3]); case 3 : ps.setString(3,
		 * cates[2]); case 2 : ps.setString(2, cates[1]); case 1 : ps.setString(1,
		 * cates[0]); } }
		 */
		
		
		
		public static MOIM findById(String id) {
			try {
				Connection conn = DriverManager.getConnection(url, user, password);
				
				String sql = "select moims.*, users.name as manager_name, avatars.url as manager_url ";
			     sql += "from moims ";
			     sql += "join users on moims.manager_id = users.id ";
			     sql += "join avatars on avatars.id = users.avatar_id ";
			     sql += "where moims.id = ? ";


				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs = ps.executeQuery();
				MOIM moim = null;

				while(rs.next()) {
					moim = new MOIM();
					moim.setId(rs.getString("ID"));
					moim.setManagerId(rs.getString("manager_id"));
					moim.setEvent(rs.getString("EVENT"));
					moim.setCate(rs.getString("CATE"));
					moim.setType(rs.getString("TYPE"));
					moim.setDescription(rs.getString("DESCRIPTION"));
					moim.setMaxPerson(rs.getInt("MAX_PERSON"));
					moim.setCurrentPerson(rs.getInt("CURRENT_PERSON"));
					moim.setBeginDate(rs.getDate("BEGIN_DATE"));
					moim.setEndDate(rs.getDate("END_DATE"));
					moim.setFinalCost(rs.getInt("FINAL_COST"));
					
					moim.setManagerName(rs.getString("MANAGER_NAME"));
					moim.setManagerAvatarURL(rs.getString("MANAGER_URL"));
				}
				conn.close();

				return moim;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		
		
		public static List<MOIM> publicMoim(String id) {
			try {
				Connection conn = DriverManager.getConnection(url, user, password);
				
				String sql = "UPDATE MOIMS SET CURRENT_PERSON = CURRENT_PERSON + 1 WHERE ID = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs = ps.executeQuery();
				List<MOIM> moimList = new LinkedList<>();

				while(rs.next()) {
					MOIM moim = new MOIM();
					moim.setId(rs.getString("ID"));
					moim.setManagerId(rs.getString("manager_id"));
					moim.setEvent(rs.getString("event"));
					moim.setCate(rs.getString("cate"));
					moim.setType(rs.getString("type"));
					moim.setDescription(rs.getString("description"));
					moim.setMaxPerson(rs.getInt("Max_Person"));
					moim.setCurrentPerson(rs.getInt("current_Person"));
					moim.setBeginDate(rs.getDate("Begin_Date"));
					moim.setEndDate(rs.getDate("End_Date"));
					moim.setFinalCost(rs.getInt("final_cost"));
					
					moimList.add(moim);
				}
				conn.close();

				return moimList;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		
		

}
