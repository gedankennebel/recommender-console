package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.model.RecommendationResponse;
import de.jstage.recommender.cf.recommender.RecommendationType;
import de.jstage.recommender.cf.recommender.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

@Service
public class CollaborativeFilteringRecommendationService {

	@Resource(name = "itemBased")
	private RecommendationService itemBasedRecommendationService;

	@Resource(name = "userBased")
	private RecommendationService userBasedRecommendationService;

	public RecommendationResponse getRecommendations(RecommendationType recommendationType, SimilarityMetric metric, int howMany, long userId) throws TasteException {
		switch (recommendationType) {
			case ITEM_BASED:
				return new RecommendationResponse(itemBasedRecommendationService.getRecommendations(metric, howMany, userId));
			case USER_BASED:
				return new RecommendationResponse(userBasedRecommendationService.getRecommendations(metric, howMany, userId));
			default:
				return new RecommendationResponse(Collections.<RecommendedItem>emptyList());
		}
	}
}
