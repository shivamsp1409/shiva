package com.example.lms.model;

public class Student {
	private String id;
	private String fullName;
	private String email;
	private String avatarUrl;

	public Student() {}

	public Student(String id, String fullName, String email, String avatarUrl) {
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.avatarUrl = avatarUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
}