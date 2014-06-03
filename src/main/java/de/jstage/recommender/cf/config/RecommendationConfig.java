package de.jstage.recommender.cf.config;

import de.jstage.recommender.cf.domain.RecommendationSettings;
import de.jstage.recommender.cf.enums.NeighborhoodType;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

@Configuration
@PropertySources(value = {@PropertySource("/mahout.properties")})
public class RecommendationConfig {

  @Inject
  private DataModel dataModel;

  @Inject
  private Environment environment;

  @Bean
  public RecommendationSettings defaultSetting() {
    int numberOfRecommendation = Integer.parseInt(environment.getProperty("numberOfRecommendation"));
    int numberOfRecommendedBecause = Integer.parseInt(environment.getProperty("numberOfRecommendedBecause"));
    double neighborhoodThreshold = Double.parseDouble(environment.getProperty("neighborhoodThreshold"));
    boolean isCachingRecommender = Boolean.parseBoolean(environment.getProperty("cachingRecommender"));
    boolean isItemPreComputationEnabled = Boolean.parseBoolean(environment.getProperty("isItemPreComputationEnabled"));
    String preComputationSimilarityMetric = environment.getProperty("preComputationSimilarityMetric");
    String neighorhoodType = environment.getProperty("neighborhoodType");
    return new RecommendationSettings(NeighborhoodType.valueOf(neighorhoodType),
            numberOfRecommendation, numberOfRecommendedBecause, neighborhoodThreshold, isCachingRecommender,
            isItemPreComputationEnabled, preComputationSimilarityMetric);
  }
}
