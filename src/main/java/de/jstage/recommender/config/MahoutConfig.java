package de.jstage.recommender.config;

import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
public class MahoutConfig {

	@Inject
	DataSource dataSource;

}
