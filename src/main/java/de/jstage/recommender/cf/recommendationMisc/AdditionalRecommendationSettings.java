package de.jstage.recommender.cf.recommendationMisc;

public class AdditionalRecommendationSettings {

	private NeighborhoodType neighborhoodType;
	private int numberOfRecommendation;
	private int numberOfRecommendedBecause;
	private double neighborhoodThreshold;
	private boolean isCachingRecommender;
	private boolean isCachingSimilarity;
	private boolean isCachingNeighborhood;
	private boolean isItemPreComputationEnabled;
	private SimilarityMetric similarityMetricForPreComputation;


	public AdditionalRecommendationSettings(NeighborhoodType neighborhoodType, int numberOfRecommendation,
											int numberOfRecommendedBecause, double neighborhoodThreshold,
											boolean isCachingRecommender, boolean isItemPreComputationEnabled) {
		this.neighborhoodType = neighborhoodType;
		this.numberOfRecommendation = numberOfRecommendation;
		this.numberOfRecommendedBecause = numberOfRecommendedBecause;
		this.neighborhoodThreshold = neighborhoodThreshold;
		this.isCachingRecommender = isCachingRecommender;
		this.isItemPreComputationEnabled = isItemPreComputationEnabled;
	}

	public boolean isCachingRecommender() {
		return isCachingRecommender;
	}

	public void setCachingRecommender(boolean isCachingRecommender) {
		this.isCachingRecommender = isCachingRecommender;
	}

	public NeighborhoodType getNeighborhoodType() {
		return neighborhoodType;
	}

	public void setNeighborhoodType(NeighborhoodType neighborhoodType) {
		this.neighborhoodType = neighborhoodType;
	}

	public int getNumberOfRecommendation() {
		return numberOfRecommendation;
	}

	public void setNumberOfRecommendation(int numberOfRecommendation) {
		this.numberOfRecommendation = numberOfRecommendation;
	}

	public int getNumberOfRecommendedBecause() {
		return numberOfRecommendedBecause;
	}

	public void setNumberOfRecommendedBecause(int numberOfRecommendedBecause) {
		this.numberOfRecommendedBecause = numberOfRecommendedBecause;
	}

	public double getNeighborhoodThreshold() {
		return neighborhoodThreshold;
	}

	public void setNeighborhoodThreshold(double neighborhoodThreshold) {
		this.neighborhoodThreshold = neighborhoodThreshold;
	}

	public boolean isItemPreComputationEnabled() {
		return isItemPreComputationEnabled;
	}

	public void setItemPreComputationEnabled(boolean isItemPreComputationEnabled) {
		this.isItemPreComputationEnabled = isItemPreComputationEnabled;
	}

	public SimilarityMetric getSimilarityMetricForPreComputation() {
		return similarityMetricForPreComputation;
	}

	public void setSimilarityMetricForPreComputation(SimilarityMetric similarityMetricForPreComputation) {
		this.similarityMetricForPreComputation = similarityMetricForPreComputation;
	}
}
