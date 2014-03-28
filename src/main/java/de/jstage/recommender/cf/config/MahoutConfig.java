package de.jstage.recommender.cf.config;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Configuration
public class MahoutConfig {

	@Inject
	ServletContext servletContext;

	@Bean(name = "fileDataModel")
	public DataModel fileDataModel() throws IOException {
		return new FileDataModel(new File(servletContext.getRealPath("/resources/preference_csv/intro.csv")));
	}
}
