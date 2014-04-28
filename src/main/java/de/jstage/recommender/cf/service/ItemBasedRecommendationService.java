package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.model.RecommendationParameters;
import de.jstage.recommender.cf.recommendationMisc.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.GenericItemSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("itemBased")
public class ItemBasedRecommendationService extends AbstractCfRecommendationService {

	@Inject
	private PreComputationService preComputationService;

	@Inject
	private ItemSimilarity preComputedItemSimilarity;

	public List<RecommendedItem> getRecommendedBecause(RecommendationParameters param) throws TasteException {
		return getItemBasedRecommender(param.getAppliedSimilarity())
				.recommendedBecause(param.getUserId(), param.getItemId(), recommendationSettings.getNumberOfRecommendedBecause());
	}

	public List<RecommendedItem> getMostSimilarItems(RecommendationParameters param) throws TasteException {
		return getItemBasedRecommender(param.getAppliedSimilarity()).mostSimilarItems(param.getItemId(), param.getHowMany());
	}

	private ItemBasedRecommender getItemBasedRecommender(SimilarityMetric similarityMetric) throws TasteException {
		return (ItemBasedRecommender) getRecommender(similarityMetric);
	}

	@Override
	public RecommenderBuilder getRecommenderBuilder(SimilarityMetric similarityMetric) throws TasteException {
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
			case PRE_COMPUTED_SIMILARITY:
				return createRecommenderBuilder(preComputedItemSimilarity);
			default:
				return createRecommenderBuilder(new PearsonCorrelationSimilarity(dataModel));
		}
	}

	public RecommenderBuilder createRecommenderBuilder(ItemSimilarity similarity) throws TasteException {
		return model -> new GenericItemBasedRecommender(model, similarity);
	}

	@Bean(name = "preComputedItemSimilarity")
	public ItemSimilarity createPreComputedItemSimilarity() throws TasteException, FileNotFoundException {
		if (recommendationSettings.isItemPreComputationEnabled()) {
			SimilarityMetric similarityMetric = SimilarityMetric.valueOf("LOGLIKELIHOOD_SIMILARITY");
			RecommenderBuilder builder = getRecommenderBuilder(similarityMetric);
			ItemBasedRecommender itemBasedRecommender = (ItemBasedRecommender) builder.buildRecommender(dataModel);
			String pathToPreComputedFile = preComputationService.preComputeSimilarities(itemBasedRecommender);
			InputStream inputStream = new FileInputStream(new File(pathToPreComputedFile));
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			Collection<GenericItemSimilarity.ItemItemSimilarity> correlations = bufferedReader.lines()
					.map(mapToItemItemSimilarity)
					.collect(Collectors.toList());
			return new GenericItemSimilarity(correlations);
		} else {
			return null;
		}
	}

	private static Function<String, GenericItemSimilarity.ItemItemSimilarity> mapToItemItemSimilarity = (line) -> {
		String[] row = line.split(",");
		return new GenericItemSimilarity.ItemItemSimilarity(
				Long.parseLong(row[0]), Long.parseLong(row[1]), Double.parseDouble(row[2]));
	};
}
