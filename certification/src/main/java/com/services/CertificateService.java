package com.services;

import java.util.List;

import com.dto.CertificateDto;

public interface CertificateService {

	public boolean add(CertificateDto certificateDto);

	public boolean edit(CertificateDto certificateDto);

	public boolean delete(int certificateDtoId);

	public boolean deleteList(List<CertificateDto> certificateDto);

	public CertificateDto getCertificateByName(String name);

	public CertificateDto getCertificateById(int idCertificate);

	public List<CertificateDto> getCertificates(String description, int idManager);

	public boolean existCertificate(String name);
}
