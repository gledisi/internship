package com.dao;

import java.util.List;

import com.entity.Certificate;

public interface CertificateDao {

	public boolean add(Certificate certificate);

	public boolean edit(Certificate certificate);

	public boolean delete(int certificateId);

	public Certificate getCertificateByName(String name);

	public Certificate getCertificateById(int idCertificate);

	public List<Certificate> getCertificates(String description, int idManager);
}
