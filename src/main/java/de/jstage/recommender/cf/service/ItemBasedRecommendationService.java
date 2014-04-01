package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.recommender.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.EnumMap;

@Service
public class ItemBasedRecommendationService {

	@Inject
	private DataModel dataModel;

	private EnumMap<SimilarityMetric, Recommender> itemBasedRecommenderMap;

	public Recommender getItemBasedRecommender(SimilarityMetric similarityMetric) throws TasteException {
		if (itemBasedRecommenderMap != null) {
			return (itemBasedRecommenderMap.get(similarityMetric) != null) ?
					itemBasedRecommenderMap.get(similarityMetric) : putAndReturnRecommender(similarityMetric);
		} else {
			itemBasedRecommenderMap = new EnumMap<>(SimilarityMetric.class);
			return putAndReturnRecommender(similarityMetric);
		}
	}

	private Recommender putAndReturnRecommender(SimilarityMetric similarityMetric) throws TasteException {
		itemBasedRecommenderMap.put(similarityMetric, createRecommenderForGivenSimilarityMetric(similarityMetric));
		return itemBasedRecommenderMap.get(similarityMetric);
	}

	private Recommender createRecommenderForGivenSimilarityMetric(SimilarityMetric similarityMetric) throws TasteException {
		switch (similarityMetric) {
			case PEARSON_CORRELATION:
				System.out.println(similarityMetric.name() + "wurde aufgerufden");
				return buildRecommender(new PearsonCorrelationSimilarity(dataModel));
			case EUCLIDEAN_DISTANCE:
				System.out.println(similarityMetric.name() + "wurde aufgerufden");
				return buildRecommender(new EuclideanDistanceSimilarity(dataModel));
			case CITY_BLOCK:
				System.out.println(similarityMetric.name() + "wurde aufgerufden");
				return buildRecommender(new CityBlockSimilarity(dataModel));
			case TANIMOTO_COEFFICIENT:
				System.out.println(similarityMetric.name() + "wurde aufgerufden");
				return buildRecommender(new TanimotoCoefficientSimilarity(dataModel));
			default:
				return buildRecommender(new PearsonCorrelationSimilarity(dataModel));
		}
	}

	private Recommender buildRecommender(ItemSimilarity similarity) throws TasteException {
		RecommenderBuilder recommenderBuilder = model -> new GenericItemBasedRecommender(model, similarity);
		return recommenderBuilder.buildRecommender(dataModel);
	}
}
