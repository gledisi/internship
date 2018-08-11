package com.converter;

import java.util.ArrayList;
import java.util.List;

import com.dto.CertificateDto;
import com.entity.Certificate;
import com.entity.User;

public class CertificateConverter {

	public static Certificate toCertificate(CertificateDto certificateDto) {

		Certificate certificate = new Certificate();
		certificate.setName(certificateDto.getName());
		certificate.setType(certificateDto.getType());
		certificate.setDescription(certificateDto.getDescription());
		certificate.setValidity(true);

		User manager = new User();
		manager.setId(certificateDto.getIdManager());
		certificate.setManager(manager);

		return certificate;
	}

	public static Certificate toEditCertificate(CertificateDto certificateDto) {

		Certificate certificate = new Certificate();
		certificate.setId(certificateDto.getId());
		certificate.setName(certificateDto.getName());
		certificate.setType(certificateDto.getType());
		certificate.setDescription(certificateDto.getDescription());
		certificate.setValidity(true);

		User manager = new User();
		manager.setId(certificateDto.getIdManager());
		certificate.setManager(manager);

		return certificate;
	}

	public static CertificateDto toCertificateDto(Certificate certificate) {

		CertificateDto certificateDto = new CertificateDto();
		certificateDto.setId(certificate.getId());
		certificateDto.setDescription(certificate.getDescription());
		certificateDto.setName(certificate.getName());
		certificateDto.setType(certificate.getType());
		certificateDto.setIdManager(certificate.getManager().getId());

		return certificateDto;
	}

	public static List<CertificateDto> toCertificateListDto(List<Certificate> certificates) {
		List<CertificateDto> certificatesDto = new ArrayList<>();
		for (Certificate certificate : certificates) {
			certificatesDto.add(toCertificateDto(certificate));
		}
		return certificatesDto;
	}

}
