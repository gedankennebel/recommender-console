package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.model.RecommendationParameters;
import de.jstage.recommender.cf.model.RecommendationResponse;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class CollaborativeFilteringRecommendationService {

	@Resource(name = "itemBased")
	private RecommendationService itemBasedRecommendationService;

	@Resource(name = "userBased")
	private RecommendationService userBasedRecommendationService;

	public RecommendationResponse getRecommendations(RecommendationParameters param) throws TasteException {
		switch (param.getRecommendationType()) {
			case ITEM_BASED:
				return new RecommendationResponse(param, itemBasedRecommendationService.getRecommendations(param));
			case USER_BASED:
				return new RecommendationResponse(param, userBasedRecommendationService.getRecommendations(param));
			default:
				return new RecommendationResponse(param, Collections.<RecommendedItem>emptyList());
		}
	}

	public RecommendationResponse getRecommendedBecause(RecommendationParameters param) throws TasteException {
		List<RecommendedItem> recommendedBecauseList = ((ItemBasedRecommendationService) itemBasedRecommendationService).getRecommendedBecause(param);
		return new RecommendationResponse(param, recommendedBecauseList);
	}
}
