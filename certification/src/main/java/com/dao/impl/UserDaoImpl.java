package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

	@SuppressWarnings("unchecked")
	public List<User> getEmployeesOfManager(int idManager) {
		List<User> users = new ArrayList<User>();
		try {
			LOGGER.info("retrieving users of manager!");
			users = entityManager
					.createQuery(
							"Select user From User user Where user.managedBy = :idManager and user.validity=:validity")
					.setParameter("idManager", idManager).setParameter("validity", true).getResultList();
			LOGGER.info("user of manager retrieved!");
		} catch (RuntimeException e) {

			LOGGER.error("error retrieving user of manager !Message: " + e.getMessage(), e);

		}
		return users;
	}

}
