package de.jstage.recommender.cf.config;

import de.jstage.recommender.cf.aspect.ComputingTimeAspect;
import de.jstage.recommender.cf.model.ConsoleMetaData;
import de.jstage.recommender.cf.model.User;
import de.jstage.recommender.cf.recommendationMisc.AdditionalRecommendationSettings;
import de.jstage.recommender.cf.recommendationMisc.NeighborhoodType;
import de.jstage.recommender.cf.recommendationMisc.RecommendationType;
import de.jstage.recommender.cf.utils.SpliteratorUtil;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Configuration
@PropertySources(value = {@PropertySource("/mahoutConfig.properties")})
public class RecommendationConfig {

	@Inject
	private DataModel dataModel;

	@Inject
	private Environment environment;

	@Bean
	public AdditionalRecommendationSettings defaultSetting() {
		int numberOfRecommendation = Integer.parseInt(environment.getProperty("numberOfRecommendation"));
		int numberOfRecommendedBecause = Integer.parseInt(environment.getProperty("numberOfRecommendedBecause"));
		double neighborhoodThreshold = Double.parseDouble(environment.getProperty("neighborhoodThreshold"));
		boolean isCachingRecommender = Boolean.parseBoolean(environment.getProperty("cachingRecommender"));
		String neighorhoodType = environment.getProperty("neighborhoodType");
		return new AdditionalRecommendationSettings(NeighborhoodType.valueOf(neighorhoodType),
				numberOfRecommendation, numberOfRecommendedBecause, neighborhoodThreshold, isCachingRecommender);
	}

	@Bean
	public ConsoleMetaData createConsoleMetaData() throws TasteException {
		return new ConsoleMetaData(getRecommendationTypes(), getMax100Users(),
				getNumberOfUsers(), getNuberOfItem(), ComputingTimeAspect.dataModelCreationTime);
	}

	private List<RecommendationType> getRecommendationTypes() {
		return Arrays.asList(RecommendationType.values());
	}

	private List<User> getMax100Users() throws TasteException {
		final int MAX_USERS = 100;
		return StreamSupport.stream(getSpliterator(), true)
				.limit(MAX_USERS)
				.map(User::new)
				.collect(Collectors.toList());
	}

	private Spliterator<Long> getSpliterator() throws TasteException {
		return SpliteratorUtil.getSpliterator(dataModel.getUserIDs());
	}

	private int getNumberOfUsers() throws TasteException {
		return dataModel.getNumUsers();
	}

	private int getNuberOfItem() throws TasteException {
		return dataModel.getNumItems();
	}
}
