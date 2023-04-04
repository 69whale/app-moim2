package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import data.MOIM;

public class Searchs {
	
	static final String url = "jdbc:oracle:thin:@192.168.4.22:1521:xe";
	static final String user = "C##MOIM";
	static final String password = "1q2w3e4r";
	
	public static List<MOIM> findCate(String cate) {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			List<MOIM> moimList = new LinkedList<>();
			if(cate.equals("all")) {
				String sql ="select * from "
						+ "(select * from moims where type = 'public' and begin_date > sysdate "
						+ "order by begin_date - sysdate)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();

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
			}else{
			
			String sql = "select * from (select * from moims where type = 'public' and begin_date > sysdate order by begin_date - sysdate) where cate = ?";
			PreparedStatement ps = conn.prepareStatement(sql);			
			ps.setString(1, cate);
			ResultSet rs = ps.executeQuery();

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
			}
			conn.close();
			return moimList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
