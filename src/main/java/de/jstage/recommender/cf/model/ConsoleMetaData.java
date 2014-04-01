package de.jstage.recommender.cf.model;

import de.jstage.recommender.cf.recommender.RecommendationType;

import java.util.List;

public class ConsoleMetaData {

	private List<RecommendationType> recommendationTypeList;
	private List<User> userList;
	private int numberOfUsers;
	private int numberOfItems;

	public ConsoleMetaData(List<RecommendationType> recommendationTypeList, List<User> userList, int numberOfUsers, int numberOfItems) {
		this.recommendationTypeList = recommendationTypeList;
		this.userList = userList;
		this.numberOfUsers = numberOfUsers;
		this.numberOfItems = numberOfItems;
	}

	public List<RecommendationType> getRecommendationTypeList() {
		return recommendationTypeList;
	}

	public void setRecommendationTypeList(List<RecommendationType> recommendationTypeList) {
		this.recommendationTypeList = recommendationTypeList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public int getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}
}
