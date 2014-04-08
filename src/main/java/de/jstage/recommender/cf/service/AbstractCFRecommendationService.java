package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.recommender.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import javax.inject.Inject;
import java.util.EnumMap;
import java.util.List;

public abstract class AbstractCFRecommendationService implements RecommendationService {

	@Inject
	protected DataModel dataModel;

	private EnumMap<SimilarityMetric, Recommender> recommendationTyeMap;

	@Override
	public List<RecommendedItem> getRecommendations(SimilarityMetric similarityMetric, int howMany, long userId) throws TasteException {
		return getRecommender(similarityMetric).recommend(userId, howMany);
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

	protected abstract Recommender createRecommenderForGivenSimilarityMetric(SimilarityMetric similarityMetric) throws TasteException;
}
