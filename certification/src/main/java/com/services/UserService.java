package com.services;

import java.util.List;

import com.dto.UserDto;

public interface UserService {

	public boolean add(UserDto userDto);

	public boolean edit(UserDto userDto);

	public boolean delete(int userId);

	public boolean deleteList(List<UserDto> employees);

	public boolean existUserEmail(String email);
	
	public boolean existUserCardId(String idCard);

	public boolean changePassword(int userId, String newPassword);

	public UserDto getUserFromId(int userId);

	public UserDto getLoggedUser(String email);

	public List<UserDto> getEmployeesOfManager(String inputSearch, int idManager);
}
