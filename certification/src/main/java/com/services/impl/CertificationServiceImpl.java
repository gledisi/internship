package com.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.converter.CertificationConverter;
import com.dao.CertificationDao;
import com.dto.AddCertificationDto;
import com.dto.CertificationDto;
import com.dto.UserDto;
import com.entity.Certification;
import com.services.CertificationService;

@Service("certificationService")
public class CertificationServiceImpl implements CertificationService {

	@Autowired
	private CertificationDao certificationDao;

	public boolean add(AddCertificationDto addCertificationDto) {
		return certificationDao.add(CertificationConverter.toAddCertification(addCertificationDto));
	}

	public boolean addListCertification(List<UserDto> employeeSelected, AddCertificationDto addCertificationDto) {
		boolean control = false;
		for (UserDto user : employeeSelected) {
			addCertificationDto.setEmployeeId(user.getId());
			control = certificationDao.add(CertificationConverter.toAddCertification(addCertificationDto));
		}

		return control;
	}

	public boolean edit(CertificationDto certificationDto) {
		return certificationDao.edit(CertificationConverter.toCertification(certificationDto));
	}

	public boolean delete(int certificationDtoId) {
		return certificationDao.delete(certificationDtoId);
	}

	public boolean deleteList(List<CertificationDto> certifiactions) {
		boolean control = false;
		for (CertificationDto certificationDto : certifiactions) {
			control = certificationDao.delete(certificationDto.getCertificateId());
		}

		return control;
	}

	public List<CertificationDto> getCertificationOfManager(int managerId) {

		List<CertificationDto> certificationsDto = new ArrayList<CertificationDto>();
		List<Certification> certifications = certificationDao.getCertificationByManager(managerId);
		for (Certification certification : certifications) {
			certificationsDto.add(CertificationConverter.toCertificationDto(certification));
		}
		return certificationsDto;
	}

	public List<CertificationDto> getEmployeeCertifications(int employeeId) {
		List<CertificationDto> certificationsDto = new ArrayList<CertificationDto>();
		List<Certification> certifications = certificationDao.getEmployeeCertifications(employeeId);
		for (Certification certification : certifications) {
			certificationsDto.add(CertificationConverter.toCertificationDto(certification));
		}
		return certificationsDto;
	}


}
