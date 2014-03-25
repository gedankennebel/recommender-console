package de.jstage.recommender.dao;

import de.jstage.recommender.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

	@Inject
	JdbcTemplate jdbcTemplate;

	public static final String USER_ID_COLUMN = "USER_ID";

	public List<User> getAllUsers() {
		String sql = "SELECT USER_ID FROM INTRO";
		return jdbcTemplate.query(sql,
				(rs, rowNum) -> new User(rs.getLong(USER_ID_COLUMN)));
	}
}
