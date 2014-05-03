package de.jstage.recommender.cf.service;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.similarity.precompute.FileSimilarItemsWriter;
import org.apache.mahout.cf.taste.impl.similarity.precompute.MultithreadedBatchItemSimilarities;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.similarity.precompute.BatchItemSimilarities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class PreComputationService {

	private static final Logger log = LoggerFactory.getLogger(PreComputationService.class);

	public String preComputeSimilarities(ItemBasedRecommender recommender) throws TasteException {
		String pathToAbsolutePath = "";
		try {
			File resultFile = new File(System.getProperty("java.io.tmpdir"), "similarities.csv");
			if (resultFile.exists()) {
				resultFile.delete();
			}
			BatchItemSimilarities batchJob = new MultithreadedBatchItemSimilarities(recommender, 15);
			int numSimilarities = batchJob.computeItemSimilarities(Runtime.getRuntime().availableProcessors(), 1,
					new FileSimilarItemsWriter(resultFile));
			pathToAbsolutePath = resultFile.getAbsolutePath();
			log.info("Computed " + numSimilarities + " similarities and saved them to " + pathToAbsolutePath);
		} catch (IOException e) {
			log.error("Error while writing pre computed similarities to file", e);
		}
		return pathToAbsolutePath;
	}
}
