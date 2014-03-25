package de.jstage.recommender.config;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class MahoutConfig {

	@Inject
	DataSource dataSource;

	@Bean
	public JDBCDataModel jdbcDataModel() {
		return new MySQLJDBCDataModel(dataSource, "intro",
				MySQLJDBCDataModel.DEFAULT_USER_ID_COLUMN,
				MySQLJDBCDataModel.DEFAULT_ITEM_ID_COLUMN,
				MySQLJDBCDataModel.DEFAULT_PREFERENCE_COLUMN,
				MySQLJDBCDataModel.DEFAULT_PREFERENCE_TIME_COLUMN);
	}

	@Bean
	public ItemBasedRecommender itemBasedRecommender() throws IOException, TasteException {
		ItemSimilarity similarity = new PearsonCorrelationSimilarity(jdbcDataModel());
		ItemBasedRecommender recommender = new GenericItemBasedRecommender(jdbcDataModel(), similarity);
		return recommender;
	}
}
