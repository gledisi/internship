package com.managedBean;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import com.dto.StatusDto;
import com.services.StatusService;

@ManagedBean(name = "statusBean")
@ViewScoped
public class StatusBean {

	private StatusDto statusDto;
	private List<StatusDto> statusDtoList;

	@ManagedProperty(value = "#{statusService}")
	private StatusService statusService;

	@PostConstruct
	public void init() {
		refreshBean();
	}

	private void refreshBean() {
		this.statusDto = new StatusDto();
		this.statusDtoList = statusService.getAllStatus();
	}


	public StatusDto getStatusDto() {
		return statusDto;
	}

	public void setStatusDto(StatusDto statusDto) {
		this.statusDto = statusDto;
	}

	public List<StatusDto> getStatusDtoList() {
		return statusDtoList;
	}

	public void setStatusDtoList(List<StatusDto> statusDtoList) {
		this.statusDtoList = statusDtoList;
	}

	public StatusService getStatusService() {
		return statusService;
	}

	public void setStatusService(StatusService statusService) {
		this.statusService = statusService;
	}


}
