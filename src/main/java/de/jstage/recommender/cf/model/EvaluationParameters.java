package de.jstage.recommender.cf.model;

import de.jstage.recommender.cf.recommendationMisc.EvaluatorType;
import de.jstage.recommender.cf.recommendationMisc.RecommendationType;
import de.jstage.recommender.cf.recommendationMisc.SimilarityMetric;

public class EvaluationParameters {

	private SimilarityMetric similarityMetric;
	private RecommendationType recommendationType;
	private EvaluatorType evaluatorType;
	private double trainingPercentage;
	private double evaluationPercentage;
	private int recallAt;

	public EvaluationParameters(RecommendationType recommendationType, SimilarityMetric similarityMetric,
								EvaluatorType evaluatorType, double trainingPercentage, double evaluationPercentage) {
		this.recommendationType = recommendationType;
		this.similarityMetric = similarityMetric;
		this.evaluatorType = evaluatorType;
		this.trainingPercentage = trainingPercentage;
		this.evaluationPercentage = evaluationPercentage;
	}

	public EvaluationParameters(RecommendationType recommendationType, SimilarityMetric similarityMetric,
								int recallAt, double evaluationPercentage) {
		this.recommendationType = recommendationType;
		this.similarityMetric = similarityMetric;
		this.recallAt = recallAt;
		this.evaluationPercentage = evaluationPercentage;
	}

	public RecommendationType getRecommendationType() {
		return recommendationType;
	}

	public void setRecommendationType(RecommendationType recommendationType) {
		this.recommendationType = recommendationType;
	}

	public SimilarityMetric getSimilarityMetric() {
		return similarityMetric;
	}

	public void setSimilarityMetric(SimilarityMetric similarityMetric) {
		this.similarityMetric = similarityMetric;
	}

	public EvaluatorType getEvaluatorType() {
		return evaluatorType;
	}

	public void setEvaluatorType(EvaluatorType evaluatorType) {
		this.evaluatorType = evaluatorType;
	}

	public double getTrainingPercentage() {
		return trainingPercentage;
	}

	public void setTrainingPercentage(double trainingPercentage) {
		this.trainingPercentage = trainingPercentage;
	}

	public double getEvaluationPercentage() {
		return evaluationPercentage;
	}

	public void setEvaluationPercentage(double evaluationPercentage) {
		this.evaluationPercentage = evaluationPercentage;
	}

	public int getRecallAt() {
		return recallAt;
	}

	public void setRecallAt(int recallAt) {
		this.recallAt = recallAt;
	}
}
