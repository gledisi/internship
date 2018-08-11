package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dao.UserDao;
import com.entity.User;

@Repository(value = "userDao")
@Scope("singleton")
@Component
public class UserDaoImpl implements UserDao {

	private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class.getName());

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
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

	@Transactional
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

	@Transactional
	public boolean delete(int userId) {
		try {

			LOGGER.info("deleting user!");
			entityManager.createQuery("update User set validity=:validity where id=:id").setParameter("validity", false)
					.setParameter("id", userId).executeUpdate();
			LOGGER.info("user delete!");
			return true;

		} catch (Exception e) {
			LOGGER.error("error delete user!Message: " + e.getMessage(), e);
			return false;
		}
	}

	@Transactional
	public User getUserFromId(int userId) {
		User user = null;
		try {
			LOGGER.info("retrieving user by id!");
			user = (User) entityManager.createQuery("select user from User user where user.id=:userId")
					.setParameter("userId", userId).getSingleResult();
			LOGGER.info("user by id retrieved!");
			return user;

		} catch (Exception e) {
			LOGGER.error("error retrieving user by id !Message: " + e.getMessage(), e);
			return null;
		}
	}

	@Transactional
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
	@Transactional
	public List<User> getEmployeesOfManager(int idManager) {
		List<User> users = new ArrayList<User>();
		try {
			LOGGER.info("retrieving users of manager!");
			users = entityManager
					.createQuery(
							"Select user From User user Where user.managedBy = :idManager and user.validity!=:validity")
					.setParameter("idManager", idManager).setParameter("validity", false).getResultList();
			LOGGER.info("user of manager retrieved!");
			return users;
		} catch (RuntimeException e) {

			LOGGER.error("error retrieving user of manager !Message: " + e.getMessage(), e);
			return users;
		}

	}

}
