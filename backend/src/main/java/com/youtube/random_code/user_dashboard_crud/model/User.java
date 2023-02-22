package com.youtube.random_code.user_dashboard_crud.model;

public class User {
	private String name;
	private String email;
	private String status;

	public User() {}

	public User(
		String name,
		String email,
		String status
	) {
		this.name = name;
		this.email = email;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
