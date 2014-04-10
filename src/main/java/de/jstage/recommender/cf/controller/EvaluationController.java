package de.jstage.recommender.cf.controller;

import de.jstage.recommender.cf.recommendationMisc.RecommendationType;
import de.jstage.recommender.cf.recommendationMisc.SimilarityMetric;
import de.jstage.recommender.cf.service.CollaborativeFilteringRecommendationService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.eval.LoadStatistics;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

	@Inject
	CollaborativeFilteringRecommendationService cfRecommendationService;

	@RequestMapping(value = "/loadStat", method = RequestMethod.GET)
	public LoadStatistics getLoadStatistics(@RequestParam RecommendationType recommendationType, @RequestParam SimilarityMetric similarityMetric)
			throws TasteException {
		return cfRecommendationService.getLoadStatistics(recommendationType, similarityMetric);
	}
}
