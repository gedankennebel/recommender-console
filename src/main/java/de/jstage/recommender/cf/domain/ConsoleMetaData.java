package de.jstage.recommender.cf.domain;

import de.jstage.recommender.cf.recommendationMisc.RecommendationType;

import java.util.List;

public class ConsoleMetaData {

	private List<RecommendationType> recommendationTypeList;
	private List<User> userList;
	private int numberOfUsers;
	private int numberOfItems;
	private double dataModelCreationTime;

	public ConsoleMetaData(List<RecommendationType> recommendationTypeList, List<User> userList, int numberOfUsers,
						   int numberOfItems, double dataModelCreationTime) {
		this.recommendationTypeList = recommendationTypeList;
		this.userList = userList;
		this.numberOfUsers = numberOfUsers;
		this.numberOfItems = numberOfItems;
		this.dataModelCreationTime = dataModelCreationTime;
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

	public double getDataModelCreationTime() {
		return dataModelCreationTime;
	}

	public void setDataModelCreationTime(double dataModelCreationTime) {
		this.dataModelCreationTime = dataModelCreationTime;
	}
}
