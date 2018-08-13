package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.dao.UserDao;
import com.entity.User;

@Repository(value = "userDao")
@Scope("singleton")
public class UserDaoImpl implements UserDao {

	private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class.getName());

	@PersistenceContext
	EntityManager entityManager;

	public boolean add(User user) {
		try {
			LOGGER.info("adding User!");
			entityManager.persist(user);
			LOGGER.info("user added!");
			return true;

		} catch (RuntimeException e) {

			LOGGER.error("error adding user !Message: " + e.getMessage(), e);
			return false;
		}
	}

	public boolean edit(User user) {
		try {
			LOGGER.info("editing user!");
			entityManager.merge(user);
			LOGGER.info("user edit!");
			return true;

		} catch (RuntimeException e) {

			LOGGER.error("error editing user!Message: " + e.getMessage(), e);
			return false;
		}
	}

	public boolean delete(int userId) {
		try {

			User user = entityManager.find(User.class, userId);
			LOGGER.info("deleting user!" + user.getFirstname() + "with id=" + user.getId());
			user.setValidity(false);
			user.getCertifications().stream().filter(certification -> certification.isValidity())
					.forEach(certification -> certification.setValidity(false));
			entityManager.merge(user);
			LOGGER.info("user delete!");
			return true;

		} catch (Exception e) {
			LOGGER.error("error delete user!Message: " + e.getMessage(), e);
			return false;
		}
	}

	public User getUserFromId(int userId) {
		User user = new User();
		try {
			LOGGER.info("retrieving user by id!" + userId);
			user = (User) entityManager.find(User.class, userId);
			LOGGER.info("user by id retrieved!");

		} catch (Exception e) {
			LOGGER.error("error retrieving user by id !Message: " + e.getMessage(), e);
		}
		return user;
	}

	public User getLoggedUser(String email) {

		User user = null;
		try {
			LOGGER.info("retrieving user by email!");
			user = (User) entityManager.createQuery("select user from User user where user.email=:email")
					.setParameter("email", email).getSingleResult();
			LOGGER.info("user by email retrieved!");
			return user;

		} catch (Exception e) {

			LOGGER.error("error retrieving user by email !Message: " + e.getMessage(), e);
			return null;
		}

	}

	public List<User> getEmployeesOfManager(String inputSearch, int idManager) {
		List<User> users = new ArrayList<User>();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Select user From User user Where  user.validity=:validity");
		stringBuilder.append(" And user.managedBy = :idManager ");

		if (inputSearch != null && !inputSearch.trim().isEmpty()) {
			stringBuilder.append(" And user.firstname LIKE :inputSearch");
		}
		try {
			LOGGER.info("retrieving users of manager![Input Search=" + inputSearch + ";Id Manager=" + idManager + "]");
			TypedQuery<User> query = entityManager.createQuery(stringBuilder.toString(), User.class);
			query.setParameter("validity", true);
			query.setParameter("idManager", idManager);

			if (inputSearch != null && !inputSearch.trim().isEmpty()) {
				query.setParameter("inputSearch", "%" + inputSearch + "%");
			}

			users = query.getResultList();
			LOGGER.info("user of manager retrieved!" + users);
		} catch (RuntimeException e) {

			LOGGER.error("error retrieving user of manager !Message: " + e.getMessage(), e);

		}
		return users;
	}

	public boolean changePassword(int userId, String newPassword) {
		try {
			LOGGER.info("Changin password for user with id[" + userId + "]");
			entityManager.createQuery("update User  set password =:password where id=:userId")
					.setParameter("password", newPassword).setParameter("userId", userId).executeUpdate();
			LOGGER.info("Password changed!");
			return true;
		} catch (Exception e) {
			LOGGER.error("Password failed to change! Message " + e.getMessage());
			return false;

		}
	}

}
