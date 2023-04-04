package data;

import java.util.Date;

public class Reply {
	int id;
	String moim_id;
	String writer;
	String comment;
	Date writed_date;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMoim_id() {
		return moim_id;
	}
	public void setMoim_id(String moim_id) {
		this.moim_id = moim_id;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getWrited_date() {
		return writed_date;
	}
	public void setWrited_date(Date writed_date) {
		this.writed_date = writed_date;
	}
	
	
	
	
	

}
