package com.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.converter.CertificationConverter;
import com.dao.CertificationDao;
import com.dto.AddCertificationDto;
import com.dto.CertificationDto;
import com.dto.UserDto;
import com.services.CertificationService;

@Service("certificationService")
public class CertificationServiceImpl implements CertificationService {

	@Autowired
	private CertificationDao certificationDao;

	@Transactional
	public boolean add(AddCertificationDto addCertificationDto) {
		return certificationDao.add(CertificationConverter.toAddCertification(addCertificationDto));
	}

	@Transactional
	public boolean addListCertification(List<UserDto> employeeSelected, AddCertificationDto addCertificationDto) {
		boolean control = false;
		for (UserDto user : employeeSelected) {
			addCertificationDto.setEmployeeId(user.getId());
			control = certificationDao.add(CertificationConverter.toAddCertification(addCertificationDto));
		}

		return control;
	}

	@Transactional
	public boolean edit(CertificationDto certificationDto) {
		return certificationDao.edit(CertificationConverter.toCertification(certificationDto));
	}

	@Transactional
	public boolean delete(int certificationDtoId) {
		return certificationDao.delete(certificationDtoId);
	}

	@Transactional
	public boolean deleteList(List<CertificationDto> certifiactions) {
		boolean control = false;
		for (CertificationDto certificationDto : certifiactions) {
			control = certificationDao.delete(certificationDto.getId());
		}

		return control;
	}

	@Transactional
	public boolean existCertificationOnAddedList(List<UserDto> users, int idCertificate) {
		boolean exist = false;
		for (UserDto userDto : users) {
			if (existCertification(idCertificate, userDto.getId())) {
				exist = true;
				break;
			}
		}
		return exist;
	}

	@Transactional
	public boolean existCertification(int idCertificate, int idUser) {
		return getEmployeeCertifications(idUser).stream()
				.anyMatch(certification -> certification.getCertificateId() == idCertificate);
	}

	@Transactional
	public List<CertificationDto> getManagerCertifications(String description, String status, String employee,
			int idManager) {
		return CertificationConverter.toCertificationListDto(
				certificationDao.getManagerCertifications(description, status, employee, idManager));
	}

	@Transactional
	public List<CertificationDto> getEmployeeCertifications(String status, String description, int idEmployee) {
		return CertificationConverter
				.toCertificationListDto(certificationDao.getEmployeeCertifications(status, description, idEmployee));
	}

	@Transactional
	public List<CertificationDto> getEmployeeCertifications(int idEmployee) {
		return CertificationConverter.toCertificationListDto(certificationDao.getEmployeeCertifications(idEmployee));
	}

}
