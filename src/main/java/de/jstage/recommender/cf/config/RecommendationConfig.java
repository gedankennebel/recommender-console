package de.jstage.recommender.cf.config;

import de.jstage.recommender.cf.aspect.ComputingTimeAspect;
import de.jstage.recommender.cf.model.ConsoleMetaData;
import de.jstage.recommender.cf.model.User;
import de.jstage.recommender.cf.recommendationMisc.AdditionalRecommendationSettings;
import de.jstage.recommender.cf.recommendationMisc.RecommendationType;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		int neighborhoodThreshold = Integer.parseInt(environment.getProperty("neighborhoodThreshold"));
		return new AdditionalRecommendationSettings(numberOfRecommendation, numberOfRecommendedBecause, neighborhoodThreshold);
	}

	@Bean
	public ConsoleMetaData createConsoleMetaData() throws TasteException {
		return new ConsoleMetaData(getRecommendationTypes(), getUsers(),
				getNumberOfUsers(), getNuberOfItem(), ComputingTimeAspect.dataModelCreationTime);
	}

	private List<RecommendationType> getRecommendationTypes() {
		return Arrays.asList(RecommendationType.values());
	}

	private List<User> getUsers() throws TasteException {
		List<User> userList = new ArrayList<>();
		dataModel.getUserIDs().forEachRemaining(userId -> userList.add(new User(userId)));
		return userList;
	}

	private int getNumberOfUsers() throws TasteException {
		return dataModel.getNumUsers();
	}

	private int getNuberOfItem() throws TasteException {
		return dataModel.getNumItems();
	}
}
