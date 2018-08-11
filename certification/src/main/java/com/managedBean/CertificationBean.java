package com.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.dto.AddCertificationDto;
import com.dto.CertificationDto;
import com.dto.UserDto;
import com.services.CertificationService;
import com.utility.Messages;

@ManagedBean
@ViewScoped
public class CertificationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean multipleAdd;

	private String status;
	private String description;
	private String employeeName;

	private AddCertificationDto addCertification;
	private CertificationDto certificationDto;

	private List<CertificationDto> certifications;
	private List<CertificationDto> certificationsSelected;

	@ManagedProperty(value = "#{certificationService}")
	private CertificationService certificationService;
	@ManagedProperty(value = "#{userCrudBean}")
	private UserCrudBean userCrudBean;
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	@PostConstruct
	public void init() {
		refreshBean();
	}

	public void refreshBean() {
		this.multipleAdd=false;
		this.addCertification = new AddCertificationDto();
		this.certificationDto = new CertificationDto();
		this.certifications = getAllCertifications();
	}

	private List<CertificationDto> getAllCertifications() {
		String role = userBean.getUser().getRole();
		if (role.equals("manager")) {
			return certificationService.getManagerCertifications(description, status, employeeName,
					userBean.getUser().getId());
		} else if (role.equals("employee")) {
			return certificationService.getEmployeeCertifications(status, description, userBean.getUser().getId());
		}
		return null;
	}

	public String search() {
		this.certifications = getAllCertifications();
		return null;
	}

	public String addCertification() {
		if (!certificationService.existCertification(addCertification.getCertificateId(),
				addCertification.getEmployeeId())) {
			if (certificationService.add(addCertification)) {
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
		List<UserDto> employeesSelected = userCrudBean.getSelectedEmployees();
		int size = employeesSelected.size();
		if (size != 0) {
			if (certificationService.existCertificationOnAddedList(employeesSelected,
					addCertification.getCertificateId())) {
				if (certificationService.addListCertification(employeesSelected, addCertification)) {
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

		System.out.println(certificationDto.getId());

		if (certificationService.edit(certificationDto)) {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATION_EDIT"), "info");
			refreshBean();
		} else {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATION_NOT_EDIT"), "error");
		}

		return null;
	}

	public String deleteCertification(int certificationId) {

		if (certificationService.delete(certificationId)) {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATION_DELETED"), "info");
			refreshBean();
		} else {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATION_NOT_DELETED"), "error");
		}
		return null;
	}

	public String deleteListCertifications() {

		if (certificationService.deleteList(getCertificationsSelected())) {
			if (getCertificationsSelected().size() == 1) {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATION_DELETED"), "info");
			} else {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATIONS_DELETED"), "info");
			}

		} else {

			if (getCertificationsSelected().size() == 0) {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATION_NOT_SELECTED"), "warn");
			} else {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATION_NOT_DELETED"), "error");
			}

		}
		return null;
	}

	// GETTTERS AND SETTERS

	public AddCertificationDto getAddCertification() {
		return addCertification;
	}

	public void setAddCertification(AddCertificationDto addCertification) {
		this.addCertification = addCertification;
	}

	public CertificationDto getCertificationDto() {
		return certificationDto;
	}

	public void setCertificationDto(CertificationDto certificationDto) {
		this.certificationDto = certificationDto;
	}

	public List<CertificationDto> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<CertificationDto> certifications) {
		this.certifications = certifications;
	}

	public List<CertificationDto> getCertificationsSelected() {
		return certificationsSelected;
	}

	public void setCertificationsSelected(List<CertificationDto> certificationsSelected) {
		this.certificationsSelected = certificationsSelected;
	}

	public CertificationService getCertificationService() {
		return certificationService;
	}

	public void setCertificationService(CertificationService certificationService) {
		this.certificationService = certificationService;
	}

	public UserCrudBean getUserCrudBean() {
		return userCrudBean;
	}

	public void setUserCrudBean(UserCrudBean userCrudBean) {
		this.userCrudBean = userCrudBean;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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

}
