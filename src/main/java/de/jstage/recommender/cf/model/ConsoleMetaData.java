package de.jstage.recommender.cf.model;

import de.jstage.recommender.cf.recommender.RecommendationType;
import de.jstage.recommender.cf.recommender.SimilarityMetric;

import java.util.List;
import java.util.Map;

public class ConsoleMetaData {

	private Map<RecommendationType, List<SimilarityMetric>> recommenderTypeMap;
	private List<User> userList;

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public ConsoleMetaData(Map<RecommendationType, List<SimilarityMetric>> recommenderTypeListMap, List<User> users) {
		this.recommenderTypeMap = recommenderTypeListMap;
	}

	public Map<RecommendationType, List<SimilarityMetric>> getRecommenderTypeMap() {
		return recommenderTypeMap;
	}

	public void setRecommenderTypeMap(Map<RecommendationType, List<SimilarityMetric>> recommenderTypeMap) {
		this.recommenderTypeMap = recommenderTypeMap;
	}

}
