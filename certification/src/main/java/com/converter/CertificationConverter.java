package com.converter;

import com.dto.AddCertificationDto;
import com.dto.CertificationDto;
import com.entity.Certificate;
import com.entity.Certification;
import com.entity.Status;
import com.entity.User;

public class CertificationConverter {

	public static CertificationDto toCertificationDto(Certification certification) {

		CertificationDto certificationDto = new CertificationDto();

		certificationDto.setId(certification.getId());
		certificationDto.setEmployeeId(certification.getEmployee().getId());
		certificationDto.setFirstname(certification.getEmployee().getFirstname());
		certificationDto.setLastname(certification.getEmployee().getLastname());
		certificationDto.setCertificateId(certification.getCertificate().getId());
		certificationDto.setCertificatename(certification.getCertificate().getName());
		certificationDto.setCertificatetype(certification.getCertificate().getType());
		certificationDto.setStatusId(certification.getStatus().getId());
		certificationDto.setStatus(certification.getStatus().getStatus());
		certificationDto.setPoints(certification.getPoints());
		certificationDto.setDate(certification.getDate());

		return certificationDto;

	}

	public static Certification toCertification(CertificationDto certificationDto) {

		Certification certification = new Certification();

		certification.setId(certificationDto.getId());
		certification.setDate(certificationDto.getDate());
		certification.setPoints(certificationDto.getPoints());
		certification.setValidity(true);

		Status status = new Status();
		status.setId(certificationDto.getStatusId());
		certification.setStatus(status);

		User employee = new User();
		employee.setId(13);
		certification.setEmployee(employee);

		Certificate certificate = new Certificate();
		certificate.setId(42);
		certification.setCertificate(certificate);

		return certification;

	}

	public static Certification toAddCertification(AddCertificationDto addCertificationDto) {

		Certification certification = new Certification();

		
		certification.setDate(addCertificationDto.getDate());
		certification.setValidity(true);
		
		Status status = new Status();
		status.setId(1);
		certification.setStatus(status);
		
		User employee = new User();
		employee.setId(addCertificationDto.getEmployeeId());
		certification.setEmployee(employee);

		Certificate certificate = new Certificate();
		certificate.setId(addCertificationDto.getCertificateId());
		certification.setCertificate(certificate);		

		return certification;
	}
}
