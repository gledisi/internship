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

import com.dao.CertificationDao;
import com.entity.Certification;

@Repository(value = "certificationDao")
@Scope("singleton")
@Component
public class CertificationDaoImpl implements CertificationDao {

	private static final Logger LOGGER = LogManager.getLogger(CertificationDaoImpl.class.getName());

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public boolean add(Certification certification) {
		try {
			LOGGER.info("adding certification!");
			entityManager.persist(certification);
			LOGGER.info("certification added!");
			return true;

		} catch (RuntimeException e) {

			LOGGER.error("error adding certification!Message: " + e.getMessage(), e);
			return false;
		}
	}

	@Transactional
	public boolean edit(Certification certification) {
		try {
			LOGGER.info("editing certification!");
			entityManager.merge(certification);
			LOGGER.info("certification edit!");
			return true;

		} catch (RuntimeException e) {

			LOGGER.error("error editing certification!Message: " + e.getMessage(), e);
			return false;
		}
	}

	@Transactional
	public boolean delete(int certificationId) {
		try {
			LOGGER.info("deleting certification!");
			entityManager.createQuery("update Certification set validity=:validity where id=:id")
					.setParameter("validity", false).setParameter("id", certificationId).executeUpdate();
			LOGGER.info("certification delete!");
			return true;

		} catch (Exception e) {
			LOGGER.error("error deleting certification!Message: " + e.getMessage(), e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Certification> getCertificationByManager(int managerId) {

		List<Certification> certification = new ArrayList<Certification>();
		try {
			LOGGER.info("Retrieving  certifications!");
			Query query = entityManager.createQuery("Select certification  from Certification certification,User user"
					+ " Where certification.employee.id=user.id And certification.validity=:validity"
					+ " And user.validity=:validity And user.managedBy=:managedBy");
			query.setParameter("validity", true).setParameter("managedBy", managerId);
			certification = query.getResultList();
			LOGGER.info("certifications Retrieved!");
			return certification;
		} catch (RuntimeException e) {
			LOGGER.error("error Retrieving certification!Message: " + e.getMessage(), e);
			return certification;
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Certification> getEmployeeCertifications(int employeeId) {
		List<Certification> certification = new ArrayList<Certification>();

		try {
			LOGGER.info("Retrieving  certifications of employee!");
			Query query = entityManager.createQuery("Select certification  from Certification certification"
					+ " Where certification.employee.id=:employeeId And certification.validity=:validity");
			query.setParameter("validity", true).setParameter("employeeId", employeeId);
			certification = query.getResultList();
			LOGGER.info("certifications of employee Retrieved!");
			return certification;
		} catch (RuntimeException e) {
			LOGGER.error("error Retrievingof employee certification!Message: " + e.getMessage(), e);
			return certification;
		}
	}

}
