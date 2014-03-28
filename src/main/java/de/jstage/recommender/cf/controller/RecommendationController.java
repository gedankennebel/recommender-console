package de.jstage.recommender.cf.controller;

import de.jstage.recommender.cf.recommender.SimilarityMetric;
import de.jstage.recommender.cf.service.ItemBasedRecommendationService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RequestMapping("/recommend")
@RestController
public class RecommendationController {

	@Inject
	private ItemBasedRecommendationService recommendationService;

	@RequestMapping(value = "/itemBased", method = RequestMethod.GET)
	public List<RecommendedItem> recommendItems(@RequestParam String similarityMetric, @RequestParam long userId, @RequestParam int howMany) throws TasteException {
		return recommendationService.getItemBasedRecommender(SimilarityMetric.valueOf(similarityMetric)).recommend(userId, howMany);
	}
}
