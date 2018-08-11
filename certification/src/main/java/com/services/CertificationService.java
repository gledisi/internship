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

	public List<CertificationDto> getCertificationOfManager(int managerId);

	public List<CertificationDto> getEmployeeCertifications(int employeeId);

	public boolean addListCertification(List<UserDto> employeesSelected, AddCertificationDto addCertification);

}
