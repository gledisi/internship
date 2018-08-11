package com.dao;

import java.util.List;

import com.entity.Certification;

public interface CertificationDao {

	public boolean add(Certification certification);

	public boolean edit(Certification certification);

	public boolean delete(int certificationId);

	public List<Certification> getManagerCertifications(String description, String status, String employee,
			int idManager);

	public List<Certification> getEmployeeCertifications(String status, String description, int idEmployee);

	public List<Certification> getEmployeeCertifications(int employeeId);

}
