package com.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.converter.UserConverter;
import com.dao.UserDao;
import com.dto.UserDto;
import com.entity.User;
import com.services.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Transactional
	public boolean add(UserDto userDto) {
		return userDao.add(UserConverter.toUser(userDto));
	}

	@Transactional
	public boolean edit(UserDto userDto) {
		return userDao.edit(UserConverter.toEditUser(userDto));
	}

	@Transactional
	public boolean delete(int userId) {
		return userDao.delete(userId);
	}

	@Transactional
	public boolean deleteList(List<UserDto> employees) {
		boolean control = false;
		for (UserDto user : employees) {
			control = userDao.delete(user.getId());
		}
		return control;
	}

	@Transactional(readOnly=true)
	public UserDto getUserFromId(int userId) {
		User user = userDao.getUserFromId(userId);
		return user == null ? null : UserConverter.toUserDto(user);
	}

	@Transactional(readOnly=true)
	public UserDto getLoggedUser(String email) {
		User user = userDao.getLoggedUser(email);
		return user == null ? null : UserConverter.toUserDto(user);
	}

	@Transactional(readOnly=true)
	public boolean existUserEmail(String email) {
		return getLoggedUser(email) != null;
	}

	@Transactional(readOnly=true)
	public boolean existUserCardId(String idCard) {
		return userDao.getUserFromCardId(idCard) != null;
	}

	@Transactional(readOnly=true)
	public List<UserDto> getEmployeesOfManager(String inputSearch, int idManager) {
		return UserConverter.toUserListDto(userDao.getEmployeesOfManager(inputSearch, idManager));
	}

	@Transactional
	public boolean changePassword(int userId, String newPassword) {
		return userDao.changePassword(userId, newPassword);
	}

	// GETTERS AND SETTERS
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
