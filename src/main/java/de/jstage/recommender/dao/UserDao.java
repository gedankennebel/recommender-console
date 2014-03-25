package de.jstage.recommender.dao;

import de.jstage.recommender.model.User;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.List;

@Named
public class UserDao extends AbstractDao {

	public static final String USER_ID_COLUMN = "USER_ID";

	@Inject
	DataSource dataSource;

	public List<User> getAllUsers(String tableName) {
		String sql = "SELECT USER_ID FROM ?";
		return jdbcTemplate.query(sql,
				ps -> ps.setString(1, tableName),
				(rs, rowNum) -> new User(rs.getLong(USER_ID_COLUMN)));
	}
}
