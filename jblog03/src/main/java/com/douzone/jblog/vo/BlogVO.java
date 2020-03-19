package com.douzone.jblog.vo;

public class BlogVO {
	private String user_id;
	private String title;
	private String logo;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "BlogVO [user_id=" + user_id + ", title=" + title + ", logo=" + logo + "]";
	}

}
