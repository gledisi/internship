package com.converter;

import java.util.ArrayList;
import java.util.List;

import com.dto.AddEmployeeCertificationDto;
import com.dto.EmployeeCertificationDto;
import com.entity.Certificate;
import com.entity.Certification;
import com.entity.Status;
import com.entity.User;

public class EmployeeCertificationConverter {

	public static EmployeeCertificationDto toCertificationDto(Certification certification) {

		EmployeeCertificationDto certificationDto = new EmployeeCertificationDto();

		certificationDto.setId(certification.getId());
		certificationDto.setEmployeeId(certification.getEmployee().getId());
		certificationDto.setFirstname(certification.getEmployee().getFirstname());
		certificationDto.setLastname(certification.getEmployee().getLastname());
		certificationDto.setCertificateId(certification.getCertificate().getId());
		certificationDto.setCertificatename(certification.getCertificate().getName());
		certificationDto.setCertificatetype(certification.getCertificate().getType());
		certificationDto.setCertificateDescription(certification.getCertificate().getDescription());
		certificationDto.setStatusId(certification.getStatus().getId());
		certificationDto.setStatus(certification.getStatus().getStatus());
		certificationDto.setPoints(certification.getPoints());
		certificationDto.setDate(certification.getDate());

		return certificationDto;

	}

	public static Certification toCertification(EmployeeCertificationDto certificationDto) {

		Certification certification = new Certification();

		certification.setId(certificationDto.getId());
		certification.setDate(certificationDto.getDate());
		certification.setPoints(certificationDto.getPoints());
		certification.setValidity(true);

		Status status = new Status();
		status.setId(certificationDto.getStatusId());
		certification.setStatus(status);

		User employee = new User();
		employee.setId(certificationDto.getEmployeeId());
		certification.setEmployee(employee);

		Certificate certificate = new Certificate();
		certificate.setId(certificationDto.getCertificateId());
		certification.setCertificate(certificate);

		return certification;

	}

	public static Certification toAddCertification(AddEmployeeCertificationDto addCertificationDto) {

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

	public static List<EmployeeCertificationDto> toCertificationListDto(List<Certification> certifications) {
		List<EmployeeCertificationDto> certificationDto = new ArrayList<>();
		for (Certification certification : certifications) {
			certificationDto.add(toCertificationDto(certification));
		}
		return certificationDto;
	}
}
