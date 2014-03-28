package de.jstage.recommender.cf.config;

import de.jstage.recommender.cf.model.ConsoleMetaData;
import de.jstage.recommender.cf.model.User;
import de.jstage.recommender.cf.recommender.RecommendationType;
import de.jstage.recommender.cf.recommender.SimilarityMetric;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class RecommendationMetaConfig {

	@Inject
	DataModel dataModel;

	@Bean
	public ConsoleMetaData createConsoleMetaData() throws TasteException {
		Map<RecommendationType, List<SimilarityMetric>> map = new HashMap<>();
		getRecommendationTypes().forEach(type -> map.put(type, getSimilarityMetrics(type)));
		return new ConsoleMetaData(map, getUsers());
	}

	private List<RecommendationType> getRecommendationTypes() {
		return Arrays.asList(RecommendationType.values());
	}

	private List<SimilarityMetric> getSimilarityMetrics(RecommendationType recommendationType) {
		switch (recommendationType) {
			case ITEM_BASED:
				return Arrays.asList(SimilarityMetric.values()).stream()
						.filter(SimilarityMetric::isItemBasedApproach)
						.collect(Collectors.toList());
			case USER_BASED:
				return Arrays.asList(SimilarityMetric.values()).stream()
						.filter(SimilarityMetric::isUserBasedApproach)
						.collect(Collectors.toList());
			default:
				return Collections.emptyList();
		}
	}

	@Bean
	public List<User> getUsers() throws TasteException {
		List<User> userList = new ArrayList<>();
		dataModel.getUserIDs().forEachRemaining(userId -> userList.add(new User(userId)));
		return userList;
	}
}
