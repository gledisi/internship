package com.services;

import java.util.List;

import com.dto.AddCertificationDto;
import com.dto.CertificationDto;
import com.dto.UserDto;

public interface CertificationService {

	public boolean add(AddCertificationDto addCertificationDto);

	public boolean edit(CertificationDto certificationDto);

	public boolean delete(int certificationDtoId);

	public boolean deleteList(List<CertificationDto> certifiactions);

	public boolean existCertificationOnAddedList(List<UserDto> users, int idCertificate);

	public boolean existCertification(int idCertificate, int idUser);

	public List<CertificationDto> getManagerCertifications(String description, String status, String employee,
			int idManager);

	public List<CertificationDto> getEmployeeCertifications(String status, String description, int idEmployee);

	public List<CertificationDto> getEmployeeCertifications(int employeeId);

	public boolean addListCertification(List<UserDto> employeesSelected, AddCertificationDto addCertification);

}
