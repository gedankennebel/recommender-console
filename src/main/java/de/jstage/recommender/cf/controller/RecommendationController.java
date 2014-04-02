package de.jstage.recommender.cf.controller;

import de.jstage.recommender.cf.recommender.RecommendationType;
import de.jstage.recommender.cf.recommender.SimilarityMetric;
import de.jstage.recommender.cf.service.CollaborativeFilteringRecommendationService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendationController {

	@Inject
	private CollaborativeFilteringRecommendationService cfRecommendationService;

	@RequestMapping(value = "/cf", method = RequestMethod.GET)
	public List<RecommendedItem> recommendItems(@RequestParam int howMany, @RequestParam RecommendationType recommendationType,
												@RequestParam SimilarityMetric similarityMetric, @RequestParam long userId) throws TasteException {
		return cfRecommendationService.getRecommendations(recommendationType, similarityMetric, howMany, userId);
	}
}
