package com.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import com.dto.AddCertificationDto;
import com.dto.CertificationDto;
import com.dto.UserDto;
import com.services.CertificationService;
import com.utility.Messages;

@ManagedBean
@ViewScoped
public class CertificationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private AddCertificationDto addCertification;
	private CertificationDto certificationDto;

	private List<CertificationDto> certifications;
	private List<CertificationDto> certificationsSelected;
	private List<CertificationDto> employeeCertifications;

	@ManagedProperty(value = "#{certificationService}")
	private CertificationService certificationService;
	@ManagedProperty(value = "#{userCrudBean}")
	private UserCrudBean userCrudBean;

	@PostConstruct
	public void init() {
		refreshBean();
	}

	public void refreshBean() {
		this.addCertification = new AddCertificationDto();
		this.certificationDto = new CertificationDto();
		this.certifications = certificationService.getCertificationOfManager(1);
		this.employeeCertifications = certificationService.getEmployeeCertifications(13);

	}

	public String addCertification() {
		if (certificationService.add(addCertification)) {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATION_ADDED"), "info");
			refreshBean();
		} else {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATION_NOT_ADDED"), "error");
		}

		return null;
	}

	public String addCertificationForSelectedEmployee() {
		List<UserDto> employeesSelected = userCrudBean.getSelectedEmployees();
		int size = employeesSelected.size();
		if (size != 0) {
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

	public String set(CertificationDto certification) {
		this.certificationDto = certification;
		System.out.println("certificationDtosjkdfhldjgfjlsdagfljdgfj.");
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

	public List<CertificationDto> getEmployeeCertifications() {
		return employeeCertifications;
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

	public void setEmployeeCertifications(List<CertificationDto> employeeCertifications) {
		this.employeeCertifications = employeeCertifications;
	}

}
