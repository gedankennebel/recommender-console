package de.jstage.recommender.cf.domain;

import de.jstage.recommender.cf.enums.RecommendationType;
import de.jstage.recommender.cf.enums.SimilarityMetric;

public class RecommendationParameters {

	private SimilarityMetric appliedSimilarity;
	private RecommendationType recommendationType;
	private int howMany;
	private long userId;
	private long itemId;

	public RecommendationParameters(SimilarityMetric appliedSimilarity, long userId, long itemId) {
		this.appliedSimilarity = appliedSimilarity;
		this.userId = userId;
		this.itemId = itemId;
	}

	public RecommendationParameters(SimilarityMetric appliedSimilarity, long itemId, int howMany) {
		this.appliedSimilarity = appliedSimilarity;
		this.howMany = howMany;
		this.itemId = itemId;
	}

	public RecommendationParameters(RecommendationType recommendationType, SimilarityMetric appliedSimilarity, int howMany, long userId) {
		this.recommendationType = recommendationType;
		this.appliedSimilarity = appliedSimilarity;
		this.howMany = howMany;
		this.userId = userId;
	}

	public RecommendationParameters(SimilarityMetric appliedSimilarity, long userId, long itemId, int howMany) {
		this.appliedSimilarity = appliedSimilarity;
		this.howMany = howMany;
		this.userId = userId;
		this.itemId = itemId;
	}

	public SimilarityMetric getAppliedSimilarity() {
		return appliedSimilarity;
	}

	public void setAppliedSimilarity(SimilarityMetric appliedSimilarity) {
		this.appliedSimilarity = appliedSimilarity;
	}

	public RecommendationType getRecommendationType() {
		return recommendationType;
	}

	public void setRecommendationType(RecommendationType recommendationType) {
		this.recommendationType = recommendationType;
	}

	public int getHowMany() {
		return howMany;
	}

	public void setHowMany(int howMany) {
		this.howMany = howMany;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
}
