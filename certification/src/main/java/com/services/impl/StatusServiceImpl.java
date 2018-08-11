package com.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.converter.StatusConverter;
import com.dao.StatusDao;
import com.dto.StatusDto;
import com.services.StatusService;

@Service("statusService")
public class StatusServiceImpl implements StatusService {

	@Autowired
	private StatusDao statusDao;

	@Transactional
	public List<StatusDto> getOtherStatus(String currentStatus) {

		return StatusConverter.toStatusListDto(statusDao.getOtherStatus(currentStatus));

	}

}
