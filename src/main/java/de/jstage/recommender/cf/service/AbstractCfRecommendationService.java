package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.model.EvaluationParameters;
import de.jstage.recommender.cf.model.RecommendationParameters;
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
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCfRecommendationService implements RecommendationService {

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
	public double getEvaluationScore(EvaluationParameters param) throws TasteException {
		double trainingPercentage = param.getTrainingPercentage();
		double evaluationPercentage = param.getEvaluationPercentage();
		SimilarityMetric metric = param.getSimilarityMetric();
		switch (param.getEvaluatorType()) {
			case AVERAGE_ABSOLUTE_DIFFERENCE:
				return new AverageAbsoluteDifferenceRecommenderEvaluator()
						.evaluate(getRecommenderBuilder(metric), null, dataModel, trainingPercentage, evaluationPercentage);
			case ROOT_MEAN_SQUARE:
				return new RMSRecommenderEvaluator()
						.evaluate(getRecommenderBuilder(metric), null, dataModel, trainingPercentage, evaluationPercentage);
			default:
				return 0;
		}
	}

	@Override
	public void refresh(SimilarityMetric metric) throws TasteException {
		getRecommender(metric).refresh(null);
		consoleMetaDataService.createConsoleMetaData();
	}

	// every 15min (60 * 1_000 * 15)
	@Override
	@Scheduled(fixedRate = 900_000)
	public void refreshPeriodically() throws TasteException {
		for (Map.Entry<SimilarityMetric, Recommender> RecommenderEntry : recommendationTyeMap.entrySet()) {
			refresh(RecommenderEntry.getKey());
		}
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
		recommendationTyeMap.put(similarityMetric, buildRecommender(getRecommenderBuilder(similarityMetric)));
		return recommendationTyeMap.get(similarityMetric);
	}

	private Recommender buildRecommender(RecommenderBuilder builder) throws TasteException {
		if (recommendationSettings.isCachingRecommender()) {
			return new CachingRecommender(builder.buildRecommender((dataModel)));
		} else {
			return builder.buildRecommender((dataModel));
		}
	}

	protected abstract RecommenderBuilder getRecommenderBuilder(SimilarityMetric similarityMetric) throws TasteException;
}
