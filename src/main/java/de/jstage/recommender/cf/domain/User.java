package de.jstage.recommender.cf.domain;

public class User {

	private long userId;

	public User(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
