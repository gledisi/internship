package com.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.converter.CertificateConverter;
import com.dao.CertificateDao;
import com.dto.CertificateDto;
import com.entity.Certificate;
import com.services.CertificateService;

@Service("certificateService")
public class CertificateServiceImpl implements CertificateService {

	@Autowired
	private CertificateDao certificateDao;

	@Transactional
	public boolean add(CertificateDto certificateDto) {
		return certificateDao.add(CertificateConverter.toCertificate(certificateDto));
	}

	@Transactional
	public boolean edit(CertificateDto certificateDto) {
		return certificateDao.edit(CertificateConverter.toEditCertificate(certificateDto));
	}

	@Transactional
	public boolean delete(int certificateDtoId) {
		return certificateDao.delete(certificateDtoId);
	}

	@Transactional
	public boolean deleteList(List<CertificateDto> certificates) {
		boolean control = false;
		for (CertificateDto certificateDto : certificates) {
			control = certificateDao.delete(certificateDto.getId());
		}

		return control;
	}

	@Transactional
	public List<CertificateDto> getCertificates(String description, int idManager) {
		return CertificateConverter.toCertificateListDto(certificateDao.getCertificates(description, idManager));
	}

	@Transactional
	public CertificateDto getCertificateById(int idCertificate) {
		return CertificateConverter.toCertificateDto(certificateDao.getCertificateById(idCertificate));
	}

	@Transactional
	public CertificateDto getCertificateByName(String name) {
		Certificate certificate = certificateDao.getCertificateByName(name);
		return certificate == null ? null : CertificateConverter.toCertificateDto(certificate);
	}

	@Transactional
	public boolean existCertificate(String name) {
		return getCertificateByName(name) != null;
	}
}
