package de.jstage.recommender.cf.controller;

import de.jstage.recommender.cf.domain.EvaluationParameters;
import de.jstage.recommender.cf.domain.EvaluationResult;
import de.jstage.recommender.cf.domain.FullEvaluationResult;
import de.jstage.recommender.cf.enums.EvaluatorType;
import de.jstage.recommender.cf.enums.RecommendationType;
import de.jstage.recommender.cf.enums.SimilarityMetric;
import de.jstage.recommender.cf.service.CollaborativeFilteringService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
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
	private CollaborativeFilteringService cfRecommendationService;

	@RequestMapping(value = "/loadTest", method = RequestMethod.GET)
	public LoadStatistics getLoadStatistics(@RequestParam RecommendationType recommendationType, @RequestParam SimilarityMetric similarityMetric)
			throws TasteException {
		return cfRecommendationService.getLoadStatistics(recommendationType, similarityMetric);
	}

	@RequestMapping(value = "/IR")
	public IRStatistics getIRStatistics(@RequestParam RecommendationType recommendationType, @RequestParam SimilarityMetric similarityMetric,
										@RequestParam int recallAt, @RequestParam double evaluatePercentage) throws TasteException {
		return cfRecommendationService.
				getIRStatistics(new EvaluationParameters(recommendationType, similarityMetric, recallAt, evaluatePercentage));
	}

	@RequestMapping(value = "/score")
	public EvaluationResult getEvaluationScore(@RequestParam RecommendationType recommendationType, @RequestParam SimilarityMetric similarityMetric,
									 @RequestParam EvaluatorType evaluatorType, @RequestParam double trainingPercentage, @RequestParam double evaluatePercentage) throws TasteException {
		return cfRecommendationService.
				getEvaluationScore(new EvaluationParameters(recommendationType, similarityMetric, evaluatorType, trainingPercentage, evaluatePercentage));
	}

	@RequestMapping(value = "/fullScore")
	public FullEvaluationResult runFullEvaluation(@RequestParam RecommendationType recommendationType, @RequestParam EvaluatorType evaluatorType,
												  @RequestParam double trainingPercentage, @RequestParam double evaluatePercentage) throws TasteException {
		return cfRecommendationService.
				performFullEvaluation(new EvaluationParameters(recommendationType, evaluatorType, trainingPercentage, evaluatePercentage));
	}
}
