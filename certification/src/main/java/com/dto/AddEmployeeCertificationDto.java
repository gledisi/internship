package com.dto;

import java.util.Date;

public class AddEmployeeCertificationDto {

	private int employeeId;
	private int certificateId;
	private Date date;

	// GETTERS AND SETTERS

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(int certificateId) {
		this.certificateId = certificateId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
