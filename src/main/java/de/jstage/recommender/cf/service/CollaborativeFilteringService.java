package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.aspect.ComputingTimeAspect;
import de.jstage.recommender.cf.domain.EvaluationParameters;
import de.jstage.recommender.cf.domain.EvaluationResult;
import de.jstage.recommender.cf.domain.FullEvaluationResult;
import de.jstage.recommender.cf.domain.RecommendationParameters;
import de.jstage.recommender.cf.domain.RecommendationResponse;
import de.jstage.recommender.cf.recommendationMisc.RecommendationType;
import de.jstage.recommender.cf.recommendationMisc.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.impl.eval.LoadStatistics;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollaborativeFilteringService {

	private static final Logger logger = LoggerFactory.getLogger(CollaborativeFilteringService.class);

	@Resource(name = "itemBased")
	private RecommendationService itemBasedRecommendationService;

	@Resource(name = "userBased")
	private RecommendationService userBasedRecommendationService;

	@Inject
	private SimilarityTypeService similarityTypeService;

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

	public EvaluationResult getEvaluationScore(EvaluationParameters param) throws TasteException {
		return getRecommenderService(param.getRecommendationType()).getEvaluationScore(param);
	}

	public FullEvaluationResult performFullEvaluation(EvaluationParameters param) throws TasteException {
		int processCount = 1;
		RecommendationType recommendationType = param.getRecommendationType();
		logger.info("\n\n ---> Triggered full evaluation run for type: " + recommendationType.getDisplayName());
		long start = System.nanoTime();
		List<SimilarityMetric> similarityMetrics = similarityTypeService.getSimilarityMetrics(recommendationType);
		List<EvaluationResult> evaluationResultList = new ArrayList<>();
		final int numberOfMetrics = similarityMetrics.size();
		for (SimilarityMetric metric : similarityMetrics) {
			param.setSimilarityMetric(metric);
			EvaluationResult result = getEvaluationScore(param);
			logger.info("\n\n ---> " + String.valueOf(Math.ceil((processCount / (double) numberOfMetrics) * 100)) + "% finished\n");
			evaluationResultList.add(result);
			processCount++;
		}
		double end = ComputingTimeAspect.getCalculationTimeInSeconds(start, System.nanoTime());
		logger.info("\n\n Full evaluation finished in " + end + " seconds\n");
		return new FullEvaluationResult(recommendationType.getDisplayName(), param.getEvaluatorType().getDisplayName(), evaluationResultList, end);
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
