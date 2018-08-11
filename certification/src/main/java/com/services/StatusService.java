package com.services;

import java.util.List;

import com.dto.StatusDto;

public interface StatusService {

	public List<StatusDto> getOtherStatus(String currentStatus);

}
