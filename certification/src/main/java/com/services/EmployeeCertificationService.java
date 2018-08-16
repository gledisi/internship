package com.services;

import java.util.List;

import com.dto.AddEmployeeCertificationDto;
import com.dto.EmployeeCertificationDto;
import com.dto.StatusDto;
import com.dto.UserDto;

public interface EmployeeCertificationService {

	public boolean add(AddEmployeeCertificationDto addCertificationDto);

	public boolean edit(EmployeeCertificationDto certificationDto);

	public boolean delete(int certificationDtoId);

	public boolean deleteList(List<EmployeeCertificationDto> certifiactions);

	public boolean existCertificationOnAddedList(List<UserDto> users, int idCertificate);

	public boolean existCertification(int idCertificate, int idUser);

	public List<EmployeeCertificationDto> getManagerCertifications(String description, String status, String employee,
			int idManager);

	public List<EmployeeCertificationDto> getEmployeeCertifications(String status, String description, int idEmployee);

	public List<EmployeeCertificationDto> getEmployeeCertifications(int employeeId);

	public boolean addListCertification(List<UserDto> employeesSelected, AddEmployeeCertificationDto addCertification);

	public List<StatusDto> getCertificationStatus(String currentStatus);

}
