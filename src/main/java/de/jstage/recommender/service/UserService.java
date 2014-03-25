package de.jstage.recommender.service;

import de.jstage.recommender.dao.UserDao;
import de.jstage.recommender.model.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class UserService {

	@Inject
	UserDao userDao;

	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
}
