package de.jstage.recommender.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.sql.DataSource;

public class AbstractDao {

	@Inject
	DataSource dataSource;

	protected JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
}
