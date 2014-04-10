package de.jstage.recommender.cf.controller;

import de.jstage.recommender.cf.model.RecommendationParameters;
import de.jstage.recommender.cf.model.RecommendationResponse;
import de.jstage.recommender.cf.recommendationMisc.AdditionalRecommendationSettings;
import de.jstage.recommender.cf.recommendationMisc.RecommendationType;
import de.jstage.recommender.cf.recommendationMisc.SimilarityMetric;
import de.jstage.recommender.cf.service.CollaborativeFilteringRecommendationService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

	@Inject
	private CollaborativeFilteringRecommendationService cfRecommendationService;

	@Inject
	private AdditionalRecommendationSettings settings;

	@RequestMapping(value = "/cf", method = RequestMethod.GET)
	public RecommendationResponse recommendItems(@RequestParam int howMany, @RequestParam RecommendationType recommendationType,
												 @RequestParam SimilarityMetric similarityMetric, @RequestParam long userId) throws TasteException {
		settings.setNumberOfRecommendation(howMany);
		return cfRecommendationService.getRecommendations(new RecommendationParameters(recommendationType, similarityMetric, howMany, userId));
	}

	@RequestMapping(value = "/because", method = RequestMethod.GET)
	public RecommendationResponse recommendedBecause(@RequestParam SimilarityMetric similarityMetric, @RequestParam long userId, @RequestParam long itemId) throws TasteException {
		return cfRecommendationService.getRecommendedBecause(new RecommendationParameters(similarityMetric, userId, itemId));
	}
}
