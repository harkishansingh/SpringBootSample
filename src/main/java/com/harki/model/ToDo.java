package com.harki.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



public class ToDo {

	private int id;
	@NotNull
	private String user;
	@Size(min=9, message="Enter atleast 9 characters")
	private String desc;
	private Date targetDate;
	private boolean isDone;

	public ToDo(int id, String user, String desc, Date targetDate, boolean isDone) {
		super();
		this.id = id;
		this.user = user;
		this.desc = desc;
		this.targetDate = targetDate;
		this.isDone = isDone;
	}

	public ToDo() {

	}

	public int getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public String getDesc() {
		return desc;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public boolean isDone() {
		return isDone;
	}

}
