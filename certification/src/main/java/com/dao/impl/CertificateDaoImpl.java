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
			LOGGER.debug("Manager {} adding certificate {}!" + certificate.getManager().getFirstname(),
					certificate.getName());
			entityManager.persist(certificate);
			LOGGER.debug("certificate added successfully!");
			return true;

		} catch (RuntimeException e) {

			LOGGER.error("error adding certificate!Message: " + e.getMessage(), e);
			return false;
		}
	}

	public boolean edit(Certificate certificate) {
		try {
			LOGGER.debug("Manager {} editing certificate {}!" + certificate.getManager().getFirstname(),
					certificate.getName());
			entityManager.merge(certificate);
			LOGGER.debug("Certificate edited successfully!");
			return true;

		} catch (RuntimeException e) {

			LOGGER.error("error editing certificate!Message: " + e.getMessage(), e);
			return false;
		}
	}

	public boolean delete(int certificateId) {
		try {
			Certificate certificate = entityManager.find(Certificate.class, certificateId);
			LOGGER.debug("Manager {} deleting certificate {}!" + certificate.getManager().getFirstname()
					+ certificate.getName());
			certificate.setValidity(false);
			entityManager.merge(certificate);
			LOGGER.debug("Certificate deleted!");

			return true;

		} catch (Exception e) {
			LOGGER.error("error deleting certificate!Message: " + e.getMessage(), e);
			return false;
		}
	}

	public List<Certificate> getCertificates(String description, int idManager) {

		List<Certificate> certificates = new ArrayList<Certificate>();

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("Select certificate From Certificate certificate");
		queryBuilder.append(" Where certificate.validity=:validity");
		queryBuilder.append(" And certificate.manager.id=:idManager");

		if (description != null && !description.trim().isEmpty()) {
			queryBuilder.append(" And certificate.description LIKE :description");
		}

		try {
			LOGGER.debug(
					"getting certificates of manager! id[" + idManager + "] with description '" + description + "'");
			TypedQuery<Certificate> query = entityManager.createQuery(queryBuilder.toString(), Certificate.class);
			query.setParameter("validity", true);
			query.setParameter("idManager", idManager);
			if (description != null && !description.trim().isEmpty()) {
				query.setParameter("description", "%" + description + "%");
			}
			certificates = query.getResultList();
			LOGGER.debug("Certificates of manager retrieved!" + certificates);

		} catch (RuntimeException e) {

			LOGGER.error("error getting manager certificates !Message: " + e.getMessage(), e);
		}
		return certificates;
	}

	public Certificate getCertificateByName(String name) {
		Certificate certificate = null;
		try {
			LOGGER.debug("getting certificate by name!");
			certificate = (Certificate) entityManager
					.createQuery("Select certificate from Certificate certificate" + " Where certificate.name=:name"
							+ " And certificate.validity=:validity")
					.setParameter("name", name).setParameter("validity", true).getSingleResult();
			LOGGER.debug("Certificate retrieved!");
			return certificate;

		} catch (Exception e) {

			LOGGER.error("error getting certificate by name !Message: " + e.getMessage(), e);
			return certificate;
		}
	}

	public Certificate getCertificateById(int idCertificate) {
		Certificate certificate = new Certificate();

		try {

			LOGGER.debug("getting certificate by name!");
			certificate = entityManager.find(Certificate.class, idCertificate);
			LOGGER.debug("Certificate retrieved!");

		} catch (Exception e) {
			LOGGER.error("error getting certificate by name !Message: " + e.getMessage(), e);
		}

		return certificate;
	}

}
