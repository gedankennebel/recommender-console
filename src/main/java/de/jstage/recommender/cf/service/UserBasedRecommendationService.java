package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.recommendationMisc.AdditionalRecommendationSettings;
import de.jstage.recommender.cf.recommendationMisc.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Service;

@Service("userBased")
public class UserBasedRecommendationService extends AbstractCfRecommendationService {

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
			case SPEARMAN_CORRELATION:
				return createRecommenderBuilder(new SpearmanCorrelationSimilarity(dataModel));
			case LOGLIKELIHOOD_SIMILARITY:
				return createRecommenderBuilder(new LogLikelihoodSimilarity(dataModel));
			case UNCENTERED_COSINE_SIMILARITY:
				return createRecommenderBuilder(new UncenteredCosineSimilarity(dataModel));
			default:
				return createRecommenderBuilder(new PearsonCorrelationSimilarity(dataModel));
		}
	}

	public RecommenderBuilder createRecommenderBuilder(UserSimilarity similarity) throws TasteException {
		UserNeighborhood neighborhood = getUserNeighborhood(recommendationSettings, similarity, dataModel);
		return model -> new GenericUserBasedRecommender(model, neighborhood, similarity);
	}

	private UserNeighborhood getUserNeighborhood(AdditionalRecommendationSettings settings, UserSimilarity similarity, DataModel dataModel) throws TasteException {
		double threshold = settings.getNeighbourhoodThreshold();
		switch (settings.getNeighborhoodType()) {
			case NEAREST_N:
				return new NearestNUserNeighborhood((int) threshold, similarity, dataModel);
			case THRESHOLD:
				return new ThresholdUserNeighborhood(threshold, similarity, dataModel);
			default:
				return new NearestNUserNeighborhood((int) threshold, similarity, dataModel);
		}
	}
}
