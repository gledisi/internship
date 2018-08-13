package com.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.dto.CertificateDto;
import com.services.CertificateService;
import com.utility.Messages;

@ManagedBean(name = "certificateBean")
@ViewScoped
public class CertificateBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	@ManagedProperty(value = "#{certificateService}")
	private CertificateService certificateService;

	private List<CertificateDto> certificates;
	private List<CertificateDto> certificatesSelected;

	private CertificateDto certificate;
	private String description;

	@PostConstruct
	public void init() {
		refreshBean();
	}

	private void refreshBean() {
		this.certificate = new CertificateDto();
		getManagerCertificates();
	}

	public void refreshCertificate() {
		this.certificate = new CertificateDto();
	}

	public String getManagerCertificates() {
		this.certificates = certificateService.getCertificates(description, userBean.getUser().getId());
		return null;
	}

	public List<CertificateDto> getAllCertificates() {
		return certificateService.getCertificates(description, userBean.getUser().getId());
	}

	public String addCertificate() {
		certificate.setIdManager(userBean.getUser().getId());
		if (!certificateService.existCertificate(certificate.getName())) {
			if (certificateService.add(certificate)) {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATE_ADDED"), "info");
				refreshBean();
			} else {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATE_NOT_ADDED"), "error");
			}

		} else {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATE_EXIST"), "warn");
		}

		return null;
	}

	public String editCertificate() {

		boolean exist = certificateService.existCertificate(certificate.getName());
		CertificateDto certificateDb = certificateService.getCertificateById(certificate.getId());

		if (!exist || (exist && certificateDb.getName().equals(this.certificate.getName()))) {
			if (certificateService.edit(certificate)) {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATE_EDIT"), "info");
				refreshBean();
			} else {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATE_NOT_EDIT"), "error");
			}

		} else {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATE_EXIST"), "warn");
		}
		return null;
	}

	public String deleteCertificate(int idCertificate) {

		if (certificateService.canDeleteCertificate(idCertificate)) {
			if (certificateService.delete(idCertificate)) {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATE_DELETED"), "info");
				refreshBean();
			} else {
				Messages.addMessage(Messages.bundle.getString("CERTIFICATE_NOT_DELETED"), "error");
			}
		} else {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATE_CANT_DELETED"), "warn");
		}

		return null;
	}

	public String deleteCertificatesSelected() {

		if (certificateService.canDeleteListCertificates(getCertificatesSelected())) {
			if (certificateService.deleteList(getCertificatesSelected())) {
				if (getCertificatesSelected().size() == 1) {
					Messages.addMessage(Messages.bundle.getString("CERTIFICATE_DELETED"), "info");
				} else {
					Messages.addMessage(Messages.bundle.getString("CERTIFICATES_DELETED"), "info");
				}
				refreshBean();
			} else {

				if (getCertificatesSelected().size() == 0) {
					Messages.addMessage(Messages.bundle.getString("CERTIFICATE_NOT_SELECTED"), "warn");
				} else {
					Messages.addMessage(Messages.bundle.getString("CERTIFICATE_NOT_DELETED"), "error");
				}
			}
		} else {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATES_CANT_DELETED"), "warn");
		}
		return null;
	}

	// GETTERS AND SETTERS

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public CertificateService getCertificateService() {
		return certificateService;
	}

	public void setCertificateService(CertificateService certificateService) {
		this.certificateService = certificateService;
	}

	public List<CertificateDto> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<CertificateDto> certificates) {
		this.certificates = certificates;
	}

	public List<CertificateDto> getCertificatesSelected() {
		return certificatesSelected;
	}

	public void setCertificatesSelected(List<CertificateDto> certificatesSelected) {
		this.certificatesSelected = certificatesSelected;
	}

	public CertificateDto getCertificate() {
		return certificate;
	}

	public void setCertificate(CertificateDto certificate) {
		this.certificate = certificate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
