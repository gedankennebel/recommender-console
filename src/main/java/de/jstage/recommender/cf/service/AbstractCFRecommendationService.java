package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.model.RecommendationParameters;
import de.jstage.recommender.cf.recommendationMisc.AdditionalRecommendationSettings;
import de.jstage.recommender.cf.recommendationMisc.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.eval.LoadEvaluator;
import org.apache.mahout.cf.taste.impl.eval.LoadStatistics;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import javax.inject.Inject;
import java.util.EnumMap;
import java.util.List;

public abstract class AbstractCfRecommendationService implements RecommendationService {

	@Inject
	protected DataModel dataModel;

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
		recommendationTyeMap.put(similarityMetric, createRecommenderForGivenSimilarityMetric(similarityMetric));
		return recommendationTyeMap.get(similarityMetric);
	}

	protected Recommender getCachingDecoratedRecommender(Recommender recommender) throws TasteException {
		return new CachingRecommender(recommender);
	}

	protected abstract Recommender createRecommenderForGivenSimilarityMetric(SimilarityMetric similarityMetric) throws TasteException;
}
