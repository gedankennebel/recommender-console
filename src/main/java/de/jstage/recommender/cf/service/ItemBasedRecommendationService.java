package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.model.RecommendationParameters;
import de.jstage.recommender.cf.recommendationMisc.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("itemBased")
public class ItemBasedRecommendationService extends AbstractCfRecommendationService {

	public List<RecommendedItem> getRecommendedBecause(RecommendationParameters param) throws TasteException {
		ItemBasedRecommender itemBasedrecommender = ((ItemBasedRecommender) getRecommender(param.getAppliedSimilarity()));
		return itemBasedrecommender
				.recommendedBecause(param.getUserId(), param.getItemId(), recommendationSettings.getNumberOfRecommendedBecause());
	}

	@Override
	protected RecommenderBuilder getRecommenderBuilder(SimilarityMetric similarityMetric) throws TasteException {
		switch (similarityMetric) {
			case PEARSON_CORRELATION:
				return createRecommenderBuilder(new PearsonCorrelationSimilarity(dataModel));
			case EUCLIDEAN_DISTANCE:
				return createRecommenderBuilder(new EuclideanDistanceSimilarity(dataModel));
			case TANIMOTO_COEFFICIENT:
				return createRecommenderBuilder(new TanimotoCoefficientSimilarity(dataModel));
			case CITY_BLOCK:
				return createRecommenderBuilder(new CityBlockSimilarity(dataModel));
			case LOGLIKELIHOOD_SIMILARITY:
				return createRecommenderBuilder(new LogLikelihoodSimilarity(dataModel));
			case UNCENTERED_COSINE_SIMILARITY:
				return createRecommenderBuilder(new UncenteredCosineSimilarity(dataModel));
			default:
				return createRecommenderBuilder(new PearsonCorrelationSimilarity(dataModel));
		}
	}

	private RecommenderBuilder createRecommenderBuilder(ItemSimilarity similarity) throws TasteException {
		return model -> new GenericItemBasedRecommender(model, similarity);
	}
}
