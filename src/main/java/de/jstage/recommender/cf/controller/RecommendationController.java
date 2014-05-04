package de.jstage.recommender.cf.controller;

import de.jstage.recommender.cf.domain.RecommendationParameters;
import de.jstage.recommender.cf.domain.RecommendationResponse;
import de.jstage.recommender.cf.recommendationMisc.AdditionalRecommendationSettings;
import de.jstage.recommender.cf.recommendationMisc.RecommendationType;
import de.jstage.recommender.cf.recommendationMisc.SimilarityMetric;
import de.jstage.recommender.cf.service.CollaborativeFilteringRecommendationService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public RecommendationResponse recommendItems(@RequestParam RecommendationType recommendationType, @RequestParam SimilarityMetric similarityMetric,
												 @RequestParam long userId, @RequestParam(required = false, defaultValue = "6") int howMany) throws TasteException {
		settings.setNumberOfRecommendation(howMany);
		return cfRecommendationService.getRecommendations(new RecommendationParameters(recommendationType, similarityMetric, howMany, userId));
	}

	@RequestMapping(value = "/because", method = RequestMethod.GET)
	public RecommendationResponse recommendedBecause(@RequestParam SimilarityMetric similarityMetric, @RequestParam long userId, @RequestParam long itemId, @RequestParam(required = false, defaultValue = "6") int howMany) throws TasteException {
		return cfRecommendationService.getRecommendedBecause(new RecommendationParameters(similarityMetric, userId, itemId, howMany));
	}

	@RequestMapping(value = "/similar", method = RequestMethod.GET)
	public RecommendationResponse mostSimilarItems(@RequestParam SimilarityMetric similarityMetric, @RequestParam long itemId, @RequestParam(required = false, defaultValue = "6") int howMany) throws TasteException {
		return cfRecommendationService.getMostSimilarItems(new RecommendationParameters(similarityMetric, itemId, howMany));
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public ResponseEntity<String> refresh(@RequestParam RecommendationType recommendationType, @RequestParam SimilarityMetric similarityMetric) throws TasteException {
		cfRecommendationService.refresh(recommendationType, similarityMetric);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

