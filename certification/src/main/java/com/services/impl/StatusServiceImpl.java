package com.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.converter.StatusConverter;
import com.dao.StatusDao;
import com.dto.StatusDto;
import com.entity.Status;
import com.services.StatusService;

@Service("statusService")
public class StatusServiceImpl implements StatusService {

	@Autowired
	private StatusDao statusDao;

	public List<StatusDto> getAllStatus() {

		List<StatusDto> statusDtoList = new ArrayList<StatusDto>();
		List<Status> statusList = statusDao.getAllStatus();
		for (Status status : statusList) {
			statusDtoList.add(StatusConverter.toStatusDto(status));
		}

		return statusDtoList;

	}

	public List<StatusDto> getOtherStatus(String currentStatus) {

		List<StatusDto> statusDtoList = new ArrayList<StatusDto>();
		List<Status> statusList = statusDao.getOtherStatus(currentStatus);
		for (Status status : statusList) {
			statusDtoList.add(StatusConverter.toStatusDto(status));
		}

		return statusDtoList;
	}

}
