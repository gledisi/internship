package com.converter;

import java.util.ArrayList;
import java.util.List;

import com.dto.StatusDto;
import com.entity.Status;

public class StatusConverter {

	public static StatusDto toStatusDto(Status status) {
		StatusDto statusDto = new StatusDto();
		statusDto.setId(status.getId());
		statusDto.setStatus(status.getStatus());

		return statusDto;
	}

	public static Status toStatus(StatusDto statusDto) {

		Status status = new Status();
		status.setStatus(statusDto.getStatus());

		return status;

	}

	public static List<StatusDto> toStatusListDto(List<Status> statusList) {
		List<StatusDto> statusListDto = new ArrayList<>();

		for (Status status : statusList) {
			statusListDto.add(toStatusDto(status));
		}
		return statusListDto;
	}

}
