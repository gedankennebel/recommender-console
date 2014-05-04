package de.jstage.recommender.cf.misc;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.SamplingCandidateItemsStrategy;
import org.apache.mahout.cf.taste.impl.similarity.GenericItemSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.precompute.FileSimilarItemsWriter;
import org.apache.mahout.cf.taste.impl.similarity.precompute.MultithreadedBatchItemSimilarities;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.BatchItemSimilarities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecommenderTest {
	public static void main(String[] args) throws IOException, TasteException {
		DataModel dataModel = new FileDataModel(new File("/Users/najum/Documents/recommender-console/src/main/webapp/resources/preference_csv/1mil.csv"));
		final int numberOfUsers = dataModel.getNumUsers();
		final int numberOfItems = dataModel.getNumItems();
		ItemSimilarity similarity = new LogLikelihoodSimilarity(dataModel);
		ItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, similarity,
				new SamplingCandidateItemsStrategy(10, 10, 10, numberOfUsers, numberOfItems),
				new SamplingCandidateItemsStrategy(10, 10, 10, numberOfUsers, numberOfItems));

		String pathToPreComputedFile = preComputeSimilarities(recommender, 50);

		InputStream inputStream = new FileInputStream(new File(pathToPreComputedFile));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		Collection<GenericItemSimilarity.ItemItemSimilarity> correlations = bufferedReader.lines().map(mapToItemItemSimilarity).collect(Collectors.toList());
		ItemSimilarity precomputedSimilarity = new GenericItemSimilarity(correlations);
		ItemBasedRecommender recommenderWithPrecomputation = new GenericItemBasedRecommender(dataModel, precomputedSimilarity,
				new SamplingCandidateItemsStrategy(10, 10, 10, numberOfUsers, numberOfItems),
				new SamplingCandidateItemsStrategy(10, 10, 10, numberOfUsers, numberOfItems));
//
		UserBasedRecommender userBasedRecommender = new GenericUserBasedRecommender(dataModel,
				new NearestNUserNeighborhood(50, new LogLikelihoodSimilarity(dataModel), dataModel),
				new LogLikelihoodSimilarity(dataModel));
//
		System.out.println("\nItemBased:");
		recommend(recommender);

		System.out.println("\nItemBased (precomputed):");
		recommend(recommenderWithPrecomputation);
//
		System.out.println("\nUserbased:");
		recommend(userBasedRecommender);
	}

	private static void recommend(Recommender recommender) throws TasteException {
		long start = System.nanoTime();
		List<RecommendedItem> recommendations = recommender.recommend(1, 10);
		long end = System.nanoTime();
		System.out.println("Created recommendations in " + getCalculationTimeInMilliseconds(start, end) + " ms. Recommendations:" + recommendations);
	}

	private static double getCalculationTimeInMilliseconds(long start, long end) {
		double calculationTime = (end - start);
		return (calculationTime / 1_000_000);
	}


	private static Function<String, GenericItemSimilarity.ItemItemSimilarity> mapToItemItemSimilarity = (line) -> {
		String[] row = line.split(",");
		return new GenericItemSimilarity.ItemItemSimilarity(
				Long.parseLong(row[0]), Long.parseLong(row[1]), Double.parseDouble(row[2]));
	};

	private static String preComputeSimilarities(ItemBasedRecommender recommender, int simItemsPerItem) throws TasteException {
		String pathToAbsolutePath = "";
		try {
			File resultFile = new File(System.getProperty("java.io.tmpdir"), "similarities.csv");
			if (resultFile.exists()) {
				resultFile.delete();
			}
			BatchItemSimilarities batchJob = new MultithreadedBatchItemSimilarities(recommender, simItemsPerItem);
			int numSimilarities = batchJob.computeItemSimilarities(Runtime.getRuntime().availableProcessors(), 1,
					new FileSimilarItemsWriter(resultFile));
			pathToAbsolutePath = resultFile.getAbsolutePath();
			System.out.println("Computed " + numSimilarities + " similarities and saved them to " + pathToAbsolutePath);
		} catch (IOException e) {
			System.out.println("Error while writing pre computed similarities to file");
		}
		return pathToAbsolutePath;
	}

}

