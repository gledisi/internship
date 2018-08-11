package com.converter;

import com.dto.UserDto;
import com.entity.Role;
import com.entity.User;

public class UserConverter {

	public static User toUser(UserDto userDto) {
		User user = new User();

		user.setFirstname(userDto.getFirstname());
		user.setLastname(userDto.getLastname());
		user.setBirthday(userDto.getBirthday());
		user.setIdCard(userDto.getIdCard());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setManagedBy(1);
		user.setValidity(true);
		Role role = new Role();
		role.setId(2);
		user.setRole(role);

		return user;
	}

	public static User toEditUser(UserDto userDto) {
		User user = new User();

		user.setId(userDto.getId());
		user.setFirstname(userDto.getFirstname());
		user.setLastname(userDto.getLastname());
		user.setBirthday(userDto.getBirthday());
		user.setIdCard(userDto.getIdCard());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setManagedBy(1);
		user.setValidity(true);
		Role role = new Role();
		role.setId(2);
		user.setRole(role);

		return user;
	}

	public static UserDto toUserDto(User user) {

		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setFirstname(user.getFirstname());
		userDto.setLastname(user.getLastname());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setIdRole(user.getRole().getId());
		userDto.setIdCard(user.getIdCard());
		userDto.setBirthday(user.getBirthday());

		return userDto;
	}

}