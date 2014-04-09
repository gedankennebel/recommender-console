package de.jstage.recommender.cf.recommendationMisc;

public class AdditionalRecommendationSettings {

	private int numberOfRecommendation;
	private int numberOfRecommendedBecause;
	private int neighbourhoodThreshold;

	public AdditionalRecommendationSettings(int numberOfRecommendation, int numberOfRecommendedBecause, int neighbourhoodThreshold) {
		this.numberOfRecommendation = numberOfRecommendation;
		this.numberOfRecommendedBecause = numberOfRecommendedBecause;
		this.neighbourhoodThreshold = neighbourhoodThreshold;
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

	public int getNeighbourhoodThreshold() {
		return neighbourhoodThreshold;
	}

	public void setNeighbourhoodThreshold(int neighbourhoodThreshold) {
		this.neighbourhoodThreshold = neighbourhoodThreshold;
	}
}
