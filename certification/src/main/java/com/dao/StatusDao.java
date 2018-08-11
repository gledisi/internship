package com.dao;

import java.util.List;

import com.entity.Status;

public interface StatusDao {

	public List<Status> getOtherStatus(String currentStatus);
}
