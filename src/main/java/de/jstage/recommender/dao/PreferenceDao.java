package de.jstage.recommender.dao;

import de.jstage.recommender.model.User;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.List;

@Named
public class PreferenceDao extends AbstractDao {

	@Inject
	DataSource dataSource;

	public List<User> getAllUsers() {
		String sql = "select user_id from ";
		return null;
	}
}
