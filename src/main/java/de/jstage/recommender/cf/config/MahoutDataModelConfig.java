package de.jstage.recommender.cf.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ConnectionPoolDataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

@Configuration
@PropertySources(value = {@PropertySource("/mahout.properties"), @PropertySource("/jdbc.properties")})
public class MahoutDataModelConfig {

	@Inject
	private ServletContext servletContext;

	@Inject
	private Environment env;

	@Profile("file")
	@Bean
	public DataModel getDataModelFromFile() throws IOException {
		final String filePath = servletContext.getRealPath("/resources/preference_csv/");
		final String fileName = env.getProperty("dataModelFile");
		System.out.println("Starting Recommender-Server based on following data file= " + fileName);
		return new FileDataModel(new File(filePath + fileName));
	}


	@Profile("db")
	@Bean
	public DataModel getDataModelFromDataBase() throws TasteException {
		MySQLJDBCDataModel jdbcDataModel = new MySQLJDBCDataModel(getDataSource(), env.getProperty("db.table"),
				MySQLJDBCDataModel.DEFAULT_USER_ID_COLUMN,
				MySQLJDBCDataModel.DEFAULT_ITEM_ID_COLUMN,
				MySQLJDBCDataModel.DEFAULT_PREFERENCE_COLUMN,
				MySQLJDBCDataModel.DEFAULT_PREFERENCE_TIME_COLUMN);
		if (Boolean.valueOf(env.getProperty("loadDbIntoRam"))) {
			return new ReloadFromJDBCDataModel(jdbcDataModel);
		} else {
			return jdbcDataModel;
		}
	}

	private DataSource getDataSource() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName(env.getProperty("db.serverName"));
		dataSource.setUser(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		dataSource.setDatabaseName(env.getProperty("db.name"));
		return new ConnectionPoolDataSource(dataSource);
	}

}
