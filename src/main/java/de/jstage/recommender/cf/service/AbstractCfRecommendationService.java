package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.aspect.ComputingTimeAspect;
import de.jstage.recommender.cf.domain.EvaluationParameters;
import de.jstage.recommender.cf.domain.EvaluationResult;
import de.jstage.recommender.cf.domain.RecommendationParameters;
import de.jstage.recommender.cf.recommendationMisc.AdditionalRecommendationSettings;
import de.jstage.recommender.cf.recommendationMisc.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.LoadEvaluator;
import org.apache.mahout.cf.taste.impl.eval.LoadStatistics;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCfRecommendationService implements RecommendationService {

	private static final Logger log = LoggerFactory.getLogger(AbstractCfRecommendationService.class);

	@Inject
	protected DataModel dataModel;

	@Inject
	private ConsoleMetaDataService consoleMetaDataService;

	@Inject
	protected AdditionalRecommendationSettings recommendationSettings;

	private EnumMap<SimilarityMetric, Recommender> recommendationTyeMap;

	@Override
	public List<RecommendedItem> getRecommendations(RecommendationParameters param) throws TasteException {
		return getRecommender(param.getAppliedSimilarity()).recommend(param.getUserId(), param.getHowMany());
	}

	@Override
	public LoadStatistics getLoadStatistics(SimilarityMetric similarityMetric) throws TasteException {
		return LoadEvaluator.runLoad(getRecommender(similarityMetric));
	}

	@Override
	public IRStatistics getIRStatistics(EvaluationParameters param) throws TasteException {
		return new GenericRecommenderIRStatsEvaluator()
				.evaluate(getRecommenderBuilder(param.getSimilarityMetric()), null, dataModel, null, param.getRecallAt(),
						GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, param.getEvaluationPercentage());
	}

	@Override
	public EvaluationResult getEvaluationScore(EvaluationParameters param) throws TasteException {
		double trainingPercentage = param.getTrainingPercentage();
		double evaluationPercentage = param.getEvaluationPercentage();
		SimilarityMetric metric = param.getSimilarityMetric();
		long start = System.nanoTime();
		switch (param.getEvaluatorType()) {
			case AVERAGE_ABSOLUTE_DIFFERENCE:
				double score = new AverageAbsoluteDifferenceRecommenderEvaluator()
						.evaluate(getRecommenderBuilder(metric), null, dataModel, trainingPercentage, evaluationPercentage);
				return new EvaluationResult(param.getRecommendationType().getDisplayName(), metric.getDisplayName(), score,
						ComputingTimeAspect.getCalculationTimeInSeconds(start, System.nanoTime()));
			case ROOT_MEAN_SQUARE:
				double rmsScore = new RMSRecommenderEvaluator()
						.evaluate(getRecommenderBuilder(metric), null, dataModel, trainingPercentage, evaluationPercentage);
				return new EvaluationResult(param.getRecommendationType().getDisplayName(), metric.getDisplayName(), rmsScore,
						ComputingTimeAspect.getCalculationTimeInSeconds(start, System.nanoTime()));
			default:
				return new EvaluationResult();
		}
	}

	@Override
	public void refresh(SimilarityMetric metric) throws TasteException {
		log.info("\nExplicit refresh triggered");
		long start = System.nanoTime();
		getRecommender(metric).refresh(null);
		consoleMetaDataService.createConsoleMetaData();
		double end = ComputingTimeAspect.getCalculationTimeInMilliseconds(start, System.nanoTime());
		log.info("\nExplicit refresh took " + end + "ms");
	}

	// every 15min (60 * 1_000 * 15 = 900_000)
	@Scheduled(fixedRate = 900_000)
	private void autoRefresh() throws TasteException {
		log.info("\n\nPeriodical automatic full refresh triggered");
		long start = System.nanoTime();
		for (Map.Entry<SimilarityMetric, Recommender> RecommenderEntry : recommendationTyeMap.entrySet()) {
			getRecommender(RecommenderEntry.getKey()).refresh(null);
		}
		double end = ComputingTimeAspect.getCalculationTimeInMilliseconds(start, System.nanoTime());
		log.info("\n\nPeriodical refresh took " + end + "ms");
		consoleMetaDataService.createConsoleMetaData();
	}

	protected Recommender getRecommender(SimilarityMetric similarityMetric) throws TasteException {
		if (recommendationTyeMap != null) {
			return (recommendationTyeMap.get(similarityMetric) != null) ?
					recommendationTyeMap.get(similarityMetric) : putAndReturnRecommender(similarityMetric);
		} else {
			recommendationTyeMap = new EnumMap<>(SimilarityMetric.class);
			return putAndReturnRecommender(similarityMetric);
		}
	}

	private Recommender putAndReturnRecommender(SimilarityMetric similarityMetric) throws TasteException {
		recommendationTyeMap.put(similarityMetric, getRecommenderBuilder(similarityMetric).buildRecommender(dataModel));
		return recommendationTyeMap.get(similarityMetric);
	}

	protected abstract RecommenderBuilder getRecommenderBuilder(SimilarityMetric similarityMetric) throws TasteException;
}
