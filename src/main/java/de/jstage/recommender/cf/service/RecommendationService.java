package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.model.EvaluationParameters;
import de.jstage.recommender.cf.model.RecommendationParameters;
import de.jstage.recommender.cf.recommendationMisc.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.impl.eval.LoadStatistics;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.util.List;

public interface RecommendationService {

	List<RecommendedItem> getRecommendations(RecommendationParameters recommendationParameters) throws TasteException;

	LoadStatistics getLoadStatistics(SimilarityMetric similarityMetric) throws TasteException;

	IRStatistics getIRStatistics(EvaluationParameters param) throws TasteException;

	double getEvaluationScore(EvaluationParameters param) throws TasteException;
}
