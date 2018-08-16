package com.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.dto.AddEmployeeCertificationDto;
import com.dto.EmployeeCertificationDto;
import com.dto.EmployeeCertificationSearchDto;
import com.dto.StatusDto;
import com.dto.UserDto;
import com.services.EmployeeCertificationService;
import com.utility.Messages;

@ManagedBean
@ViewScoped
public class EmployeesCertificationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean multipleAdd;

	private EmployeeCertificationSearchDto searchDto;
	private AddEmployeeCertificationDto addEmployeeCertification;
	private EmployeeCertificationDto employeeCertificationDto;

	private List<StatusDto> statusDto;
	private List<EmployeeCertificationDto> employeesCertification;
	private List<EmployeeCertificationDto> employeesCertificationSelected;

	@ManagedProperty(value = "#{employeeCertificationService}")
	private EmployeeCertificationService employeeCertificationService;

	@ManagedProperty(value = "#{employeeManagementBean}")
	private EmployeeManagementBean employeeManagementBean;
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	@PostConstruct
	public void init() {
		refreshBean();
	}

	public void refreshBean() {
		this.multipleAdd = false;
		this.addEmployeeCertification = new AddEmployeeCertificationDto();
		this.employeeCertificationDto = new EmployeeCertificationDto();
		this.searchDto = new EmployeeCertificationSearchDto();
		this.employeesCertification = getAllCertifications();
		this.statusDto = getCertificationStatus();
	}

	public List<StatusDto> getCertificationStatus() {
		return employeeCertificationService.getCertificationStatus(employeeCertificationDto.getStatus());
	}

	private List<EmployeeCertificationDto> getAllCertifications() {
		String role = userBean.getUser().getRole();
		if (role.equals("manager")) {
			return employeeCertificationService.getManagerCertifications(searchDto.getDescription(),
					searchDto.getStatus(), searchDto.getEmployeeName(), userBean.getUser().getId());
		} else if (role.equals("employee")) {
			return employeeCertificationService.getEmployeeCertifications(searchDto.getStatus(),
					searchDto.getDescription(), userBean.getUser().getId());
		}
		return null;
	}

	public String search() {
		this.employeesCertification = getAllCertifications();
		return null;
	}

	public String addCertification() {
		if (!employeeCertificationService.existCertification(addEmployeeCertification.getCertificateId(),
				addEmployeeCertification.getEmployeeId())) {
			if (employeeCertificationService.add(addEmployeeCertification)) {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATION_ADDED"), "info");
				refreshBean();
			} else {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATION_NOT_ADDED"), "error");
			}
		} else {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATION_EXIST"), "warn");
			refreshBean();
		}
		return null;
	}

	public String addCertificationForSelectedEmployee() {
		List<UserDto> employeesSelected = employeeManagementBean.getSelectedEmployees();
		int size = employeesSelected.size();
		if (size != 0) {
			if (employeeCertificationService.existCertificationOnAddedList(employeesSelected,
					addEmployeeCertification.getCertificateId())) {
				if (employeeCertificationService.addListCertification(employeesSelected, addEmployeeCertification)) {
					if (size == 1) {
						Messages.addMessage(Messages.bundle.getString("CERTIFICATION_ADDED"), "info");
					} else {
						Messages.addMessage(Messages.bundle.getString("CERTIFICATIONS_ADDED"), "info");
					}
					refreshBean();
				} else {
					Messages.addMessage(Messages.bundle.getString("CERTIFICATION_NOT_ADDED"), "error");
				}
			} else {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATIONS_EXIST"), "warn");
			}
		} else {
			Messages.addMessage(Messages.bundle.getString("EMPLOYEES_NOT_SELECTED"), "warn");
		}

		return null;
	}

	public String updateCertification() {

		System.out.println(employeeCertificationDto.getId());

		if (employeeCertificationService.edit(employeeCertificationDto)) {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATION_EDIT"), "info");
			refreshBean();
		} else {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATION_NOT_EDIT"), "error");
		}

		return null;
	}

	public String deleteCertification(int certificationId) {

		if (employeeCertificationService.delete(certificationId)) {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATION_DELETED"), "info");
			refreshBean();
		} else {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATION_NOT_DELETED"), "error");
		}
		return null;
	}

	public String deleteListCertifications() {

		if (employeeCertificationService.deleteList(getEmployeesCertificationSelected())) {
			if (getEmployeesCertificationSelected().size() == 1) {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATION_DELETED"), "info");
			} else {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATIONS_DELETED"), "info");
			}
			refreshBean();
		} else {

			if (getEmployeesCertificationSelected().size() == 0) {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATION_NOT_SELECTED"), "warn");
			} else {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATION_NOT_DELETED"), "error");
			}

		}
		return null;
	}

	// GETTTERS AND SETTERS

	public AddEmployeeCertificationDto getAddEmployeeCertification() {
		return addEmployeeCertification;
	}

	public EmployeeCertificationService getEmployeeCertificationService() {
		return employeeCertificationService;
	}

	public void setEmployeeCertificationService(EmployeeCertificationService employeeCertificationService) {
		this.employeeCertificationService = employeeCertificationService;
	}

	public void setAddEmployeeCertification(AddEmployeeCertificationDto addEmployeeCertification) {
		this.addEmployeeCertification = addEmployeeCertification;
	}

	public EmployeeCertificationDto getEmployeeCertificationDto() {
		return employeeCertificationDto;
	}

	public void setEmployeeCertificationDto(EmployeeCertificationDto employeeCertificationDto) {
		this.employeeCertificationDto = employeeCertificationDto;
	}

	public List<EmployeeCertificationDto> getEmployeesCertification() {
		return employeesCertification;
	}

	public void setEmployeesCertification(List<EmployeeCertificationDto> employeesCertification) {
		this.employeesCertification = employeesCertification;
	}

	public List<EmployeeCertificationDto> getEmployeesCertificationSelected() {
		return employeesCertificationSelected;
	}

	public void setEmployeesCertificationSelected(List<EmployeeCertificationDto> employeesCertificationSelected) {
		this.employeesCertificationSelected = employeesCertificationSelected;
	}

	public EmployeeManagementBean getEmployeeManagementBean() {
		return employeeManagementBean;
	}

	public void setEmployeeManagementBean(EmployeeManagementBean employeeManagementBean) {
		this.employeeManagementBean = employeeManagementBean;
	}

	public EmployeeCertificationSearchDto getSearchDto() {
		return searchDto;
	}

	public void setSearchDto(EmployeeCertificationSearchDto searchDto) {
		this.searchDto = searchDto;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public boolean isMultipleAdd() {
		return multipleAdd;
	}

	public void setMultipleAdd(boolean multipleAdd) {
		this.multipleAdd = multipleAdd;
	}

	public List<StatusDto> getStatusDto() {
		return statusDto;
	}

	public void setStatusDto(List<StatusDto> statusDto) {
		this.statusDto = statusDto;
	}

}
