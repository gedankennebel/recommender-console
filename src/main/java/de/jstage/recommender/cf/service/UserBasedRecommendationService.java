package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.recommender.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Service;

@Service("userBased")
public class UserBasedRecommendationService extends AbstractCFRecommendationService {

	@Override
	protected Recommender createRecommenderForGivenSimilarityMetric(SimilarityMetric similarityMetric) throws TasteException {
		switch (similarityMetric) {
			case PEARSON_CORRELATION:
				return buildRecommender(new PearsonCorrelationSimilarity(dataModel));
			case EUCLIDEAN_DISTANCE:
				return buildRecommender(new EuclideanDistanceSimilarity(dataModel));
			case TANIMOTO_COEFFICIENT:
				return buildRecommender(new TanimotoCoefficientSimilarity(dataModel));
			case CITY_BLOCK:
				return buildRecommender(new CityBlockSimilarity(dataModel));
			case SPEARMAN_CORRELATION:
				return buildRecommender(new SpearmanCorrelationSimilarity(dataModel));
			case LOGLIKELIHOOD_SIMILARITY:
				return buildRecommender(new LogLikelihoodSimilarity(dataModel));
			case UNCENTERED_COSINE_SIMILARITY:
				return buildRecommender(new UncenteredCosineSimilarity(dataModel));
			default:
				return buildRecommender(new PearsonCorrelationSimilarity(dataModel));
		}
	}

	private Recommender buildRecommender(UserSimilarity similarity) throws TasteException {
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(20, similarity, dataModel);
		RecommenderBuilder recommenderBuilder = model -> new GenericUserBasedRecommender(model, neighborhood, similarity);
		return recommenderBuilder.buildRecommender(dataModel);
	}
}
