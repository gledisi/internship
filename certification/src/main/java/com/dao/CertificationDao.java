package com.dao;

import java.util.List;

import com.entity.Certification;


public interface CertificationDao {

	public boolean add(Certification certification);

	public boolean edit(Certification certification);

	public boolean delete(int certificationId);
	
	public List<Certification> getEmployeeCertifications(int uerId);
	
	public List<Certification> getCertificationByManager(int managerId);
	
}
