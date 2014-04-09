package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.recommendationMisc.RecommendationType;
import de.jstage.recommender.cf.recommendationMisc.SimilarityMetric;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimilarityTypeService {

	public List<SimilarityMetric> getSimilarityMetrics(RecommendationType recommendationType) {
		switch (recommendationType) {
			case ITEM_BASED:
				return Arrays.stream(SimilarityMetric.values())
						.filter(SimilarityMetric::isItemBasedApproach)
						.collect(Collectors.toList());
			case USER_BASED:
				return Arrays.stream(SimilarityMetric.values())
						.filter(SimilarityMetric::isUserBasedApproach)
						.collect(Collectors.toList());
			default:
				return Collections.emptyList();
		}
	}
}
