package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dao.StatusDao;
import com.entity.Status;

@Repository(value = "statusDao")
@Scope("singleton")
@Component
public class StatusDaoImpl implements StatusDao {

	private static final Logger LOGGER = LogManager.getLogger(CertificateDaoImpl.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Status> getAllStatus() {
		List<Status> allStatuses = new ArrayList<Status>();
		try {
			LOGGER.info("retrieving list of statuses");
			Query query = entityManager.createQuery("Select status from Status status Where status.validity=:validity");
			query.setParameter("validity", true);
			allStatuses = query.getResultList();
			LOGGER.info("list of status retrieved succesfully " + "size of list[" + allStatuses.size() + "]");

			return allStatuses;
		} catch (RuntimeException e) {

			LOGGER.error("error retrieving statuse!Message: " + e.getMessage(), e);
			return allStatuses;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Status> getOtherStatus(String currentStatus) {

		List<Status> otherStatuses = new ArrayList<Status>();
		try {
			LOGGER.info("retrieving list of statuses");
			Query query = entityManager.createQuery(
					"Select status from Status status Where status.validity=:validity And status.status!=:currentStatus");
			query.setParameter("validity", true).setParameter("currentStatus", currentStatus);
			otherStatuses = query.getResultList();
			LOGGER.info("list of status retrieved succesfully " + "size of list[" + otherStatuses.size() + "]");

			return otherStatuses;
		} catch (RuntimeException e) {

			LOGGER.error("error retrieving statuse!Message: " + e.getMessage(), e);
			return otherStatuses;
		}

	}

}
