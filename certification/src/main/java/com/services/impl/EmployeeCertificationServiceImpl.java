package com.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.converter.EmployeeCertificationConverter;
import com.converter.StatusConverter;
import com.dao.CertificationDao;
import com.dao.StatusDao;
import com.dto.AddEmployeeCertificationDto;
import com.dto.EmployeeCertificationDto;
import com.dto.StatusDto;
import com.dto.UserDto;
import com.services.EmployeeCertificationService;

@Service("employeeCertificationService")
public class EmployeeCertificationServiceImpl implements EmployeeCertificationService {

	@Autowired
	private CertificationDao certificationDao;
	@Autowired
	private StatusDao statusDao;

	@Transactional
	public boolean add(AddEmployeeCertificationDto addCertificationDto) {
		return certificationDao.add(EmployeeCertificationConverter.toAddCertification(addCertificationDto));
	}

	@Transactional
	public boolean addListCertification(List<UserDto> employeeSelected,
			AddEmployeeCertificationDto addCertificationDto) {
		boolean control = false;
		for (UserDto user : employeeSelected) {
			addCertificationDto.setEmployeeId(user.getId());
			control = certificationDao.add(EmployeeCertificationConverter.toAddCertification(addCertificationDto));
		}

		return control;
	}

	@Transactional
	public boolean edit(EmployeeCertificationDto certificationDto) {
		return certificationDao.edit(EmployeeCertificationConverter.toCertification(certificationDto));
	}

	@Transactional
	public boolean delete(int certificationDtoId) {
		return certificationDao.delete(certificationDtoId);
	}

	@Transactional
	public boolean deleteList(List<EmployeeCertificationDto> certifiactions) {
		boolean control = false;
		for (EmployeeCertificationDto certificationDto : certifiactions) {
			control = certificationDao.delete(certificationDto.getId());
		}

		return control;
	}

	@Transactional(readOnly = true)
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

	@Transactional(readOnly = true)
	public boolean existCertification(int idCertificate, int idUser) {
		return getEmployeeCertifications(idUser).stream()
				.anyMatch(certification -> certification.getCertificateId() == idCertificate);
	}

	@Transactional(readOnly = true)
	public List<EmployeeCertificationDto> getManagerCertifications(String description, String status, String employee,
			int idManager) {
		return EmployeeCertificationConverter.toCertificationListDto(
				certificationDao.getManagerCertifications(description, status, employee, idManager));
	}

	@Transactional(readOnly = true)
	public List<EmployeeCertificationDto> getEmployeeCertifications(String status, String description, int idEmployee) {
		return EmployeeCertificationConverter
				.toCertificationListDto(certificationDao.getEmployeeCertifications(status, description, idEmployee));
	}

	@Transactional(readOnly = true)
	public List<EmployeeCertificationDto> getEmployeeCertifications(int idEmployee) {
		return EmployeeCertificationConverter
				.toCertificationListDto(certificationDao.getEmployeeCertifications(idEmployee));
	}

	@Transactional(readOnly = true)
	public List<StatusDto> getCertificationStatus(String currentStatus) {
		return StatusConverter.toStatusListDto(statusDao.getOtherStatus(currentStatus));
	}

}
