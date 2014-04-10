package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.model.RecommendationParameters;
import de.jstage.recommender.cf.model.RecommendationResponse;
import de.jstage.recommender.cf.recommendationMisc.RecommendationType;
import de.jstage.recommender.cf.recommendationMisc.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
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
		RecommendationService recommendationService = getDesiredRecommendationServiceType(param.getRecommendationType());
		return new RecommendationResponse(param, recommendationService.getRecommendations(param));
	}

	public LoadStatistics getLoadStatistics(RecommendationType recommendationType, SimilarityMetric metric) throws TasteException {
		return getDesiredRecommendationServiceType(recommendationType).getLoadStatistics(metric);
	}

	public RecommendationResponse getRecommendedBecause(RecommendationParameters param) throws TasteException {
		List<RecommendedItem> recommendedBecauseList = ((ItemBasedRecommendationService) itemBasedRecommendationService).getRecommendedBecause(param);
		return new RecommendationResponse(param, recommendedBecauseList);
	}

	private RecommendationService getDesiredRecommendationServiceType(RecommendationType recommendationType) {
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
