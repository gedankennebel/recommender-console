package de.jstage.recommender.cf.model;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.util.List;

public class RecommendationResponse {
	private RecommendationParameters recommendationParameters;
	private List<RecommendedItem> recommendedItemList;
	private double calculationTime;

	public RecommendationResponse(RecommendationParameters recommendationParameters, List<RecommendedItem> recommendedItemList) {
		this.recommendationParameters = recommendationParameters;
		this.recommendedItemList = recommendedItemList;
	}

	public RecommendationParameters getRecommendationParameters() {
		return recommendationParameters;
	}

	public void setRecommendationParameters(RecommendationParameters recommendationParameters) {
		this.recommendationParameters = recommendationParameters;
	}

	public List<RecommendedItem> getRecommendedItemList() {
		return recommendedItemList;
	}

	public void setRecommendedItemList(List<RecommendedItem> recommendedItemList) {
		this.recommendedItemList = recommendedItemList;
	}

	public double getCalculationTime() {
		return calculationTime;
	}

	public void setCalculationTime(double calculationTime) {
		this.calculationTime = calculationTime;
	}
}
