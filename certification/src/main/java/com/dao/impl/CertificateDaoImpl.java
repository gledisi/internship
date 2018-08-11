package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.dao.CertificateDao;
import com.entity.Certificate;

@Repository(value = "certificateDao")
@Scope("singleton")
public class CertificateDaoImpl implements CertificateDao {

	private static final Logger LOGGER = LogManager.getLogger(CertificateDaoImpl.class.getName());

	@PersistenceContext
	EntityManager entityManager;

	public boolean add(Certificate certificate) {
		try {
			LOGGER.info("adding certificate!");
			entityManager.persist(certificate);
			LOGGER.info("certificate added!");
			return true;

		} catch (RuntimeException e) {

			LOGGER.error("error adding certificate!Message: " + e.getMessage(), e);
			return false;
		}
	}

	public boolean edit(Certificate certificate) {
		try {
			LOGGER.info("editing certificate!");
			entityManager.merge(certificate);
			LOGGER.info("Certificate edit!");
			return true;

		} catch (RuntimeException e) {

			LOGGER.error("error editing certificate!Message: " + e.getMessage(), e);
			return false;
		}
	}

	public boolean delete(int certificateId) {
		try {
			LOGGER.info("deleting certificate!");
			Query query = entityManager.createQuery("update Certificate set validity=:validity where id=:id");
			query.setParameter("validity", false);
			query.setParameter("id", certificateId);
			query.executeUpdate();
			LOGGER.info("Certificate delete!");

			return true;

		} catch (Exception e) {
			LOGGER.error("error deleting certificate!Message: " + e.getMessage(), e);
			return false;
		}
	}

	// @SuppressWarnings("unchecked")
	// @Transactional
	// public List<Certificate> getManagerCertificates(int idManager) {
	//
	// List<Certificate> certificates = new ArrayList<Certificate>();
	//
	// try {
	// LOGGER.info("getting certificates of manager!");
	// Query query = entityManager.createQuery(
	// "Select certificate From Certificate certificate Where
	// certificate.validity=:validity And certificate.manager.id=:idManager");
	// query.setParameter("validity", true);
	// query.setParameter("idManager", idManager);
	// certificates = query.getResultList();
	// LOGGER.info("Certificates of manager retrieved!");
	// return certificates;
	//
	// } catch (RuntimeException e) {
	//
	// LOGGER.error("error getting manager certificates !Message: " +
	// e.getMessage(), e);
	// return certificates;
	// }
	//
	// }

	public List<Certificate> getCertificates(String description, int idManager) {

		List<Certificate> certificates = new ArrayList<Certificate>();

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("Select certificate From Certificate certificate");
		queryBuilder.append(" Where certificate.validity=:validity");
		queryBuilder.append(" And certificate.manager.id=:idManager");

		if (description != null && !description.trim().isEmpty()) {
			queryBuilder.append(" And certificate.description LIKE :description");
			queryBuilder.append(" Or certificate.type LIKE :description");
			queryBuilder.append(" And certificate.name LIKE :description");
		}

		try {
			LOGGER.info("getting certificates of manager! id[" + idManager + "] me description '" + description + "'");
			TypedQuery<Certificate> query = entityManager.createQuery(queryBuilder.toString(), Certificate.class);
			query.setParameter("validity", true);
			query.setParameter("idManager", idManager);
			if (description != null && !description.trim().isEmpty()) {
				query.setParameter("description", description);
			}
			certificates = query.getResultList();
			LOGGER.info("Certificates of manager retrieved!");
			return certificates;

		} catch (RuntimeException e) {

			LOGGER.error("error getting manager certificates !Message: " + e.getMessage(), e);
			return certificates;
		}

	}

	public Certificate getCertificateByName(String name) {
		Certificate certificate = null;
		try {
			LOGGER.info("getting certificate by name!");
			certificate = (Certificate) entityManager
					.createQuery("Select certificate from Certificate certificate Where certificate.name=:name")
					.setParameter("name", name).getSingleResult();
			LOGGER.info("Certificate retrieved!");
			return certificate;

		} catch (Exception e) {

			LOGGER.error("error getting certificate by name !Message: " + e.getMessage(), e);
			return certificate;
		}
	}

	public Certificate getCertificateById(int idCertificate) {
		Certificate certificate = new Certificate();

		try {

			LOGGER.info("getting certificate by name!");
			certificate = entityManager.find(Certificate.class, idCertificate);
			LOGGER.info("Certificate retrieved!");

		} catch (Exception e) {
			LOGGER.error("error getting certificate by name !Message: " + e.getMessage(), e);
		}

		return certificate;
	}

}
