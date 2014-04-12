package de.jstage.recommender.cf.config;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Configuration
@PropertySources(value = {@PropertySource("/mahoutConfig.properties")})
public class MahoutDataModelConfig {

	@Inject
	private ServletContext servletContext;

	@Inject
	private Environment environment;

	@Bean(name = "fileDataModel")
	public DataModel fileDataModel() throws IOException {
		final String filePath = servletContext.getRealPath("/resources/preference_csv/");
		final String fileName = environment.getProperty("dataModelFile");
		System.out.println("Starting Recommender-Server based on following data file= " + fileName);
		return new FileDataModel(new File(filePath + fileName));
	}

//	@Bean
//	public DataSource dataSource() {
//		BasicDataSource basicDataSource = new BasicDataSource();
//		basicDataSource.setDriverClassName(DATABASE_DRIVER);
//		basicDataSource.setUrl(DATABASE_URL);
//		basicDataSource.setPassword(DATABASE_PASSWORD);
//		basicDataSource.setUsername(DATABASE_USERNAME);
//		return basicDataSource;
//	}
//
//	@Bean
//	public JdbcTemplate jdbcTemplate() {
//		return new JdbcTemplate(dataSource());
//	}
}
