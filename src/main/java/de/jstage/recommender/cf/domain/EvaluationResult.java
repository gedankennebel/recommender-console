package de.jstage.recommender.cf.domain;

public class EvaluationResult {

	private String recommendationType;
	private String similarityMetric;
	private double score;
	private double computingTimeInSeconds;

	public EvaluationResult() {
	}

	public EvaluationResult(String recommendationType, String similarityMetric, double score, double computingTimeInSeconds) {
		this.recommendationType = recommendationType;
		this.similarityMetric = similarityMetric;
		this.score = score;
		this.computingTimeInSeconds = computingTimeInSeconds;
	}

	public String getRecommendationType() {
		return recommendationType;
	}

	public void setRecommendationType(String recommendationType) {
		this.recommendationType = recommendationType;
	}

	public String getSimilarityMetric() {
		return similarityMetric;
	}

	public void setSimilarityMetric(String similarityMetric) {
		this.similarityMetric = similarityMetric;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getComputingTimeInSeconds() {
		return computingTimeInSeconds;
	}

	public void setComputingTimeInSeconds(double computingTimeInSeconds) {
		this.computingTimeInSeconds = computingTimeInSeconds;
	}
}
