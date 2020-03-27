package com.douzone.jblog.vo;

public class UserVO {
	private String id;
	private String name;
	private String pw;
	private String join_date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getJoin_date() {
		return join_date;
	}

	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", name=" + name + ", pw=" + pw + ", join_date=" + join_date + "]";
	}

}
