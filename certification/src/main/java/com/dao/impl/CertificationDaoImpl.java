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

import com.dao.CertificationDao;
import com.entity.Certification;

@Repository(value = "certificationDao")
@Scope("singleton")
public class CertificationDaoImpl implements CertificationDao {

	private static final Logger LOGGER = LogManager.getLogger(CertificationDaoImpl.class.getName());

	@PersistenceContext
	EntityManager entityManager;

	public boolean add(Certification certification) {

		try {
			LOGGER.debug(
					"Manager {} adding certification {} for employee {}!" + certification.getCertificate().getName(),
					certification.getEmployee().getFirstname());
			entityManager.persist(certification);
			LOGGER.debug("certification added successfully!");
			return true;

		} catch (RuntimeException e) {

			LOGGER.error("error adding certification!Message: " + e.getMessage(), e);
			return false;
		}
	}

	public boolean edit(Certification certification) {
		try {
			LOGGER.debug(
					"Manager {} editing certification {} for employee {}!" + certification.getCertificate().getName(),
					certification.getEmployee().getFirstname());
			entityManager.merge(certification);
			LOGGER.debug("certification edit successfully!");
			return true;

		} catch (RuntimeException e) {

			LOGGER.error("error editing certification!Message: " + e.getMessage(), e);
			return false;
		}
	}

	public boolean delete(int certificationId) {
		try {
			Certification certification = entityManager.find(Certification.class, certificationId);
			LOGGER.debug(
					"Manager {} deleting certification {} of employee {}!"
							+ certification.getCertificate().getManager().getFirstname(),
					certification.getCertificate().getName(), certification.getEmployee().getFirstname());
			certification.setValidity(false);
			entityManager.merge(certification);
			LOGGER.debug("certification deleted!");
			return true;

		} catch (Exception e) {
			LOGGER.error("error deleting certification!Message: " + e.getMessage(), e);
			return false;
		}
	}

	public List<Certification> getManagerCertifications(String description, String status, String employee,
			int idManager) {

		List<Certification> certification = new ArrayList<>();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Select certification  from Certification certification");
		stringBuilder.append(" Where certification.validity=:validity");
		stringBuilder.append(" And certification.employee.managedBy=:idManager");

		if (status != null && !status.trim().isEmpty()) {
			stringBuilder.append(" And certification.status.status=:status");
		}
		if (description != null && !description.trim().isEmpty()) {
			stringBuilder.append(" And certification.certificate.description LIKE :description");
		}
		if (employee != null && !employee.trim().isEmpty()) {
			stringBuilder.append(" And certification.employee.firstname  LIKE :employee");
		}
		try {
			LOGGER.debug("Retrieving  certifications!");
			TypedQuery<Certification> query = entityManager.createQuery(stringBuilder.toString(), Certification.class);
			query.setParameter("validity", true);
			query.setParameter("idManager", idManager);

			if (status != null && !status.trim().isEmpty()) {
				query.setParameter("status", status);
			}
			if (description != null && !description.trim().isEmpty()) {
				query.setParameter("description", "%" + description + "%");
			}
			if (employee != null && !employee.trim().isEmpty()) {
				query.setParameter("employee", "%" + employee + "%");
			}
			certification = query.getResultList();

			LOGGER.debug("certifications Retrieved successfully!");
		} catch (RuntimeException e) {
			LOGGER.error("error Retrieving certification!Message: " + e.getMessage(), e);
		}

		return certification;
	}

	public List<Certification> getEmployeeCertifications(String status, String description, int idEmployee) {
		List<Certification> certification = new ArrayList<Certification>();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Select certification  from Certification certification");
		stringBuilder.append(" Where certification.validity=:validity");
		stringBuilder.append(" And certification.employee.id=:idEmployee");

		if (status != null && !status.trim().isEmpty()) {
			stringBuilder.append(" And certification.status.status=:status");
		}
		if (description != null && !description.trim().isEmpty()) {
			stringBuilder.append(" And certification.certificate.description LIKE :description");
		}

		try {
			LOGGER.debug("Retrieving  certifications!");
			TypedQuery<Certification> query = entityManager.createQuery(stringBuilder.toString(), Certification.class);
			query.setParameter("validity", true);
			query.setParameter("idEmployee", idEmployee);

			if (status != null && !status.trim().isEmpty()) {
				query.setParameter("status", status);
			}
			if (description != null && !description.trim().isEmpty()) {
				query.setParameter("description", "%" + description + "%");
			}

			certification = query.getResultList();
			LOGGER.debug("certifications Retrieved successfully!");
		} catch (RuntimeException e) {
			LOGGER.error("error Retrieving certification!Message: " + e.getMessage(), e);
		}

		return certification;
	}

	@SuppressWarnings("unchecked")
	public List<Certification> getEmployeeCertifications(int employeeId) {
		List<Certification> certification = new ArrayList<Certification>();

		try {
			LOGGER.debug("Retrieving  certifications of employee!");
			Query query = entityManager.createQuery("Select certification  from Certification certification"
					+ " Where certification.employee.id=:employeeId And certification.validity=:validity");
			query.setParameter("validity", true).setParameter("employeeId", employeeId);
			certification = query.getResultList();
			LOGGER.debug("certifications of employee Retrieved successfully!");
			return certification;
		} catch (RuntimeException e) {
			LOGGER.error("error Retrievingof employee certification!Message: " + e.getMessage(), e);
			return certification;
		}
	}

}
