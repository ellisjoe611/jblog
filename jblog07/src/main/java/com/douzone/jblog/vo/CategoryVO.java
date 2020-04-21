package com.douzone.jblog.vo;

public class CategoryVO {
	private Long no;
	private String name;
	private Long posted;
	private String description;
	private String reg_date;
	private String user_id;

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPosted() {
		return posted;
	}

	public void setPosted(Long posted) {
		this.posted = posted;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "CategoryVO [no=" + no + ", name=" + name + ", posted=" + posted + ", description=" + description
				+ ", reg_date=" + reg_date + ", user_id=" + user_id + "]";
	}

}
