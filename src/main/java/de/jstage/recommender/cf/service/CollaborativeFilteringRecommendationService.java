package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.model.EvaluationParameters;
import de.jstage.recommender.cf.model.RecommendationParameters;
import de.jstage.recommender.cf.model.RecommendationResponse;
import de.jstage.recommender.cf.recommendationMisc.RecommendationType;
import de.jstage.recommender.cf.recommendationMisc.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.impl.eval.LoadStatistics;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CollaborativeFilteringRecommendationService {

	@Resource(name = "itemBased")
	private RecommendationService itemBasedRecommendationService;

	@Resource(name = "userBased")
	private RecommendationService userBasedRecommendationService;

	public RecommendationResponse getRecommendations(RecommendationParameters param) throws TasteException {
		RecommendationService recommendationService = getRecommenderService(param.getRecommendationType());
		return new RecommendationResponse(param, recommendationService.getRecommendations(param));
	}

	public LoadStatistics getLoadStatistics(RecommendationType recommendationType, SimilarityMetric metric) throws TasteException {
		return getRecommenderService(recommendationType).getLoadStatistics(metric);
	}

	public IRStatistics getIRStatistics(EvaluationParameters param) throws TasteException {
		return getRecommenderService(param.getRecommendationType()).getIRStatistics(param);
	}

	public double getEvaluationScore(EvaluationParameters param) throws TasteException {
		return getRecommenderService(param.getRecommendationType()).getEvaluationScore(param);
	}

	public RecommendationResponse getRecommendedBecause(RecommendationParameters param) throws TasteException {
		List<RecommendedItem> recommendedBecauseList = ((ItemBasedRecommendationService) itemBasedRecommendationService).getRecommendedBecause(param);
		return new RecommendationResponse(param, recommendedBecauseList);
	}

	public RecommendationResponse getMostSimilarItems(RecommendationParameters param) throws TasteException {
		List<RecommendedItem> mostSimilarItems = ((ItemBasedRecommendationService) itemBasedRecommendationService).getMostSimilarItems(param);
		return new RecommendationResponse(param, mostSimilarItems);
	}

	public void refresh(RecommendationType recommendationType, SimilarityMetric metric) throws TasteException {
		getRecommenderService(recommendationType).refresh(metric);
	}

	private RecommendationService getRecommenderService(RecommendationType recommendationType) {
		switch (recommendationType) {
			case USER_BASED:
				return userBasedRecommendationService;
			case ITEM_BASED:
				return itemBasedRecommendationService;
			default:
				return userBasedRecommendationService;
		}
	}
}
