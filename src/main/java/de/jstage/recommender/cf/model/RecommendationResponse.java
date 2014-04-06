package de.jstage.recommender.cf.model;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.util.List;

public class RecommendationResponse {
	private List<RecommendedItem> recommendedItemList;
	private long calculationTime;

	public RecommendationResponse(List<RecommendedItem> recommendedItemList) {
		this.recommendedItemList = recommendedItemList;
	}

	public RecommendationResponse(List<RecommendedItem> recommendedItemList, long calculationTime) {
		this.recommendedItemList = recommendedItemList;
		this.calculationTime = calculationTime;
	}

	public List<RecommendedItem> getRecommendedItemList() {
		return recommendedItemList;
	}

	public void setRecommendedItemList(List<RecommendedItem> recommendedItemList) {
		this.recommendedItemList = recommendedItemList;
	}

	public long getCalculationTime() {
		return calculationTime;
	}

	public void setCalculationTime(long calculationTime) {
		this.calculationTime = calculationTime;
	}
}
