package de.jstage.recommender.cf.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.ConnectionPoolDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
@PropertySources(value = @PropertySource("/jdbc.properties"))
public class DataSourceConfig {

	@Inject
	private Environment environment;

	@Bean
	public DataSource getDataSource() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName(environment.getProperty("db.serverName"));
		dataSource.setUser(environment.getProperty("db.username"));
		dataSource.setPassword(environment.getProperty("db.password"));
		dataSource.setDatabaseName(environment.getProperty("db.name"));
		return new ConnectionPoolDataSource(dataSource);
	}
}
