//package de.jstage.recommender.cf.config;
//
//import org.apache.commons.dbcp.BasicDataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//
//	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
//	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/test";
//	private static final String DATABASE_PASSWORD = "root";
//	private static final String DATABASE_USERNAME = "root";
//
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
//}
