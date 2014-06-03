package de.jstage.recommender.cf.domain;

import java.util.List;

public class FullEvaluationResult {

	private String recommendationType;
	private String evaluationType;
	private List<EvaluationResult> evaluationResultList;
	private double computingTimeInSeconds;

	public FullEvaluationResult() {
	}

	public FullEvaluationResult(String recommendationType, String evaluationType, List<EvaluationResult> evaluationResultList, double computingTimeInSeconds) {
		this.recommendationType = recommendationType;
		this.evaluationType = evaluationType;
		this.evaluationResultList = evaluationResultList;
		this.computingTimeInSeconds = computingTimeInSeconds;
	}

	public String getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(String evaluationType) {
		this.evaluationType = evaluationType;
	}

	public String getRecommendationType() {
		return recommendationType;
	}

	public void setRecommendationType(String recommendationType) {
		this.recommendationType = recommendationType;
	}

	public List<EvaluationResult> getEvaluationResultList() {
		return evaluationResultList;
	}

	public void setEvaluationResultList(List<EvaluationResult> evaluationResultList) {
		this.evaluationResultList = evaluationResultList;
	}

	public double getComputingTimeInSeconds() {
		return computingTimeInSeconds;
	}

	public void setComputingTimeInSeconds(double computingTimeInSeconds) {
		this.computingTimeInSeconds = computingTimeInSeconds;
	}
}
