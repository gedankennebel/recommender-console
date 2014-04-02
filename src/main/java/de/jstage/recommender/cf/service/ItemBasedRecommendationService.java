package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.recommender.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.EnumMap;
import java.util.List;

@Service("itemBased")
public class ItemBasedRecommendationService implements RecommendationService {

	@Inject
	private DataModel dataModel;

	private EnumMap<SimilarityMetric, Recommender> recommendationTyeMap;

	@Override
	public List<RecommendedItem> getRecommendations(SimilarityMetric similarityMetric, int howMany, long userId) throws TasteException {
		return getRecommender(similarityMetric).recommend(userId, howMany);
	}

	private Recommender getRecommender(SimilarityMetric similarityMetric) throws TasteException {
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

	private Recommender createRecommenderForGivenSimilarityMetric(SimilarityMetric similarityMetric) throws TasteException {
		switch (similarityMetric) {
			case PEARSON_CORRELATION:
				return buildRecommender(new PearsonCorrelationSimilarity(dataModel));
			case EUCLIDEAN_DISTANCE:
				return buildRecommender(new EuclideanDistanceSimilarity(dataModel));
			case TANIMOTO_COEFFICIENT:
				return buildRecommender(new TanimotoCoefficientSimilarity(dataModel));
			case CITY_BLOCK:
				return buildRecommender(new CityBlockSimilarity(dataModel));
			case LOGLIKELIHOOD_SIMILARITY:
				return buildRecommender(new LogLikelihoodSimilarity(dataModel));
			case UNCENTERED_COSINE_SIMILARITY:
				return buildRecommender(new UncenteredCosineSimilarity(dataModel));
			default:
				return buildRecommender(new PearsonCorrelationSimilarity(dataModel));
		}
	}

	private Recommender buildRecommender(ItemSimilarity similarity) throws TasteException {
		RecommenderBuilder recommenderBuilder = model -> new GenericItemBasedRecommender(model, similarity);
		return recommenderBuilder.buildRecommender(dataModel);
	}
}
