package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.dao.StatusDao;
import com.entity.Status;

@Repository(value = "statusDao")
@Scope("singleton")
@Component
public class StatusDaoImpl implements StatusDao {

	private static final Logger LOGGER = LogManager.getLogger(CertificateDaoImpl.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	public List<Status> getOtherStatus(String currentStatus) {

		List<Status> status = new ArrayList<Status>();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Select status from Status status");
		stringBuilder.append(" Where status.validity=:validity");

		if (currentStatus != null && !currentStatus.trim().isEmpty()) {
			stringBuilder.append(" And status.status!=:currentStatus");
		}

		try {
			LOGGER.info("retrieving list of statuses");
			TypedQuery<Status> query = entityManager.createQuery(stringBuilder.toString(), Status.class);
			query.setParameter("validity", true);
			if (currentStatus != null && !currentStatus.trim().isEmpty()) {
				query.setParameter("currentStatus", currentStatus);
			}
			status = query.getResultList();
			LOGGER.info("list of status retrieved succesfully " + "size of list[" + status.size() + "]");

		} catch (RuntimeException e) {

			LOGGER.error("error retrieving statuse!Message: " + e.getMessage(), e);

		}
		return status;
	}

}
