package com.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.converter.UserConverter;
import com.dao.UserDao;
import com.dto.UserDto;
import com.entity.User;
import com.services.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public boolean add(UserDto userDto) {
		return userDao.add(UserConverter.toUser(userDto));
	}

	public boolean edit(UserDto userDto) {
		return userDao.edit(UserConverter.toEditUser(userDto));
	}

	public boolean delete(int userId) {
		return userDao.delete(userId);
	}
	public boolean deleteList(List<UserDto> employees) {
		boolean control = false;
		System.out.print("controlli"+control);
		for (UserDto user : employees) {
			control = userDao.delete(user.getId());
		}
		System.out.print("controlli"+control);
		return control;
	}

	public UserDto getUserFromId(int userId) {
		return UserConverter.toUserDto(userDao.getUserFromId(userId));
	}

	public UserDto getLoggedUser(String email) {
		User user = userDao.getLoggedUser(email);
		if (user != null) {
			return UserConverter.toUserDto(user);
		} else {
			return null;
		}
	}

	public List<UserDto> getEmployeesOfManager(int idManager) {

		List<UserDto> userDtoList = new ArrayList<UserDto>();
		List<User> userList = userDao.getEmployeesOfManager(idManager);
		for (User user : userList) {
			userDtoList.add(UserConverter.toUserDto(user));
		}
		return userDtoList;
	}

	// GETTERS AND SETTERS
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
