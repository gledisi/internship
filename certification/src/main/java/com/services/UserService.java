package com.services;

import java.util.List;

import com.dto.UserDto;

public interface UserService {

	public boolean add(UserDto userDto);

	public boolean edit(UserDto userDto);

	public boolean delete(int userId);
	
	public boolean deleteList(List<UserDto> employees);

	public UserDto getUserFromId(int userId);

	public UserDto getLoggedUser(String email);

	public List<UserDto> getEmployeesOfManager(int idManager);
}
